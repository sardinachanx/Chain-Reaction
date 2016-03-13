package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import processors.CoreProcessor;

/**
 * Driver of the game.
 * @author tchan17
 *
 */
public class GameEngine{

	//The fixed width of the game screen.
	public static final int WIDTH = 960;
	//The fixed height of the game screen.
	public static final int HEIGHT = 640;

	/**
	 * Initializes the app.
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new CoreProcessor(false));
		app.setTargetFrameRate(60);
		app.setDisplayMode(WIDTH, HEIGHT, false);
		app.setShowFPS(false);
		app.start();
	}
}
