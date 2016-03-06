package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class GameEngine{

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new CoreProcessor());
		app.setTargetFrameRate(60);
		app.setDisplayMode(960, 640, false);
		app.setShowFPS(false);
		app.start();
	}
}
