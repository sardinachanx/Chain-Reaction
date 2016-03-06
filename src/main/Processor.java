package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface Processor{

	public void init(GameContainer gc) throws SlickException;

	public void render(GameContainer gc, Graphics g) throws SlickException;

	public void update(GameContainer gc, int delta) throws SlickException;

}
