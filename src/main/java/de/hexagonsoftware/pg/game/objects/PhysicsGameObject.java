package de.hexagonsoftware.pg.game.objects;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import de.hexagonsoftware.pg.Polygon;

public class PhysicsGameObject extends GameObject {
	private Body physBody;
	private Fixture physFixture;
	
	// This function should be Implemented by Standard.
	// It should check if the position is available
	// (No other collider is there). It is essentially
	// a function for safely teleporting an object without
	// messing up collision real bad. 
	// - Felix
	public void updatePosition(int x, int y) {
		// TODO: Actually implement availabilty check
		for (int i = 0; i < Polygon.PG_WORLD.getBodyCount(); i++) {
			Body body = Polygon.PG_WORLD.getBodyList();
			if (body == null) break;
		}
	}
}
