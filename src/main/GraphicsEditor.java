package main;

import org.newdawn.slick.Graphics;

/**
 * A graphics formatter. As Slick's coordinates denote the top left hand corner instead of the
 * center, these static methods are used to calculate the center x and y coordinates.
 * @author tchan17
 *
 */
public class GraphicsEditor{

	/**
	 * Gets the center x-coordinate of the object.
	 * @param s text to find the center of
	 * @param x original x-coordinate
	 * @param g the Graphics of the game
	 * @return the x-coordinate of the center of the object
	 */
	public static int getCenterX(String s, int x, Graphics g){
		return x - g.getFont().getWidth(s) / 2;
	}

	/**
	 * Gets the center y-coordinate of the object.
	 * @param s text to find the center of
	 * @param y original x-coordinate
	 * @param g the Graphics of the game
	 * @return the x-coordinate of the center of the object
	 */
	public static int getCenterY(String s, int y, Graphics g){
		return y - g.getFont().getHeight(s) / 2;
	}

	/**
	 * Gets the center x-coordinate of the object.
	 * @param s text to find the center of
	 * @param x original x-coordinate
	 * @param g the Graphics of the game
	 * @return the x-coordinate of the center of the object
	 */
	public static float getCenterX(String s, float x, Graphics g){
		return x - g.getFont().getWidth(s) / 2;
	}

	/**
	 * Gets the center y-coordinate of the object.
	 * @param s text to find the center of
	 * @param y original x-coordinate
	 * @param g the Graphics of the game
	 * @return the x-coordinate of the center of the object
	 */
	public static float getCenterY(String s, float y, Graphics g){
		return y - g.getFont().getHeight(s) / 2;
	}

}
