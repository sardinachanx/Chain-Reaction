package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import processors.CoreProcessor;

/*
 * The main engine for the game. Initializes a new CoreProcessor that 
 * controls the GUI, Game, Startup Screen, Help and High score.
 */
public class GameEngine{

	//The fixed width of the game screen.
	public static final int WIDTH = 960;
	//The fixed height of the game screen.
	public static final int HEIGHT = 640;

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new CoreProcessor(false));
		app.setTargetFrameRate(60);
		app.setDisplayMode(WIDTH, HEIGHT, false);
		app.setShowFPS(false);
		app.start();
	}
}
