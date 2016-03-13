package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * This class extends Ball. The ExpandBall is a special, player-controlled type of ball/bomb that will be
 * expanded upon right-click by the player in order to trigger a chain reaction.
 * @author tchan17
 *
 */
public class ExpandBall extends Ball{

	//A boolean to check whether the user has clicked, expanded and shrunk this ExpandBall.
	protected boolean done;

	public ExpandBall(int initialRadius, Color color){
		super(initialRadius, color);
		order = 0;
		done = false;
	}

	/**
	 * A method that starts the expanding process of the ball
	 */
	public void startExpanding(){
		radius = 0;
		expanding = true;
	}

	/**
	 * A method to check whether the ball has been expanded by the user and shrunk completely.
	 * @return whether the ball has been expanded by the user
	 */
	public boolean isDone(){
		return done;
	}

	/**
	 * Sets the ball to expanded and shrunk or not expanded and shrunk.
	 * @param done whether the ball has been expanded by the user
	 */
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
