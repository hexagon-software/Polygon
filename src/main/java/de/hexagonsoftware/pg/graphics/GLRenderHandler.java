/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import de.hexagonsoftware.pg.Polygon;

import java.awt.*;

public class GLRenderHandler implements GLEventListener {
    public void init(GLAutoDrawable glAutoDrawable) {
        setup(glAutoDrawable, glAutoDrawable.getSurfaceWidth(), glAutoDrawable.getSurfaceHeight());
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    public void display(GLAutoDrawable glAutoDrawable) {
        Color cc = Polygon.PG_PROPERTIES.getPorpertyAsColor("gl.clearColor");
        glAutoDrawable.getGL().getGL2().glClearColor(cc.getRed()/255f, cc.getGreen()/255f, cc.getBlue()/255f, 1f);
        glAutoDrawable.getGL().getGL2().glClear(GL2.GL_COLOR_BUFFER_BIT);

        final GLGraphics g = new GLGraphics(glAutoDrawable);

        Polygon.PG_CAMERA.getVisible().forEach((v) -> v.render(g));
        Polygon.RENDER_TARGETS.forEach((v) -> v.render(g));
    }

    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        // (re)setup GL
        setup(glAutoDrawable, width, height);
    }

    private void setup(GLAutoDrawable glAutoDrawable, int width, int height) {
        GL2 gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glMatrixMode( GL2.GL_PROJECTION );
        gl2.glLoadIdentity();

        GLU glu = new GLU();
        glu.gluOrtho2D(0.0f, (float)width, (float)height, 0.0f);

        gl2.glMatrixMode( GL2.GL_MODELVIEW );
        gl2.glLoadIdentity();

        gl2.glViewport( 0, 0, width, height );

        if (Polygon.PG_PROPERTIES.getPropertyAsBool("gl.enableAlpha")) {
            gl2.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
            gl2.glEnable(GL2.GL_BLEND);
        }

        gl2.setSwapInterval(Polygon.PG_PROPERTIES.getPropertyAsInt("gl.swapInterval"));
    }
}
