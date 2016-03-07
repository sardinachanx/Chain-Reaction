package menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Button{

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

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public boolean isEnabled(){
		return enabled;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	public abstract void clicked(GameContainer gc);

	public abstract void render(Graphics g);

}
