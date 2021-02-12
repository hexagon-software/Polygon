package de.hexagonsoftware.pg.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	public static BufferedImage loadImage(String path, Class<?> clazz) {
		BufferedImage out = null;
		
		if (path == null) {
			return null;
		}
		
		try {
			out = ImageIO.read(clazz.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return out;
	}

}
