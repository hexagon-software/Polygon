package de.hexagonsoftware.pg.graphics.animation;

import com.jogamp.opengl.util.texture.Texture;

import de.hexagonsoftware.pg.graphics.GLGraphics;

public class TextureAnimation extends Animation {
	private Texture[] frames;
	private int index;
	private long lastTime;
	
	private int x, y, width, height;
	
	public TextureAnimation(Texture[] frames, double speed, int x, int y, int width, int height) {
		super(speed);
		this.frames = frames;
		this.index  = 0;
		this.lastTime = System.currentTimeMillis();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void nextFrame() {
		int nFrames = frames.length;
		
		if (index == nFrames-1) {
			index = 0;
			return;
		}
		
		index++;
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
