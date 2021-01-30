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

import de.hexagonsoftware.pg.graphics.GLGraphics;

public class GameObject {
    protected int x = 0;
    protected int y = 0;
    protected int width = 0;
    protected int height = 0;

    protected GameObjectRenderer renderer;

    public GameObject() {
        this.width = 1;
        this.height = 1;
        this.renderer = new DefaultGameObjectRenderer();
    }

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.renderer = new DefaultGameObjectRenderer();
    }

    public GameObject(int x, int y, int width, int height, GameObjectRenderer renderer) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.renderer = renderer;
    }

    public void update() {}
    public void render(GLGraphics g) {
        renderer.render(g, x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public GameObjectRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(GameObjectRenderer renderer) {
        this.renderer = renderer;
    }
}
