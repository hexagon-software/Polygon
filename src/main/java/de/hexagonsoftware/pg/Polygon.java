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
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import de.hexagonsoftware.pg.audio.AudioEngine;
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
    /*World/Game Variables*/
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
     * JBox2D world for Physics
     * */
    public static World PG_WORLD;
    /*Other Variables*/
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
                PG_PROPERTIES.getPropertyAsInt("window.height"), PG_PROPERTIES.getPropertyAsBool("window.resizable"), PG_PROPERTIES.getPropertyAsBool("window.fullscreen"));
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
                PG_PROPERTIES.getPropertyAsInt("window.height"), PG_PROPERTIES.getPropertyAsBool("window.resizable"), PG_PROPERTIES.getPropertyAsBool("window.fullscreen"));
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

        PG_LOGGER.info("Loading Resources...");

        // Load Resources if the property "common.gameResourceList" exists
        if (!PG_PROPERTIES.getProperty("common.gameResourceList").matches("NotFound")) {        
        	PG_LOGGER.info("Game Resource List: "+PG_PROPERTIES.getProperty("common.gameResourceList"));
            PG_WINDOW.getCanvas().display();
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
        EngineLevel.SCALE_FACTOR_X = PG_PROPERTIES.getPropertyAsDouble("camera.scaleFactorX");
        EngineLevel.SCALE_FACTOR_Y = PG_PROPERTIES.getPropertyAsDouble("camera.scaleFactorY");

        PG_LOGGER.info("Loading done!");
        // Add PG_RENDER_HANDLER as Event Handler
        PG_WINDOW.getCanvas().setContext(PG_WINDOW.getCanvas().getGL().getContext(), false);
        PG_WINDOW.getCanvas().addGLEventListener(PG_RENDER_HANDLER);

        PG_LOGGER.info("Loading world...");
        EngineLevel.genLevelCreate();
        PG_UPDATER_LIST.add(EngineLevel.getInstance());
        
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
        
        AudioEngine.getInstance().start();
        
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
    
    /**
     * This essentially contains all informations
     * about a currently loaded level. Its gen-level-
     * create function instantiates a basic world
     * with normal gravity.
     * */
    public static class EngineLevel implements IUpdated {
    	// These are reference classes for the size of World Units
    	// on a 1280x720 Window in pixels. These are used for translating
    	// world to screen coordinates.
    	// This will be moved to a separate class, when Physics are implemented.
        public static final int    ONE_UNIT_PX = 8;
        public static final double ONE_PX_UNIT = 0.125;
        public static double 	   SCALE_FACTOR_X = 1;
        public static double 	   SCALE_FACTOR_Y = 1;
        
        private static EngineLevel INSTANCE = new EngineLevel();
        
        /**
         * pus = Physics-Updates/Second. Default 200ms
         * */
        public static final long pus = 200;      
        private static long lastTime;
        
        public static void genLevelCreate() {
        	Polygon.PG_WORLD = new World(new Vec2(0, 10));
        	lastTime = System.currentTimeMillis();
        }
        
        public static EngineLevel getInstance() {
        	return INSTANCE == null ? INSTANCE = new EngineLevel() : INSTANCE;
        }
        
        public void update() {
        	if (System.currentTimeMillis() - lastTime > 1000) {
        		Polygon.PG_WORLD.step(0.02f, 10, 20);
        	}
        }
    }
}
