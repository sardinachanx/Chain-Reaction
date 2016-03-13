package objects;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import main.GameEngine;

/**
 * The GameBall class extends Ball and floats around during game play. Once collision with the ExpandBall is
 * detected, the GameBall will expand. The GameBall differs from the ExpandBall in that the GameBall is entirely
 * not player-controllable and its speed and initial location are both determined randomly upon creation.
 * @author tchan17
 *
 */
public class GameBall extends Ball{

	private static final float SPEED_CONSTANT = 1.5f;
	private static final float SPEED_MIN = 0.5f;
	private static final float EXPAND_TRANSPARENCY = 0.65f;

	protected float speedX;
	protected float speedY;
	protected boolean colorChanged;

	/**
	 * Creates a new GameBall with randomized speed, x/y coordinates and color.
	 * @param initialRadius the initial radius of the ball
	 * @param color the color of the ball
	 */
	public GameBall(int initialRadius, Color color){
		super(initialRadius, color);
		Random rand = new Random();
		x = rand.nextInt(GameEngine.WIDTH - radius * 2) + radius;
		y = rand.nextInt(GameEngine.HEIGHT - radius * 2) + radius;
		speedX = generateSpeed();
		speedY = generateSpeed();
		colorChanged = false;
	}

	/**
	 * A method to get the horizontal speed of the ball.
	 * @return the horizontal speed of the ball
	 */
	public float getSpeedX(){
		return speedX;
	}

	/**
	 * A method to get the vertical speed of the ball.
	 * @return the vertical speed of the ball
	 */
	public float getSpeedY(){
		return speedY;
	}

	/**
	 * A method to set the horizontal speed of the ball.
	 * @param speedX the new horizontal speed of the ball
	 */
	public void setSpeedX(float speedX){
		this.speedX = speedX;
	}

	/**
	 * A method to set the vertical speed of the ball.
	 * @param speedY the new vertical speed of the bal
	 */
	public void setSpeedY(float speedY){
		this.speedY = speedY;
	}

	/**
	 * A method to generate a random speed for the ball.
	 * @return a random speed of the ball.
	 */
	private static float generateSpeed(){
		float speed = (float) (Math.random() * SPEED_CONSTANT + SPEED_MIN);
		if(Math.random() < 0.5){
			speed = -speed;
		}
		return speed;
	}

	@Override
	public void expand(int expandSpeed, int expandRadius){
		if(!colorChanged){
			colorChanged = true;
			color = new Color(color.r, color.g, color.b, EXPAND_TRANSPARENCY);
		}
		super.expand(expandSpeed, expandRadius);

	}

	@Override
	public void move(GameContainer gc){
		x += speedX;
		if(x > GameEngine.WIDTH - radius || x < radius){
			speedX *= -1;
		}
		y += speedY;
		if(y > GameEngine.HEIGHT - radius || y < radius){
			speedY *= -1;
		}
	}

	@Override
	public boolean isGameBall(){
		return true;
	}

}
