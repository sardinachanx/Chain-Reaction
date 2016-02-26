package main;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import objects.Ball;
import objects.ExpandBall;
import objects.GameBall;

public class GameFrame extends BasicGame{

	public static final int INITIAL_BALL_RADIUS = 8;
	public static final int INITIAL_EXPANDBALL_RADIUS = 50;
	public static final int EXPANDED_BALL_RADIUS = 50;
	public static final int MAX_TIMER = 3000;
	public static final int EXPAND_SPEED = 3;
	public static final int SHRINK_SPEED = 4;
	public static final int BALL_NUM = 100;

	private static final int SCORE_X = 10;
	private static final int SCORE_Y = 10;
	private static final int SCORE_FACTOR = 1000;

	protected Set<Ball> balls;
	protected Set<Ball> removed;
	protected ExpandBall expandBall;
	protected boolean allFinished;
	protected int score;

	public GameFrame(){
		super("ChainReaction");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.setBackground(Color.darkGray);
		g.clear();
		for(Ball ball : balls){
			g.setColor(ball.getColor());
			g.fillOval(ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(), ball.getRadius() * 2,
					ball.getRadius() * 2);
		}
		g.setColor(Color.white);
		g.drawString("Score: " + score, SCORE_X, SCORE_Y);
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		balls = new HashSet<Ball>();
		for(int i = 0; i < BALL_NUM; i++){
			GameBall ball = new GameBall(gc, INITIAL_BALL_RADIUS);
			balls.add(ball);
		}
		expandBall = new ExpandBall(INITIAL_EXPANDBALL_RADIUS);
		balls.add(expandBall);
		removed = new HashSet<Ball>();
		allFinished = false;
		score = 0;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		if(allFinished){
			return;
		}
		Input input = gc.getInput();
		boolean frameCheck = false;
		for(Ball ball : balls){
			if(!ball.isExpanded() && !ball.isExpanding()){
				ball.move(gc);
			}
			if(ball.isExpanding() || ball.isExpanded()){
				frameCheck = true;
				for(Ball others : balls){
					if(ball.contactWith(others) && !ball.equals(others)){
						others.setExpanding(true);
					}
				}
				if(ball.isExpanding() && !ball.isExpanded()){
					ball.expand(EXPAND_SPEED, EXPANDED_BALL_RADIUS);
				}
				else{
					ball.setTimer(ball.getTimer() + delta);
					if(ball.getTimer() >= MAX_TIMER){
						ball.shrink(SHRINK_SPEED);
					}
					if(ball.getRadius() <= 0){
						removed.add(ball);
					}
				}
			}
		}
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !expandBall.isExpanding() && !expandBall.isExpanded()){
			expandBall.startExpanding();
		}
		balls.removeAll(removed);
		removed.clear();
		if(!frameCheck && expandBall.isDone()){
			allFinished = true;
		}
	}
}
