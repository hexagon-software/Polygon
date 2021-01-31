/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.game.objects;

import java.util.ArrayList;

import de.hexagonsoftware.pg.graphics.GLGraphics;

public class GameObjectHandler {
    private ArrayList<GameObject> OBJECTS = new ArrayList<>();

    public void update() {
        OBJECTS.forEach((v) -> v.update());
    }

    public void render(GLGraphics g) {
        OBJECTS.forEach((v) -> v.render(g));
    }

    public void addGameObject(GameObject object) {
        this.OBJECTS.add(object);
    }

    public ArrayList<GameObject> getGameObjects() { return this.OBJECTS; }
}
