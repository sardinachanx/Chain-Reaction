package main;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class CoreProcessor extends BasicGame{

	GameProcessor gp;

	public CoreProcessor(){
		super("Chain Reaction");
		gp = new GameProcessor();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		//TODO
		gp.render(gc, g);
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		// TODO Auto-generated method stub
		gp.init(gc);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		// TODO Auto-generated method stub
		gp.update(gc, delta);
	}

}
