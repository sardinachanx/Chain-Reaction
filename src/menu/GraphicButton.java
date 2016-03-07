package menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

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

	public Image getImage(){
		return image;
	}

	public void setImage(Image image){
		this.image = image;
	}

	@Override
	public void render(Graphics g){
		g.drawImage(image, x - width / 2, y - height / 2);
	}

}
