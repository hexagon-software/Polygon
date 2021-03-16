/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.game.objects;

import de.hexagonsoftware.pg.Polygon;
import de.hexagonsoftware.pg.graphics.GLGraphics;

import java.awt.*;

public class DefaultGameObjectRenderer extends GameObjectRenderer {
    public void render(GLGraphics g, int x, int y, int width, int height) {
        g.setColor(new Color(255, 0, 255, 125));
        g.fillRect(x*Polygon.EngineLevel.ONE_UNIT_PX,
                y*Polygon.EngineLevel.ONE_UNIT_PX,
                width*Polygon.EngineLevel.ONE_UNIT_PX,
                height*Polygon.EngineLevel.ONE_UNIT_PX);
    }

    public void render(GLGraphics g) {}
}
