package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class GameEngine{

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new GameFrame());
		app.setTargetFrameRate(60);
		app.setDisplayMode(1280, 800, false);
		app.setShowFPS(false);
		app.start();
	}
}
