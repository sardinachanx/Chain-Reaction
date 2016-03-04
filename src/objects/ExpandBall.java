package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class ExpandBall extends Ball{

	protected boolean done;

	public ExpandBall(int initialRadius, Color color){
		super(initialRadius, color);
		order = 0;
		done = false;
	}

	public void startExpanding(){
		radius = 0;
		expanding = true;
	}

	public boolean isDone(){
		return done;
	}

	public void setDone(boolean done){
		this.done = done;
	}

	@Override
	public void shrink(int shrinkSpeed){
		super.shrink(shrinkSpeed);
		if(radius <= 0){
			done = true;
		}
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
