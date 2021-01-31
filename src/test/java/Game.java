import com.jogamp.opengl.util.texture.Texture;

import de.hexagonsoftware.pg.Polygon;
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
		// Test Animation
        TextureAnimation ta = new TextureAnimation(new Texture[] {ResourceHandler.getGLTexture("f1").get(), 
        		ResourceHandler.getGLTexture("f2").get(),
        		ResourceHandler.getGLTexture("f3").get(),
        		ResourceHandler.getGLTexture("f4").get(),
        		ResourceHandler.getGLTexture("f5").get()},
        		1000, // 1 FPS
        		0, // draw at X: 500
        		0, // Y: 500
        		1280, // Width
        		720  // Height
        		);
	}
}
