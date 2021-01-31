/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.graphics;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import de.hexagonsoftware.pg.Polygon;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * GLGraphics is a helper class for rendering Images and Basic
 * geometry like it is done with awt.Graphics.
 *
 * @see java.awt.Graphics
 * @since 1
 * @author Felix Eckert
 * */
public class GLGraphics {
    private GLAutoDrawable drawable;
    private float r = 0f, g = 0f, b = 0f, a = 0f;
    private boolean alphaEnabled = false;

    private TextRenderer tr = new TextRenderer(new Font("Courier New", Font.PLAIN, 25));
    private Font font = null;

    public GLGraphics(GLAutoDrawable drawable) {
        this.drawable = drawable;
        this.r = 0f;
        this.g = 0f;
        this.b = 0f;
        this.a = 0f;
        this.alphaEnabled = Polygon.PG_PROPERTIES.getPropertyAsBool("gl.enableAlpha");
    }

    public void setColor(Color color) {
        this.r = color.getRed()/255f;
        this.g = color.getGreen()/255f;
        this.b = color.getBlue()/255f;
        this.a = color.getAlpha()/255f;
    }

    public void setFont(Font f) {
        this.font = f;
        this.tr = new TextRenderer(f);
    }

    public Rectangle2D getStringBounds(String s) {
        return tr.getBounds(s);
    }

    public Font getFont() { return font; }
    public float[] getColor() {return new float[] {r, g, b, a};}
    public Color getAWTColor() { return new Color(r*255f, g*255f, b*255f, a*255f);}

    /**
     * Fills a Rectangle.
     *
     * @param x The x coordinate to start
     * @param y The y coordinate to start
     * @param width The width of the rect
     * @param height The height of the rect
     * */
    public void fillRect(int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        if (alphaEnabled) {
            gl.glPushMatrix();
            gl.glEnable(GL2.GL_BLEND);
        }

        gl.glColor4f(r, g, b, a);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(x ,y);
            gl.glVertex2f(x, y+height);
            gl.glVertex2f(x+width, y+height);
            gl.glVertex2f(x+width, y);
        gl.glEnd();

        if (alphaEnabled) {
            gl.glPopMatrix();
        }
    }

    /**
     * Draw an empty Rectangle.
     *
     * @param x The x coordinate to start
     * @param y The y coordinate to start
     * @param width The width of the rect
     * @param height The height of the rect
     * */
    public void drawRect(int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        if (alphaEnabled) {
            gl.glPushMatrix();
            gl.glEnable(GL2.GL_BLEND);
        }

        gl.glColor4f(r, g, b, a);
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(x, y);
            gl.glVertex2f(x, y+height);
            gl.glVertex2f(x, y+height);
            gl.glVertex2f(x+width, y+height);
            gl.glVertex2f(x+width, y+height);
            gl.glVertex2f(x+width, y);
            gl.glVertex2f(x+width, y);
            gl.glVertex2f(x, y);
        gl.glEnd();

        if (alphaEnabled) {
            gl.glPopMatrix();
        }
    }

    public void drawTriangle(int rx, int ry, int tx, int ty, int lx, int ly) {
    	GL2 gl = drawable.getGL().getGL2();
    	
    	if (alphaEnabled) {
    		gl.glPushMatrix();
    		gl.glEnable(GL2.GL_BLEND);
    	}
    	
    	gl.glColor4f(r, g, b, a);
    	
    	gl.glBegin(GL2.GL_TRIANGLES);
			gl.glVertex2f(lx, ly);
    		gl.glVertex2f(rx, ry);
    		gl.glVertex2f(tx, ty);
    	gl.glEnd();
    	
    	if (alphaEnabled) {
    		gl.glPopMatrix();
    	}
    }
    
    /**
     * Draw a texture at its original x and y
     *
     * @param texture The texture to be drawn
     * @param x The x coordinate to start drawing
     * @param y THe y coordinate to start drawing
     * */
    public void drawTexture(Texture texture, int x, int y) {
        texture.bind(drawable.getGL());
        GL gl = drawable.getGL();
        GL2 gl2 = drawable.getGL().getGL2();

        if (alphaEnabled) {
            gl2.glPushMatrix();
            gl2.glEnable(GL2.GL_BLEND);
        }

        texture.enable(gl);
            texture.bind(gl);
            gl2.glColor3f(1, 1, 1);
            gl2.glBegin(GL2.GL_QUADS);
                TextureCoords texcoords = texture.getImageTexCoords();
                gl2.glVertex2f(x, y);
                gl2.glTexCoord2f(texcoords.left(), texcoords.top());
                gl2.glVertex2f(x, y + texture.getHeight());
                gl2.glTexCoord2f(texcoords.right(), texcoords.top());
                gl2.glVertex2f(x + texture.getWidth(), y + texture.getHeight());
                gl2.glTexCoord2f(texcoords.right(), texcoords.bottom());
                gl2.glVertex2f(x + texture.getWidth(), y);
                gl2.glTexCoord2f(texcoords.left(), texcoords.bottom());
            gl2.glEnd();
        texture.disable(gl);

        if (alphaEnabled) {
            gl2.glPopMatrix();
        }
    }

    /**
     * Draws a texture at specified dimensions.
     *
     * @param texture The texture to be drawn
     * @param x The x coordinate to start drawing
     * @param y The y coordinate to start drawing
     * @param width The width of the image
     * @param height The height of the image
     * */
    public void drawTexture(Texture texture, int x, int y, int width, int height) {
        texture.bind(drawable.getGL());
        GL gl = drawable.getGL();
        GL2 gl2 = drawable.getGL().getGL2();

        if (alphaEnabled) {
            gl2.glPushMatrix();
            gl2.glEnable(GL2.GL_BLEND);
        }

        texture.enable(gl);
        texture.bind(gl);
            gl2.glColor3f(1, 1, 1);
            gl2.glBegin(GL2.GL_QUADS);
                TextureCoords texcoords = texture.getImageTexCoords();
                gl2.glVertex2f(x, y);
                gl2.glTexCoord2f(texcoords.left(), texcoords.top());
                gl2.glVertex2f(x, y + height);
                gl2.glTexCoord2f(texcoords.right(), texcoords.top());
                gl2.glVertex2f(x + width, y + height);
                gl2.glTexCoord2f(texcoords.right(), texcoords.bottom());
                gl2.glVertex2f(x + width, y);
                gl2.glTexCoord2f(texcoords.left(), texcoords.bottom());
            gl2.glEnd();
        texture.disable(gl);

        if (alphaEnabled) {
            gl2.glPopMatrix();
        }
    }

    /**
     * Draws a String at specified Position.
     *
     * @param s The String to be drawn
     * @param x The X Coordinate
     * @param y The Y Coordinate
     * */
    public void drawString(String s, int x, int y) {
        tr.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        tr.setColor(r, g, b, a);
        tr.draw(s, x, y);
        tr.endRendering();
    }

    public GLAutoDrawable getDrawable() { return this.drawable; }
}
