package menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * The abstract class Button is used for anything "clickable", or anything that triggers an action when clicked.
 * This includes text menu options, menu buttons and others. The action to be performed upon clicking is different
 * for each button, so the clicked() method is declared separately for each button upon creation.
 * @author tchan17
 *
 */
public abstract class Button{

	//The basic attributes of a button.
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean enabled;

	public Button(int x, int y, int width, int height, boolean enabled){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabled = enabled;
	}

	/**
	 * A method to get the x-coordinate of the button.
	 * @return the x-coordinate of the button
	 */
	public int getX(){
		return x;
	}

	/**
	 * A method to get the y-coordinate of the button.
	 * @return the y-coordinate of the button
	 */
	public int getY(){
		return y;
	}

	/**
	 * A method to get the width of the button.
	 * @return the width of the button
	 */
	public int getWidth(){
		return width;
	}

	/**
	 * A method to get the height of the button.
	 * @return the height of the button
	 */
	public int getHeight(){
		return height;
	}

	/**
	 * A method to check whether this button is enabled.
	 * @return whether the button is enabled
	 */
	public boolean isEnabled(){
		return enabled;
	}

	/**
	 * Sets the x-coordinate of the button.
	 * @param x the new x-coordinate of the button
	 */
	public void setX(int x){
		this.x = x;
	}

	/**
	 * Sets the y-coordinate of the button.
	 * @param y the new y-coordinate of the button
	 */
	public void setY(int y){
		this.y = y;
	}

	/**
	 * Sets the width of the button.
	 * @param width the new width of the button
	 */
	public void setWidth(int width){
		this.width = width;
	}

	/**
	 * Sets the height of the button.
	 * @param height the new height of the button
	 */
	public void setHeight(int height){
		this.height = height;
	}

	/**
	 * Sets whether the button is enabled.
	 * @param enabled whether the button is enabled
	 */
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	/**
	 * The action to be performed upon clicking of the button. This method is kept abstract for this class
	 * and all subclasses such that all buttons are required to implement this method upon declaration.
	 * @param gc the GameContainer for the buttons
	 */
	public abstract void clicked(GameContainer gc);

	/**
	 * The render function of the button. As the render is different for text buttons and image buttons, this
	 * method is kept abstract in this superclass.
	 * @param g
	 */
	public abstract void render(Graphics g);

}
