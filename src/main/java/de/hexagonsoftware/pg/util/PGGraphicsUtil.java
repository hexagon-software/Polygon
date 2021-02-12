/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Class for Graphics Utility methods.
 * 
 * @author Felix Eckert
 * */
public class PGGraphicsUtil {
	/**
	 * Converts a GL-Type color to normal RGBA
	 * 
	 * @param r The Red value
	 * @param g The Green Value
	 * @param b The Blue Value
	 * @param a The Alpha Value
	 * @return A Color Object with the converted rgba values
	 * */
	public static Color glToRGBColor(float r, float g, float b, float a) {
		return new Color(r*255f, g*255f, b*255f, a*255f);
	}
	
	/**
	 * Flips an Image along the Horizontal Axis
	 * @param in The Image to be flipped
	 * @return The flipped image
	 * */
    public static BufferedImage flipHorizontal(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), in.getType());
        Graphics2D g = out.createGraphics();
        g.rotate(Math.toRadians(180), out.getWidth()/2, out.getHeight()/2);
        g.drawImage(in, null, 0, 0);
        g.dispose();
        return out;
    }

    /**
     * Flips an Image along the Vertical Axis.
     * @param in The Image to be flipped
     * @return The flipped image
     * */
    public static BufferedImage flipVertical(BufferedImage in) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-in.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        in = op.filter(in, null);

        return in;
    }
}
