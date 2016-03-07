package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class GameEngine{

	public static final int WIDTH = 960;
	public static final int HEIGHT = 640;

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new CoreProcessor(false));
		app.setTargetFrameRate(60);
		app.setDisplayMode(WIDTH, HEIGHT, false);
		app.setShowFPS(false);
		app.start();
	}
}
