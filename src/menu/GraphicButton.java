package menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * The GraphicButton extends Button. It takes in Image files and renders them as buttons, giving them
 * a "click" function.
 * @author tchan17
 *
 */
public abstract class GraphicButton extends Button{

	Image image;

	public GraphicButton(int x, int y, Image image){
		this(x, y, true, image);
	}

	public GraphicButton(int x, int y, boolean enabled, Image image){
		super(x, y, 0, 0, enabled);
		width = image.getWidth();
		height = image.getHeight();
		this.image = image;
	}

	/**
	 * Gets the image of this GraphicButton.
	 * @return the image of the button
	 */
	public Image getImage(){
		return image;
	}

	/**
	 * Sets the image of this GraphicButton.
	 * @param image the new image of this button
	 */
	public void setImage(Image image){
		this.image = image;
	}

	@Override
	public void render(Graphics g){
		g.drawImage(image, x - width / 2, y - height / 2);
	}

}
