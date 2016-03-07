package menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class GraphicButton extends Button{

	private static final int DEFAULT_HEIGHT = 50;
	private static final int DEFAULT_WIDTH = 50;

	Image image;

	public GraphicButton(int x, int y, Image image){
		this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, image);
	}

	public GraphicButton(int x, int y, int width, int height, Image image){
		this(x, y, width, height, true, image);
	}

	public GraphicButton(int x, int y, int width, int height, boolean visible, Image image){
		super(x, y, width, height, visible);
		this.image = image;
		image = image.getScaledCopy(width, height);
	}

	public Image getImage(){
		return image;
	}

	public void setImage(Image image){
		this.image = image;
	}

	@Override
	public void render(Graphics g){
		g.drawImage(image, x, y);
	}

}
