/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */
package de.hexagonsoftware.pg.graphics;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import de.hexagonsoftware.pg.Polygon;

import javax.swing.*;
import java.awt.*;

/**
 * Window class used by the Engine.
 *
 * @author Felix Eckert
 * */
public class Window extends JFrame {
    private GLCanvas canvas;
    private GLCapabilities capabilities;
    private GLProfile profile;

    /**
     * Creates a generic 1920x1080 res. Window,
     * will have GLCanvas and will be visible
     * automatically.
     * */
    public Window() {
        super(String.format("PG%s by Hexagon Software", Polygon.PG_VERSION));
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup GL
        profile = GLProfile.getDefault();
        capabilities = new GLCapabilities(profile);
        canvas = new GLCanvas(capabilities);

        // Add Canvas
        getContentPane().add(canvas, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Creates a Window with parameters.
     *
     * @param title the window title
     * @param width the window width
     * @param height the window height
     * */
    public Window(String title, int width, int height, boolean resizable) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup GL
        profile = GLProfile.getDefault();
        capabilities = new GLCapabilities(profile);
        canvas = new GLCanvas(capabilities);

        // Add Canvas
        getContentPane().add(canvas, BorderLayout.CENTER);

        // Finish up
        setResizable(resizable);
        setVisible(true);
    }

    /////////////////////////////////////////////////////////////////////

    /**
     * Convinience method for getting the Windows input-map
     * @return the input-map
     * */
    public InputMap getInputMap() {
        return this.getRootPane().getInputMap();
    }

    /**
     * Convinience method for getting the Windows action-map
     * @return the action-map
     * */
    public ActionMap getActionMap() {
        return this.getRootPane().getActionMap();
    }

    /**
     * @return the GLCanvas
     * */
    public GLCanvas getCanvas() { return this.canvas; }
}
