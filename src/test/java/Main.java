/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

import java.awt.Color;

import de.hexagonsoftware.pg.Polygon;
import de.hexagonsoftware.pg.audio.AudioEngine;
import de.hexagonsoftware.pg.audio.AudioEngineException;
import de.hexagonsoftware.pg.game.objects.GameObject;
import de.hexagonsoftware.pg.graphics.GLGraphics;
import de.hexagonsoftware.pg.graphics.IRenderer;

public class Main implements IRenderer {
    public static void main(String[] args) throws AudioEngineException {
        //System.out.println("Running Polygon Test...");
        Polygon pg = new Polygon("/engine.properties", Main.class);
        
        // Audio Engine Instance
        AudioEngine ae = AudioEngine.getInstance();
        
        // Load WAV File "test" and save it as "test" in the Sounds Hashmap
        ae.loadSound("test", "/assets/test.wav");
        
        // Create a new listener for the AudioEngine and specify position, orientation and velocity
        ae.createListener(new float[] {0f, 0f, 0f}, new float[] {0f, 0f, 0f}, new float[] {0f, 0f, 0f});
        
        // Create a source for the sound "test" and save the given id for the source
        int source = ae.createSource("test", new float[] {300f, -10f, 0f}, new float[] {0f, 0f, 0f}, 100.0f, 1.0f);

        // Start engine and add Game Objects
        pg.start(new Game());
        Polygon.RENDER_TARGETS.add(new Main());
        Polygon.PG_GAME_OBJECT_HANDLER.addGameObject(new GameObject(5, 5, 15, 5));
        
        //ae.updateListener(); // <-- DEBUG
        ae.playSource(source); // Playback the sound source
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
        
        g.setColor(new Color(255, 255, 255, 127));
        g.fillTriangle(0, 100, 100, 0, 200, 100);
        
        g.setColor(new Color(255, 0, 200, 127));
        g.fillTriangle(50, 150, 150, 50, 250, 150);
        
        g.setColor(new Color(255, 255, 0, 127));
        g.fillTriangle(100, 200, 200, 100, 300, 200);
        
        g.setColor(new Color(0, 255, 0, 127));
        g.drawTriangle(150, 250, 250, 150, 350, 250);
        
        /////////////////////////////////////////////////
        
        g.setColor(new Color(0, 255, 0, 127));
        g.fillTriangle(200, 50, 250, 0, 300, 50);
        
        g.setColor(new Color(255, 255, 0, 127));
        g.fillTriangle(250, 0,  300, 50, 350, 0);
        
        g.setColor(new Color(255, 0, 200, 127));
        g.fillTriangle(300, 50, 350, 0, 400, 50);
        
        g.setColor(new Color(255, 255, 255, 127));
        g.fillTriangle(200, 50, 250, 100, 300, 50);
        
        g.setColor(new Color(0, 255, 50, 100));
        g.fillTriangle(250, 100, 300, 50, 350, 100);
        
        g.setColor(new Color(0, 255, 255, 100));
        g.fillTriangle(300, 50, 350, 100, 400, 50);

        /////////////////////////////////////////////////
        
        g.setColor(new Color(255, 255, 0));
        g.fillRect(500, 500, 100, 100);
    }
}
