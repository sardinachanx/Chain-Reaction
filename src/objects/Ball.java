package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

public abstract class Ball{

	protected float x;
	protected float y;
	protected int radius;
	protected boolean expanding;
	protected boolean expanded;
	protected long timer;
	protected Color color;

	public Ball(int initialRadius){
		radius = initialRadius;
		expanding = false;
		expanded = false;
	}

	public float getX(){
		return x;
	}

	public float getY(){
		return y;
	}

	public int getRadius(){
		return radius;
	}

	public boolean isExpanded(){
		return expanded;
	}

	public boolean isExpanding(){
		return expanding;
	}

	public long getTimer(){
		return timer;
	}

	public Color getColor(){
		return color;
	}

	public void setX(float x){
		this.x = x;
	}

	public void setY(float y){
		this.y = y;
	}

	public void setRadius(int radius){
		this.radius = radius;
	}

	public void setExpanded(boolean expanded){
		this.expanded = expanded;
	}

	public void setExpanding(boolean expanding){
		this.expanding = expanding;
	}

	public void setTimer(long timer){
		this.timer = timer;
	}

	public void expand(int expandSpeed, int expandRadius){
		radius += expandSpeed;
		if(radius >= expandRadius){
			expanding = false;
			expanded = true;
		}
	}

	public void shrink(int shrinkSpeed){
		if(radius > 0){
			radius -= shrinkSpeed;
			if(radius < 0){
				radius = 0;
			}
		}
	}

	public boolean contactWith(Ball b){
		double distanceX = Math.abs(x - b.getX());
		double distanceY = Math.abs(y - b.getY());
		double condition = radius + b.getRadius();
		if(distanceX * distanceX + distanceY * distanceY <= condition * condition){
			return true;
		}
		return false;
	}

	public abstract void move(GameContainer gc);

	public abstract boolean isGameBall();

}
