package de.hexagonsoftware.pg.graphics.animation;

import de.hexagonsoftware.pg.game.IUpdated;
import de.hexagonsoftware.pg.graphics.GLGraphics;
import de.hexagonsoftware.pg.graphics.IRenderer;

/**
 * Animation Base Class, this is only used
 * to generalize different types of animations.
 * 
 * @author Felix Eckert
 * */
public class Animation implements IRenderer, IUpdated {
	protected double speed;
	
	public Animation(double speed) {
		this.speed = speed;
	}
	
	public void update() {
		
	}
	
	public void render(GLGraphics g) {
		
	}
}
