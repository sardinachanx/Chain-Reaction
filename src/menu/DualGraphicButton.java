package menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class DualGraphicButton extends GraphicButton{

	Image imageTwo;
	Image[] images;
	private int currentImage;

	public DualGraphicButton(int x, int y, Image imageOne, Image imageTwo){
		this(x, y, true, imageOne, imageTwo);
	}

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

	public Image getImageTwo(){
		return imageTwo;
	}

	public void setImageTwo(Image imageTwo){
		this.imageTwo = imageTwo;
		images[1] = imageTwo;
	}

	public int getCurrentImage(){
		return currentImage;
	}

	public void setCurrentImage(int currentImage){
		this.currentImage = currentImage;
	}

	@Override
	public void render(Graphics g){
		g.drawImage(images[getCurrentImage()], x - width / 2, y - height / 2);
	}

}
