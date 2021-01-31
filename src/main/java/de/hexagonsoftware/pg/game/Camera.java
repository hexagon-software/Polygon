/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.game;

import de.hexagonsoftware.pg.Polygon;
import de.hexagonsoftware.pg.game.objects.GameObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Camera {
    // All coordinates in World-Coordinates

    public int worldX  = 0;
    public int worldY  = 0;
    public int cameraZ = -1;

    public int cameraWidth  = 0;
    public int cameraHeight = 0;

    private ArrayList<GameObject> visible = new ArrayList<>();

    public Camera(int x, int y, int z, int width, int height) {
        this.worldX  = x;
        this.worldY  = y;
        this.cameraZ = z;
        this.cameraWidth = (width/32);
        this.cameraHeight = (height/32);
    }

    public void centerOnGOBJ(GameObject gameObject) {
        this.worldX = ((gameObject.getX()/2)+(gameObject.getWidth()/2));
        this.worldY = ((gameObject.getY()/2)+(gameObject.getHeight()/2));
    }

    public void update() {
        visible.clear();
        ArrayList<GameObject> objects = Polygon.PG_GAME_OBJECT_HANDLER.getGameObjects();
        Iterator<GameObject> i = objects.iterator();
        while (i.hasNext()) {
            GameObject obj = (GameObject) i.next();

            if (obj.getX() <= worldX+cameraWidth && obj.getY() <= worldY+cameraHeight && obj.getX() >= worldX && obj.getY() >= worldY) {
                visible.add(obj);
            }
        }
    }

    public ArrayList<GameObject> getVisible() {
        return visible;
    }
}
