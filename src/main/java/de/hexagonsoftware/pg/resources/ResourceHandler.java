/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.resources;

import com.jogamp.opengl.GL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class ResourceHandler {
    private static ResourceHandler INSTANCE = null;
    private static Logger logger = LogManager.getLogger("ResHandler");

    //////////////////////////////////////////////////////////////////////////////////////////////
    private HashMap<String, GLTextureResource> GL_TEXTURES;

    private static MissingFontResource MISSING_FONT_RESOURCE = new MissingFontResource();
    private HashMap<String, FontResource> FONTS;
    //////////////////////////////////////////////////////////////////////////////////////////////

    private ResourceHandler() {
        this.GL_TEXTURES = new HashMap<>();
        this.FONTS = new HashMap<>();
    }

    /****** TEXTURES ******/

    public void registerGLTexture(String name, GLTextureResource res) {
        logger.info(String.format("Registered GLTexture \"%s\"", name));
        GL_TEXTURES.put(name, res);
    }

    public void createGLTexture(String name, String path, Class<?> CLASS, GL gl) {
        registerGLTexture(name, new GLTextureResource(path, CLASS, gl));
    }

    public GLTextureResource getGLTextureRes(String name) {
        return GL_TEXTURES.get(name);
    }

    public static GLTextureResource getGLTexture(String name) {
        if (INSTANCE == null) {
            return null;
        }

        return getInstance().getGLTextureRes(name);
    }

    /****** FONTS ******/

    public void registerFont(String name, FontResource res) {
        FONTS.put(name, res);
    }

    public void createFont(String name, String path, Class<?> CLASS, int type, float size) {
        FONTS.put(name, new FontResource(path, CLASS, type, size));
    }

    public FontResource getFontRes(String name) {
        return FONTS.containsKey(name) ? FONTS.get(name) : MISSING_FONT_RESOURCE;
    }

    public static FontResource getFont(String name) {
        if (INSTANCE == null) {
            return MISSING_FONT_RESOURCE;
        }

        return getInstance().getFontRes(name);
    }

    /////////////////////////////////////////////

    public static ResourceHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ResourceHandler();

        return INSTANCE;
    }
}
