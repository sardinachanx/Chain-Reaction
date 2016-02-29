package menu;

public abstract class Button{

	protected int x;
	protected int y;
	protected String text;
	protected int sizeX;
	protected int sizeY;

	public Button(int x, int y, int sizeX, int sizeY, String text){
		this.x = x;
		this.y = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.text = text;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getSizeX(){
		return sizeX;
	}

	public int getSizeY(){
		return sizeY;
	}

	public String getText(){
		return text;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setSizeX(int sizeX){
		this.sizeX = sizeX;
	}

	public void setSizeY(int sizeY){
		this.sizeY = sizeY;
	}

	public void setText(String text){
		this.text = text;
	}

	protected abstract void clicked();
}
