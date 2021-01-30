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

public class PGImageUtil {
    public static BufferedImage flipHorizontal(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), in.getType());
        Graphics2D g = out.createGraphics();
        g.rotate(Math.toRadians(180), out.getWidth()/2, out.getHeight()/2);
        g.drawImage(in, null, 0, 0);
        g.dispose();
        return out;
    }

    public static BufferedImage flipVertical(BufferedImage in) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-in.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        in = op.filter(in, null);

        return in;
    }
}
