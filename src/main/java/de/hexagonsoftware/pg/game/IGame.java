package de.hexagonsoftware.pg.game;

import de.hexagonsoftware.pg.graphics.IRenderer;

/**
 * The IGame interface is used for initializing/setting up
 * your game's scene(s) or creating objects which requires textures.
 * The start method is called right before the game-loop start.
 * 
 * This class is not necessary, but recommended.
 * 
 * @author Felix Eckert
 * */
public interface IGame extends IUpdated, IRenderer {
	void start();
}
