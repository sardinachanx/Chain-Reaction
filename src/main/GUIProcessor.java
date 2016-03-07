package main;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import menu.Button;
import menu.TextButton;

public class GUIProcessor implements Processor{

	Set<Button> buttons;

	@Override
	public void init(GameContainer gc) throws SlickException{
		// TODO Auto-generated method stub
		buttons = new HashSet<Button>();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		// TODO Auto-generated method stub
		for(Button button : buttons){
			if(button.isVisible()){
				button.render(g);
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		// TODO Auto-generated method stub

	}

	public void addButton(TextButton button){
		buttons.add(button);
	}

}
