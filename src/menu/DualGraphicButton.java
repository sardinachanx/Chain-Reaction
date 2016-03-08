package menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class DualGraphicButton extends GraphicButton{

	Image imageTwo;
	Image[] images;
	int currentImage;

	public DualGraphicButton(int x, int y, boolean enabled, Image imageOne, Image imageTwo){
		super(x, y, enabled, imageOne);
		this.imageTwo = imageTwo;
		images = new Image[2];
	}

	public Image getImageTwo(){
		return imageTwo;
	}

	public void setImageTwo(Image imageTwo){
		this.imageTwo = imageTwo;
	}

	@Override
	public void render(Graphics g){
		g.drawImage(images[currentImage], x - width / 2, y - height / 2);
	}

}
