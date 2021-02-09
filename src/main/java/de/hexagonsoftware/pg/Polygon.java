/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */
package de.hexagonsoftware.pg;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hexagonsoftware.pg.game.Camera;
import de.hexagonsoftware.pg.game.IGame;
import de.hexagonsoftware.pg.game.IUpdated;
import de.hexagonsoftware.pg.game.objects.GameObjectHandler;
import de.hexagonsoftware.pg.graphics.GLRenderHandler;
import de.hexagonsoftware.pg.graphics.IRenderer;
import de.hexagonsoftware.pg.graphics.Window;
import de.hexagonsoftware.pg.resources.ResourceLoader;
import de.hexagonsoftware.pg.util.FileUtil;
import de.hexagonsoftware.pg.util.PGProperties;
import de.hexagonsoftware.pg.util.debugging.DebugConsole;

/**
 * Polygon 1 by Hexagon Software.
 *
 * @version 1
 * @author Felix Eckert
 * */
public class Polygon implements Runnable {
    /**
     * Engine Thread
     * */
    public static Thread PG_THREAD;
    /**
     * Running Boolean
     * */
    public static boolean PG_THREAD_RUNNING = false;
    /*Graphics-Related Variables*/
    /**
     * Engine Window
     * */
    public static Window PG_WINDOW;
    /**
     * Engine GLRenderHandler
     * */
    public static GLRenderHandler PG_RENDER_HANDLER;
    /**
     * Render Targets
     * */
    public static volatile ArrayList<IRenderer> RENDER_TARGETS = new ArrayList<>();
    /*Other Variables*/
    /**
     * Object Handler
     * */
    public static GameObjectHandler PG_GAME_OBJECT_HANDLER = new GameObjectHandler();
    /**
     * Updater Objects
     * */
    public static volatile ArrayList<IUpdated> PG_UPDATER_LIST = new ArrayList<>();
    /**
     * Game Camera
     * */
    public static Camera PG_CAMERA = new Camera(0 ,0 , -1, 1920, 1080);
    /**
     * IGame instance
     * */
    public static IGame PG_IGAME;
    /**
     * Engine Version
     * */
    public static final String PG_VERSION = "1";
    /**
     * Engine Logger
     * */
    public static final Logger PG_LOGGER = LogManager.getLogger("PG"+PG_VERSION);
    /**
     * Common Class for loading Files from jar
     * */
    public static Class<?> PG_CLASS_LOADING = Polygon.class;
    /**
     * Engine Properties
     * */
    public static PGProperties PG_PROPERTIES;
    /**
     * Debug Console
     * */
    public static DebugConsole PG_DEBUG_CONSOLE;
    /**
     * Debug Console Thread.
     * */
    public static Thread PG_DEBUG_CONSOLE_THREAD;
    ///////////////////////////////////////////////////////////////////////

    /**
     * Creates a generic Engine Instance
     * */
    public Polygon() {
    	PG_LOGGER.info("Initialising Polygon...");
        PG_PROPERTIES = FileUtil.loadPGPropertiesFromJAR("/default.properties", this.getClass());
        PG_WINDOW = new Window(PG_PROPERTIES.getProperty("window.title"), PG_PROPERTIES.getPropertyAsInt("window.width"),
                PG_PROPERTIES.getPropertyAsInt("window.height"), PG_PROPERTIES.getPropertyAsBool("window.resizable"));
    }

    /**
     * Creates Instance with parameters
     * @param properties The properties file, containing the engine config.
     * @param CLAZZ The class to use for loading this file.
     * */
    public Polygon(String properties, Class<?> CLAZZ) {
    	PG_LOGGER.info("Initialising Polygon...");
        PG_CLASS_LOADING = CLAZZ;
        PG_PROPERTIES = FileUtil.loadPGPropertiesFromJAR(properties, CLAZZ);
        PG_WINDOW = new Window(PG_PROPERTIES.getProperty("window.title"), PG_PROPERTIES.getPropertyAsInt("window.width"),
                PG_PROPERTIES.getPropertyAsInt("window.height"), PG_PROPERTIES.getPropertyAsBool("window.resizable"));
    }

    ///////////////////////////////////////////////////////

    public void start() {
        PG_LOGGER.info("Starting Thread...");
        PG_THREAD = new Thread(this, "PG"+PG_VERSION);
        PG_THREAD.start();
    }
    
    public void start(IGame game) {
        PG_IGAME = game;
        start();
    }

    public void run() {
        PG_THREAD_RUNNING = true;

        PG_LOGGER.info("Initialising GLRenderHandler");
        PG_RENDER_HANDLER = new GLRenderHandler();

        PG_LOGGER.info("Loading Textures...");

        PG_LOGGER.info(PG_PROPERTIES.getProperty("common.gameResourceList"));
        if (!PG_PROPERTIES.getProperty("common.gameResourceList").matches("NotFound")) {
            PG_WINDOW.getCanvas().display();
            //PG_WINDOW.getCanvas().getContext().makeCurrent();
            ResourceLoader.loadResources(PG_PROPERTIES.getProperty("common.gameResourceList"), PG_CLASS_LOADING, PG_WINDOW.getCanvas().getGL());
        }

        PG_LOGGER.info("Defaulting Camera...");

        // Assign Camera Variables
        PG_CAMERA.cameraWidth = PG_PROPERTIES.getPropertyAsInt("camera.width");
        PG_CAMERA.cameraHeight = PG_PROPERTIES.getPropertyAsInt("camera.height");
        PG_CAMERA.worldX = PG_PROPERTIES.getPropertyAsInt("camera.x");
        PG_CAMERA.worldY = PG_PROPERTIES.getPropertyAsInt("camera.y");
        PG_CAMERA.cameraZ = PG_PROPERTIES.getPropertyAsInt("camera.z");

        // Set Scale Factors
        WorldUnits.SCALE_FACTOR_X = PG_PROPERTIES.getPropertyAsDouble("camera.scaleFactorX");
        WorldUnits.SCALE_FACTOR_Y = PG_PROPERTIES.getPropertyAsDouble("camera.scaleFactorY");

        PG_LOGGER.info("Loading done!");
        // Add PG_RENDER_HANDLER as Event Handler
        PG_WINDOW.getCanvas().setContext(PG_WINDOW.getCanvas().getGL().getContext(), false);
        PG_WINDOW.getCanvas().addGLEventListener(PG_RENDER_HANDLER);

        // Enable debug if activated in config
        if (PG_PROPERTIES.getPropertyAsBool("debug.enable")) {
            PG_LOGGER.info("Debug enabled!");

            if (PG_PROPERTIES.getPropertyAsBool("debug.console")) {
                DebugConsole.RENDER_HANDLER = PG_RENDER_HANDLER;
                PG_DEBUG_CONSOLE = new DebugConsole();
                PG_DEBUG_CONSOLE_THREAD = new Thread(PG_DEBUG_CONSOLE);
                PG_DEBUG_CONSOLE_THREAD.start();
            }
         }

        if (PG_IGAME != null) PG_IGAME.start();
        
        while (PG_THREAD_RUNNING) {
            update();
            PG_WINDOW.getCanvas().display();
        }

        PG_DEBUG_CONSOLE.running = false;
    }

    private void update() {
        PG_CAMERA.update();
        PG_GAME_OBJECT_HANDLER.update();

        Iterator<IUpdated> i = PG_UPDATER_LIST.iterator();

        try {
	        while (i.hasNext()) {
	            ((IUpdated) i.next()).update();
	        }
        } catch (ConcurrentModificationException e) {
        	PG_LOGGER.warn("Caught CME whilst updating");
        	return;
        }
        
        if (PG_IGAME != null) PG_IGAME.update();
    }

    public static class WorldUnits {
        public static final int    ONE_UNIT_PX = 8;
        public static final double ONE_PX_UNIT = 0.125;
        public static double 	   SCALE_FACTOR_X = 1;
        public static double 	   SCALE_FACTOR_Y = 1;
    }
}
