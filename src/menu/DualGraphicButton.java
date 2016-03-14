package menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * The DualGraphicsButton class extends the GraphicButton class. The two classes differ in that this class
 * provides for the opportunity to switch between two images on click.
 * @author tchan17
 *
 */
public abstract class DualGraphicButton extends GraphicButton{

	Image imageTwo;
	Image[] images;
	private int currentImage;

	/**
	 * Simple constructor for a DualGraphicButton.
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param imageOne the default image of the button
	 * @param imageTwo the secondary image of the button
	 */
	public DualGraphicButton(int x, int y, Image imageOne, Image imageTwo){
		this(x, y, true, imageOne, imageTwo);
	}

	/**
	 * Constructor for a DualGraphicButton
	 * @param x the x-coordinate of the button
	 * @param y the y-coordinate of the button
	 * @param enabled whether the button is turned on
	 * @param imageOne the default image of the button
	 * @param imageTwo the secondary image of the button
	 */
	public DualGraphicButton(int x, int y, boolean enabled, Image imageOne, Image imageTwo){
		super(x, y, true, imageOne);
		this.imageTwo = imageTwo;
		images = new Image[2];
		images[0] = image;
		images[1] = imageTwo;
	}

	@Override
	public void setImage(Image imageOne){
		super.setImage(imageOne);
		images[0] = imageOne;
	}

	/**
	 * Gets the secondary image of the button.
	 * @return secondary image of the button
	 */
	public Image getImageTwo(){
		return imageTwo;
	}

	/**
	 * Sets the secondary image of the button
	 * @param imageTwo the secondary image of the button
	 */
	public void setImageTwo(Image imageTwo){
		this.imageTwo = imageTwo;
		images[1] = imageTwo;
	}

	/**
	 * Gets the current image of the button (i.e. the one being displayed)
	 * @return the current image of the button
	 */
	public int getCurrentImage(){
		return currentImage;
	}

	/**
	 * Sets the current image of the button
	 * @param currentImage the index of new image in the list of images (0, 1)
	 */
	public void setCurrentImage(int currentImage){
		this.currentImage = currentImage;
	}

	@Override
	public void render(Graphics g){
		g.drawImage(images[getCurrentImage()], x - width / 2, y - height / 2);
	}

}
