package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

/**
 * The abstract Ball class defines the basic structure and attributes of any ball, or "bomb",
 * that appears in the game play.
 * @author tchan17
 *
 */
public abstract class Ball{

	protected float x;
	protected float y;
	protected int radius;
	protected boolean expanding;
	protected boolean expanded;
	protected boolean shrinking;
	//The time elapsed between when the ball fully expanded and now.
	protected long timer;
	protected Color color;
	/**
	 * The distance between the player-expanded ball and this instance of ball. By default, the order of the
	 * player-expanded ball is 0, and any ball that comes in contact directly with the player-expanded ball has
	 * an order of 1.
	 */
	protected int order;

	/**
	 * Creates a new instance of Ball. The initial radius and color is passed in.
	 * @param radius the radius of the ball initially
	 * @param color the color of the ball
	 */
	public Ball(int radius, Color color){
		this.color = color;
		this.radius = radius;
		expanding = false;
		expanded = false;
		shrinking = false;
	}

	/**
	 * Returns the x-coordinate of the ball for collision-checking and rendering.
	 * @return the x-coordinate of the ball
	 */
	public float getX(){
		return x;
	}

	/**
	 * Returns the y-coordinate of the ball for collision-checking and rendering.
	 * @return the y-coordinate of the ball
	 */
	public float getY(){
		return y;
	}

	/**
	 * Returns the radius of the ball at any point of the game.
	 * @return the current radius of the ball
	 */
	public int getRadius(){
		return radius;
	}

	/**
	 * Returns whether the ball has been expanded.
	 * @return whether the ball is expanded
	 */
	public boolean isExpanded(){
		return expanded;
	}

	/**
	 * Returns whether the ball is expanding.
	 * @return whether the ball is expanding
	 */
	public boolean isExpanding(){
		return expanding;
	}

	/**
	 * Returns the current timer of the ball
	 * @return the timer of the ball
	 */
	public long getTimer(){
		return timer;
	}

	/**
	 * Returns the RGBA color of the ball.
	 * @return color of the ball
	 */
	public Color getColor(){
		return color;
	}

	/**
	 * Returns the order of the ball.
	 * @return the order of the ball
	 */
	public int getOrder(){
		return order;
	}

	/**
	 * Returns whether the ball is shrinking (i.e. the timer is up)
	 * @return whether the ball is shrinking
	 */
	public boolean isShrinking(){
		return shrinking;
	}

	/**
	 * Returns the score value of the ball. THe value is calculated by the cube of the order.
	 * @return
	 */
	public int getScore(){
		return order * order * order;
	}

	/**
	 * Sets the x-coordinate of the ball.
	 * @param x the new x-coordinate of the ball
	 */
	public void setX(float x){
		this.x = x;
	}

	/**
	 * Sets the y-coordinate of the ball.
	 * @param y the new y-coordinate of the ball
	 */
	public void setY(float y){
		this.y = y;
	}

	/**
	 * Sets the radius of the ball.
	 * @param radius the nwe radius of the ball
	 */
	public void setRadius(int radius){
		this.radius = radius;
	}

	/**
	 * Sets whether the ball has been expanded or not
	 * @param expanded whether the ball has expanded
	 */
	public void setExpanded(boolean expanded){
		this.expanded = expanded;
	}

	/**
	 * Sets whether the ball is expanding.
	 * @param expanding whether the ball is expanding
	 */
	public void setExpanding(boolean expanding){
		this.expanding = expanding;
	}

	/**
	 * Updates the timer of the ball.
	 * @param timer the new timer value
	 */
	public void setTimer(long timer){
		this.timer = timer;
	}

	/**
	 * Sets whether this ball is shrinking or not.
	 * @param shrinking whether the ball is shrinking or not
	 */
	public void setShrinking(boolean shrinking){
		this.shrinking = shrinking;
	}

	/**
	 * THe expand function of the ball. The ball expands until it reaches the target radius.
	 * @param expandSpeed the expand speed of the ball
	 * @param expandRadius the target radius
	 */
	public void expand(int expandSpeed, int expandRadius){
		radius += expandSpeed;
		if(radius >= expandRadius){
			expanding = false;
			expanded = true;
		}
	}

	/**
	 * The shrink function of the ball. The ball shrinks until its radius reaches 0.
	 * @param shrinkSpeed the speed of shrinking of the ball
	 */
	public void shrink(int shrinkSpeed){
		if(radius > 0){
			radius -= shrinkSpeed;
			if(radius < 0){
				radius = 0;
			}
		}
	}

	/**
	 * Checks contact between two balls. By the distance, if the difference between the square of the differences
	 * between their x/y coordinates are smaller than square of the sum of their radii, the balls are in contact.
	 * This method checks contact between the ball passed in as a parameter and the ball that called this method.
	 * @param ball the ball to be compared with
	 * @return whether the bwo balls are in contact
	 */
	public boolean contactWith(Ball ball){
		double distanceX = Math.abs(x - ball.getX());
		double distanceY = Math.abs(y - ball.getY());
		double condition = radius + ball.getRadius();
		if(distanceX * distanceX + distanceY * distanceY <= condition * condition){
			return true;
		}
		return false;
	}

	/**
	 * Sets the order of the ball. Used when two balls are in contact.
	 * @param order
	 */
	public void setOrder(int order){
		this.order = order;
	}

	/**
	 * The move function of the ball. As the player-controlled ball and the floating balls have different update
	 * methods per frame, this method is abstract and will be implemented by this class's subclasses.
	 * @param gc
	 */
	public abstract void move(GameContainer gc);

	/**
	 * Checks if the ball is a GameBall (the floating balls).
	 * @return whether the ball is a GameBall
	 */
	public abstract boolean isGameBall();

}
