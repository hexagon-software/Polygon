/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.util.debugging;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hexagonsoftware.pg.Polygon;
import de.hexagonsoftware.pg.graphics.GLRenderHandler;

public class DebugConsole implements Runnable {
    public static GLRenderHandler RENDER_HANDLER = null;

    public static boolean testSoundWaiting = false;
    public static float[] testSoundPos = null;
    
    public boolean running = true;
    private Logger LOGGER  = LogManager.getLogger(DebugConsole.class);

    public void run() {
        Scanner s = new Scanner(System.in);
        String in = "";
        while (running) {
            in = s.nextLine();

            switch (in) {
                case "stop":
                    Polygon.PG_THREAD_RUNNING = false;
                    s.close();
		            LOGGER.info("> Exiting...");
                    break;
                    
                // Quick botch for testing 3D sound, will likely be removed in release
                case "testSounds":
                	System.out.print("X: ");
                	float x = Float.parseFloat(s.nextLine());
                	System.out.print("Y: ");
                	float y = Float.parseFloat(s.nextLine());
                	System.out.print("Z: ");
                	float z = Float.parseFloat(s.nextLine());
                	DebugConsole.testSoundWaiting = true;
                	DebugConsole.testSoundPos = new float[] {x, y, z};
                	
                    continue;
            }
        }

	    LOGGER.info("Stopped debug thread!");
    }
}
