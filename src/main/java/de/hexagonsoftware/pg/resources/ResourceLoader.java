/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.resources;

import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.jogamp.opengl.GL;

import de.hexagonsoftware.pg.Polygon;

public class ResourceLoader {
	private static Logger logger = LogManager.getLogger("ResourceLoader");

	public static void loadResources(String file, Class<?> CLASS, GL gl) {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new InputStreamReader(CLASS.getResourceAsStream(file)));
		JsonObject resourceFile = gson.fromJson(reader, JsonObject.class);

		if (resourceFile.has("textures")) {
			loadTextures(resourceFile, CLASS, gl);
		}
		if (resourceFile.has("sounds")) {
			//loadSounds(resourceFile);
		}
		if (resourceFile.has("fonts")) {
			loadFonts(resourceFile, CLASS);
		}
	}

	private static void loadTextures(JsonObject resourceFile, Class<?> CLASS, GL gl) {
		JsonObject textures = resourceFile.get("textures").getAsJsonObject();
		String assetsRoot   = Polygon.PG_PROPERTIES.getProperty("common.assetsRoot");

		if (assetsRoot == null || assetsRoot.matches("NotFound"))
			assetsRoot = "";

		int loadedTextures = 0;

		if (gl == null) {
			logger.error("Missing GL object for texture loading! Aborting... (PG_0x1)");
			return;
		}

		gl.getContext().makeCurrent();

		logger.debug("Texture Loading Context:\nCLASS: "+CLASS+"\nGL: "+gl.toString());

		for (String texture : textures.keySet()) {
			loadedTextures++;
			logger.debug("NAME: "+texture);
			logger.debug("PATH: "+textures.get(texture).getAsString());
			ResourceHandler.getInstance().createGLTexture(texture, assetsRoot + textures.get(texture).getAsString(), CLASS, gl);
		}

		logger.info("Loaded "+loadedTextures+" texture(s)");
	}

	private static void loadFonts(JsonObject resourceFile, Class<?> CLASS) {
		JsonObject fonts = resourceFile.get("fonts").getAsJsonObject();
		String assetsRoot   = Polygon.PG_PROPERTIES.getProperty("common.assetsRoot");

		if (assetsRoot == null || assetsRoot.matches("NotFound"))
			assetsRoot = "";

		int loadedFonts = 0;

		for (String font : fonts.keySet()) {
			loadedFonts++;
			ResourceHandler.getInstance().createFont(font, assetsRoot+fonts.get(font).getAsJsonObject().get("path").getAsString(), CLASS,
					fonts.get(font).getAsJsonObject().get("type").getAsInt(), fonts.get(font).getAsJsonObject().get("size").getAsFloat());
		}

		logger.info("Loaded "+loadedFonts+" font(s)");
	}
}
