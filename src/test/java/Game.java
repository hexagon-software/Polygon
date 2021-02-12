import com.jogamp.opengl.util.texture.Texture;

import de.hexagonsoftware.pg.Polygon;
import de.hexagonsoftware.pg.audio.AudioEngine;
import de.hexagonsoftware.pg.game.IGame;
import de.hexagonsoftware.pg.graphics.GLGraphics;
import de.hexagonsoftware.pg.graphics.animation.TextureAnimation;
import de.hexagonsoftware.pg.resources.ResourceHandler;

public class Game implements IGame {
	
	@Override
	public void update() {
	}

	@Override
	public void render(GLGraphics g) {
	}

	@Override
	public void start() {
		Texture[] imgs = new Texture[] {
				ResourceHandler.getGLTexture("f1").get(),
				ResourceHandler.getGLTexture("f2").get(),
				ResourceHandler.getGLTexture("f3").get(),
				ResourceHandler.getGLTexture("f4").get(),
				ResourceHandler.getGLTexture("f5").get()
		};
		
		// Test Animation
        TextureAnimation ta = new TextureAnimation(
        		imgs,
        		1000, // 1 FPS
        		0,    // draw at X: 500
        		0,    // Y: 500
        		Polygon.PG_WINDOW.getWidth(), // Width
        		Polygon.PG_WINDOW.getHeight()   // Height
        		);
        ta.play();
        
        AudioEngine ae = AudioEngine.getInstance();
        
        // Create a new listener for the AudioEngine and specify position, orientation and velocity
        ae.createListener(new float[] {0f, 0f, 0f}, new float[] {0f, 0f, 0f}, new float[] {0f, 0f, 0f});
        
        // Create a source for the sound "test" and save the given id for the source
        int source = ae.createSource("test", new float[] {300f, -10f, 0f}, new float[] {0f, 0f, 0f}, 100.0f, 1.0f);

        //ae.updateListener(); // <-- DEBUG
        ae.playSource(source); // Playback the sound source
	}
}
