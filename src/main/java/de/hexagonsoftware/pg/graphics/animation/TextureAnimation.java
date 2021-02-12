package de.hexagonsoftware.pg.graphics.animation;

import com.jogamp.opengl.util.texture.Texture;

import de.hexagonsoftware.pg.Polygon;
import de.hexagonsoftware.pg.graphics.GLGraphics;

/**
 * An Animation consisting of multiple textures.
 * 
 * @author Felix Eckert
 * */
public class TextureAnimation extends Animation {
	private Texture[] frames;
	private int index;
	private long lastTime;
	public int nPlayed;
	public boolean played;
	public boolean once;
	
	private int x, y, width, height;

	/**
	 * @param frames The frames for the animation
	 * @param speed  The speed of the animation in milliseconds
	 * @param x      The X Coordinate for drawing
	 * @param y		 The Y Coordinate for drawing
	 * @param width  The Width for drawing
	 * @param height The Height for drawing
	 * */
	public TextureAnimation(Texture[] frames, double speed, int x, int y, int width, int height) {
		super(speed);
		this.frames = frames;
		this.index  = 0;
		this.lastTime = System.currentTimeMillis();
		this.nPlayed = 0;
		this.played = false;
		this.once = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Jumps the frame-index to the next frame,
	 * if it is the last frame, it loops over.
	 * */
	public void nextFrame() {
		int nFrames = frames.length;
		
		if (index == nFrames-1) {
			index = 0;
			if (once) {
				Polygon.RENDER_TARGETS.remove(this);
				Polygon.PG_UPDATER_LIST.remove(this);
				once = false;
				played = true;
				return;
			}
			nPlayed++;
			return;
		}
		
		index++;
	}
	
	/**
	 * Plays back the Animation once.
	 * */
	public void play() {
		once = true;

		Polygon.RENDER_TARGETS.add(this);
		Polygon.PG_UPDATER_LIST.add(this);
	}
	
	/**
	 * Loops the Animation
	 * */
	public void loop() {
		Polygon.RENDER_TARGETS.add(this);
		Polygon.PG_UPDATER_LIST.add(this);
	}
	
	/**
	 * Stops the animation and resets the index.
	 * */
	public void stop() {
		Polygon.RENDER_TARGETS.remove(this);
		Polygon.PG_UPDATER_LIST.remove(this);
		this.index = 0;
	}
	
	public void update() {
		if (System.currentTimeMillis() - lastTime > speed) {
			nextFrame();
			lastTime = System.currentTimeMillis();
		}
	}
	
	@Override
	public void render(GLGraphics g) {
		g.drawTexture(frames[index], x, y, width, height);
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
}
