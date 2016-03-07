package menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public abstract class TextButton extends Button{

	private static final int DEFAULT_WIDTH = 50;
	private static final int DEFAULT_HEIGHT = 30;

	protected String text;
	protected int borderThickness;

	protected Color buttonColor;
	protected Color textColor;
	protected Color borderColor;

	public TextButton(int x, int y){
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public TextButton(int x, int y, int width, int height){
		this(x, y, width, height, true);
	}

	public TextButton(int x, int y, int width, int height, boolean enabled){
		this(x, y, width, height, enabled, "");
	}

	public TextButton(int x, int y, int width, int height, boolean enabled, String text){
		this(x, y, width, height, enabled, text, Color.white, Color.black);
	}

	public TextButton(int x, int y, int width, int height, boolean enabled, String text, Color buttonColor,
			Color textColor){
		this(x, y, width, height, enabled, text, 0, buttonColor, textColor, Color.black);
	}

	public TextButton(int x, int y, int width, int height, boolean enabled, String text, int borderThickness,
			Color buttonColor, Color textColor, Color borderColor){
		super(x, y, width, height, enabled);
		this.text = text;
		this.borderThickness = borderThickness;
		this.buttonColor = buttonColor;
		this.textColor = textColor;
		this.borderColor = borderColor;
	}

	public String getText(){
		return text;
	}

	public int getBorderThickness(){
		return borderThickness;
	}

	public Color getButtonColor(){
		return buttonColor;
	}

	public Color getTextColor(){
		return textColor;
	}

	public Color getBorderColor(){
		return borderColor;
	}

	public void setText(String text){
		this.text = text;
	}

	public void setBorderThickness(int borderThickness){
		this.borderThickness = borderThickness;
	}

	public void setButtonColor(Color buttonColor){
		this.buttonColor = buttonColor;
	}

	public void setTextColor(Color textColor){
		this.textColor = textColor;
	}

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
		g.drawString(text, x - g.getFont().getWidth(text) / 2, y - g.getFont().getHeight(text) / 2);
	}

}
