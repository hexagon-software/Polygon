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
        TextureAnimation ta = new TextureAnimation(new Texture[] {ResourceHandler.getGLTexture("orange").get(), 
        		ResourceHandler.getGLTexture("white").get()},
        		1000, // 1 FPS
        		500, // draw at X: 500
        		500, // Y: 500
        		128, // Width
        		128  // Height
        		);
        Polygon.RENDER_TARGETS.add(ta);
        Polygon.PG_UPDATER_LIST.add(ta);
	}
}
