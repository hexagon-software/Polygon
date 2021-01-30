/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

import de.hexagonsoftware.pg.Polygon;
import de.hexagonsoftware.pg.game.objects.GameObject;
import de.hexagonsoftware.pg.graphics.GLGraphics;
import de.hexagonsoftware.pg.graphics.IRenderer;

import java.awt.Color;

public class Main implements IRenderer {
    public static void main(String[] args) {
        System.out.println("Running Polygon Test...");
        Polygon pg = new Polygon("/engine.properties", Main.class);
        pg.start();
        Polygon.RENDER_TARGETS.add(new Main());
        Polygon.PG_GAME_OBJECT_HANDLER.addGameObject(new GameObject(5, 5,15 ,5));
    }

    public void render(GLGraphics g) {

        // Transparency Test (who would have ever thought that it worked)
        // Draws a bunch of rects which overlap with one another
        g.setColor(new Color(0, 50, 255, 100));
        g.fillRect(5*8, 0, 10*8, 5*8);
        g.fillRect(10*8, 0, 5*8, 20*8);

        g.setColor(new Color(0, 255, 255, 100));
        g.fillRect(0, 10*8, 20*8, 5*8);
        g.fillRect(0, 10*8, 5*8, 15*8);
        g.fillRect(0, 10*8, 10*8, 15*8);

        g.setColor(new Color(0, 255, 50, 100));
        g.fillRect(0, 15*8, 20*8, 5*8);

    }
}
