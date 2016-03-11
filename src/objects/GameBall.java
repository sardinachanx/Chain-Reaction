package objects;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import main.GameEngine;

public class GameBall extends Ball{

	private static final float SPEED_CONSTANT = 1.5f;
	private static final float SPEED_MIN = 0.5f;
	private static final float EXPAND_TRANSPARENCY = 0.65f;

	protected float speedX;
	protected float speedY;
	protected boolean colorChanged;

	public GameBall(int initialRadius, Color color){
		super(initialRadius, color);
		Random rand = new Random();
		x = rand.nextInt(GameEngine.WIDTH - radius * 2) + radius;
		y = rand.nextInt(GameEngine.HEIGHT - radius * 2) + radius;
		speedX = generateSpeed();
		speedY = generateSpeed();
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

	private static float generateSpeed(){
		float speed = (float) (Math.random() * SPEED_CONSTANT + SPEED_MIN);
		if(Math.random() < 0.5){
			speed = -speed;
		}
		return speed;
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
		if(x > GameEngine.WIDTH - radius || x < radius){
			speedX *= -1;
		}
		y += speedY;
		if(y > GameEngine.HEIGHT - radius || y < radius){
			speedY *= -1;
		}
	}

	@Override
	public boolean isGameBall(){
		return true;
	}

}
