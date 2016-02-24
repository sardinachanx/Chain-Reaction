package objects;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

public class GameBall extends Ball{

	private static final float SPEED_FACTOR = 1.5f;
	private static final float SPEED_INCREASE = 0.5f;
	private static final float EXPAND_TRANSPARENCY = 0.65f;

	protected float speedX;
	protected float speedY;
	protected boolean colorChanged;

	public GameBall(GameContainer gc, int initialRadius){
		super(initialRadius);
		Random rand = new Random();
		x = rand.nextInt(gc.getWidth() - radius * 2) + radius;
		y = rand.nextInt(gc.getHeight() - radius * 2) + radius;
		speedX = SPEED_FACTOR * rand.nextFloat() + SPEED_INCREASE;
		speedY = SPEED_FACTOR * rand.nextFloat() + SPEED_INCREASE;
		color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)).brighter();
		colorChanged = false;
	}

	public float getSpeedX(){
		return speedX;
	}

	public float getSpeedY(){
		return speedY;
	}

	public void setSpeedX(float speedX){
		this.speedX = speedX;
	}

	public void setSpeedY(float speedY){
		this.speedY = speedY;
	}

	@Override
	public void expand(int expandSpeed, int expandRadius){
		if(!colorChanged){
			colorChanged = true;
			color = new Color(color.r, color.g, color.b, EXPAND_TRANSPARENCY);
		}
		super.expand(expandSpeed, expandRadius);

	}

	@Override
	public void move(GameContainer gc){
		x += speedX;
		if(x > gc.getWidth() - radius || x < radius){
			speedX *= -1;
		}
		y += speedY;
		if(y > gc.getHeight() - radius || y < radius){
			speedY *= -1;
		}
	}

	@Override
	public boolean isGameBall(){
		return true;
	}

}
