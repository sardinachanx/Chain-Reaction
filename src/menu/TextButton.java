package menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import main.GraphicsEditor;

/**
 * An abstract TextButton class. A TextButton is required to have a String as part of the message displayed.
 * @author tchan17
 *
 */
public abstract class TextButton extends Button{

	private static final int DEFAULT_WIDTH = 50;
	private static final int DEFAULT_HEIGHT = 30;

	protected String text;
	protected int borderThickness;

	protected Color buttonColor;
	protected Color textColor;
	protected Color borderColor;

	/**
	 * Basic constructor for a TextButton. Uses default width, height, color, border and text.
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 */
	public TextButton(int x, int y){
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Simple constructor for a TextButton. Uses default color, border and text.
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param width the width of the button
	 * @param height the height of the button
	 */
	public TextButton(int x, int y, int width, int height){
		this(x, y, width, height, true);
	}

	/**
	 * Constructor for a TextButton. Uses default color, border and text.
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param width the width of the button
	 * @param height the height of the button
	 * @param enabled whether the button is enabled upon initialization
	 */
	public TextButton(int x, int y, int width, int height, boolean enabled){
		this(x, y, width, height, enabled, "");
	}

	/**
	 * Constructor for a TextButton. Uses default border and color.
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param width the width of the button
	 * @param height the height of the button
	 * @param enabled whether the button is enabled upon initialization
	 * @param text the text to be displayed by the button
	 */
	public TextButton(int x, int y, int width, int height, boolean enabled, String text){
		this(x, y, width, height, enabled, text, Color.white, Color.black);
	}

	/**
	 * Constructor for a TextButton. Uses default border.
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param width the width of the button
	 * @param height the height of the button
	 * @param enabled whether the button is enabled upon initialization
	 * @param text the text to be displayed by the button
	 * @param buttonColor the color of the button
	 * @param textColor the text color of the button
	 */
	public TextButton(int x, int y, int width, int height, boolean enabled, String text, Color buttonColor,
			Color textColor){
		this(x, y, width, height, enabled, text, 0, buttonColor, textColor, Color.black);
	}

	/**Constructor for a TextButton. Uses default border.
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param width the width of the button
	 * @param height the height of the button
	 * @param enabled whether the button is enabled upon initialization
	 * @param text the text to be displayed by the button
	 * @param borderThickness the thickness of the border of the button
	 * @param buttonColor the color of the button
	 * @param textColor the text color of the button
	 * @param borderColor the color of the border of the button
	 */
	public TextButton(int x, int y, int width, int height, boolean enabled, String text, int borderThickness,
			Color buttonColor, Color textColor, Color borderColor){
		super(x, y, width, height, enabled);
		this.text = text;
		this.borderThickness = borderThickness;
		this.buttonColor = buttonColor;
		this.textColor = textColor;
		this.borderColor = borderColor;
	}

	/**
	 * Gets the text of this button.
	 * @return the text of this button
	 */
	public String getText(){
		return text;
	}

	/**
	 * Gets the thickness of the border of this button.
	 * @return the thickness of the border of this button
	 */
	public int getBorderThickness(){
		return borderThickness;
	}

	/**
	 * Gets the color of the button itself.
	 * @return the color of the button
	 */
	public Color getButtonColor(){
		return buttonColor;
	}

	/**
	 * Gets the text color of the button.
	 * @return the text color
	 */
	public Color getTextColor(){
		return textColor;
	}

	/**
	 * Gets the color of the border of the button.
	 * @return the color of the border
	 */
	public Color getBorderColor(){
		return borderColor;
	}

	/**
	 * Sets the text of the button.
	 * @param text the new text of the button
	 */
	public void setText(String text){
		this.text = text;
	}

	/**
	 * Sets the border thickness of the button.
	 * @param borderThickness the new border thickness
	 */
	public void setBorderThickness(int borderThickness){
		this.borderThickness = borderThickness;
	}

	/**
	 * Sets the color of the button itself.
	 * @param buttonColor the new color of the button
	 */
	public void setButtonColor(Color buttonColor){
		this.buttonColor = buttonColor;
	}

	/**
	 * Sets the color of the text in the button.
	 * @param textColor the new text color
	 */
	public void setTextColor(Color textColor){
		this.textColor = textColor;
	}

	/**
	 * Sets the color of the border.
	 * @param borderColor the new border color
	 */
	public void setBorderColor(Color borderColor){
		this.borderColor = borderColor;
	}

	@Override
	public void render(Graphics g){
		g.setColor(borderColor);
		g.fillRect(x - (width + 2 * borderThickness) / 2, y - (height + 2 * borderThickness) / 2,
				width + 2 * borderThickness, height + 2 * borderThickness);
		g.setColor(buttonColor);
		g.fillRect(x - width / 2, y - height / 2, width, height);
		g.setColor(textColor);
		g.drawString(text, GraphicsEditor.getCenterX(text, x, g), GraphicsEditor.getCenterY(text, y, g));
	}

}
