package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class ExpandBall extends Ball{

	public ExpandBall(int initialRadius){
		super(initialRadius);
		color = new Color(255, 255, 255, 96);
	}

	public void startExpanding(){
		radius = 0;
		expanding = true;
	}

	@Override
	public void move(GameContainer gc){
		Input input = gc.getInput();
		x = input.getMouseX();
		y = input.getMouseY();
	}

	@Override
	public boolean isGameBall(){
		return false;
	}

}
