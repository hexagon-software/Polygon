/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.util.debugging;

import de.hexagonsoftware.pg.Polygon;
import de.hexagonsoftware.pg.graphics.GLRenderHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class DebugConsole implements Runnable {
    public static GLRenderHandler RENDER_HANDLER = null;

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
                //case "reloadTextures":
                //     Polygon.reloadTextures();
                //     System.out.println("> Reloaded Textures");
                //     continue;
            }
        }

	    LOGGER.info("Stopped debug thread!");
    }
}
