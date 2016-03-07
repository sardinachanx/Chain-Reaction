package main;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import levels.InfiniteLevel;
import levels.Level;
import levels.OriginalLevel;
import levels.SurvivalLevel;
import objects.Ball;
import objects.ExpandBall;
import objects.GameBall;

public class GameProcessor implements Processor{

	public static final int INITIAL_BALL_RADIUS = 8;
	public static final int INITIAL_EXPANDBALL_RADIUS = 50;
	public static final int EXPANDED_BALL_RADIUS = 50;
	public static final int MAX_TIMER = 3000;
	public static final int EXPAND_SPEED = 3;
	public static final int SHRINK_SPEED = 4;

	private static final int SCORE_X = 10;
	private static final int SCORE_Y = 10;
	private static final int OFFSET = 25;
	private static final int BALL_Y = SCORE_Y + OFFSET;
	private static final int MODE_Y = BALL_Y + OFFSET;
	private static final int SCORE_FACTOR = 100;

	protected Set<Ball> balls;
	protected Set<Ball> removed;
	protected ExpandBall expandBall;
	protected long score;
	protected long currentLevelScore;
	protected int ballsExpanded;
	protected Random random;

	protected GameMode gameMode;
	protected Level level;

	protected boolean started;
	protected boolean finished;
	protected boolean paused;
	protected boolean initialized;
	protected boolean debug;

	protected CoreProcessor cp;

	public GameProcessor(CoreProcessor cp, boolean debug){
		this.cp = cp;
		initialized = false;
		this.debug = debug;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		if(ballsExpanded >= level.getLevelThreshold()){
			g.setBackground(Color.darkGray.brighter());
		}
		else{
			g.setBackground(Color.darkGray);
		}
		g.clear();
		if(started){
			for(Ball ball : balls){
				g.setColor(ball.getColor());
				g.fillOval(ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(), ball.getRadius() * 2,
						ball.getRadius() * 2);
				if(!ball.isShrinking() && (ball.isExpanded() || ball.isExpanding())){
					g.setColor(Color.white);
					if(ball.isGameBall()){
						String ballScore = "+" + ball.getScore() * SCORE_FACTOR;
						g.drawString(ballScore, ball.getX() - g.getFont().getWidth(ballScore) / 2,
								ball.getY() - g.getFont().getHeight(ballScore) / 2);
					}
				}
			}
		}
		g.setColor(Color.white);
		g.drawString("Score: " + (currentLevelScore + score), SCORE_X, SCORE_Y);
		g.drawString(ballsExpanded + " out of " + level.getBallNum() + " expanded", SCORE_X, BALL_Y);
		g.drawString("Mode: " + gameMode.getName() + " Level " + level.getLevel(), SCORE_X, MODE_Y);
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		balls = new HashSet<Ball>();
		removed = new HashSet<Ball>();
		random = new Random();
		started = false;
		level = newLevelFromGameMode(GameMode.ORIGINAL);
		score = 0;
		paused = false;
		gameMode = GameMode.ORIGINAL;
		restart(gc);
		initialized = true;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		if(paused){
			return;
		}
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_0) && isDebug()){
			ballsExpanded = level.getLevelThreshold();
			started = true;
			finished = true;
			return;
		}
		if(!started){
			if(input.isKeyPressed(Input.KEY_A) && isDebug()){
				gameMode = GameMode.ORIGINAL;
				resetLevel();
				restart(gc);
			}
			if(input.isKeyPressed(Input.KEY_S) && isDebug()){
				gameMode = GameMode.SURVIVAL;
				resetLevel();
				restart(gc);
			}
			if(input.isKeyPressed(Input.KEY_D) && isDebug()){
				gameMode = GameMode.INFINTE;
				resetLevel();
				restart(gc);
			}
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !crossesMenu(input)){
				started = true;
			}
		}
		if(finished){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !crossesMenu(input)
					|| input.isKeyPressed(Input.KEY_SPACE)){
				restart(gc);
			}
			return;
		}
		boolean frameCheck = false;
		for(Ball ball : balls){
			if(!ball.isExpanded() && !ball.isExpanding()){
				ball.move(gc);
			}
			if(ball.isExpanding() || ball.isExpanded()){
				frameCheck = true;
				for(Ball others : balls){
					if(ball.contactWith(others) && !ball.equals(others)){
						if(!others.isExpanded() & !others.isExpanding()){
							others.setOrder(ball.getOrder() + 1);
							currentLevelScore += others.getScore() * SCORE_FACTOR;
							ballsExpanded++;
						}
						others.setExpanding(true);
					}
				}
				if(ball.isExpanding() && !ball.isExpanded()){
					ball.expand(EXPAND_SPEED, EXPANDED_BALL_RADIUS);
				}
				else{
					ball.setTimer(ball.getTimer() + delta);
					if(ball.getTimer() >= MAX_TIMER){
						if(!ball.isShrinking()){
							ball.setShrinking(true);
						}
						ball.shrink(SHRINK_SPEED);
					}
					if(ball.getRadius() <= 0){
						removed.add(ball);
					}
				}
			}
		}
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !expandBall.isExpanding() && !expandBall.isExpanded()
				&& !crossesMenu(input)){
			expandBall.startExpanding();
		}
		balls.removeAll(removed);
		removed.clear();
		if(!frameCheck && expandBall.isDone()){
			finished = true;
		}
	}

	public void setGameMode(GameMode gameMode){
		this.gameMode = gameMode;
	}

	public boolean isPaused(){
		return paused;
	}

	public void setPaused(boolean paused){
		this.paused = paused;
	}

	public void restart(GameContainer gc) throws SlickException{
		balls.clear();
		if(ballsExpanded >= level.getLevelThreshold()){
			level = level.getNextLevel();
			if(level.getLevel() > 1){
				score += currentLevelScore;
			}
			else{
				score = 0;
			}
		}
		else{
			if(gameMode == GameMode.SURVIVAL){
				level = newLevelFromGameMode(GameMode.SURVIVAL).getNextLevel();
			}
		}
		for(int i = 0; i < level.getBallNum(); i++){
			GameBall ball = new GameBall(INITIAL_BALL_RADIUS,
					new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)).brighter(), gc);
			balls.add(ball);
		}
		expandBall = new ExpandBall(INITIAL_EXPANDBALL_RADIUS, new Color(255, 255, 255, 96));
		balls.add(expandBall);
		removed.clear();
		finished = false;
		currentLevelScore = 0;
		ballsExpanded = 0;
	}

	private static Level newLevelFromGameMode(GameMode gameMode){
		switch(gameMode){
			case SURVIVAL:
				return new SurvivalLevel(0);
			case INFINTE:
				return new InfiniteLevel(0);
			case ORIGINAL:
				return new OriginalLevel(0);
			default:
				throw new IllegalArgumentException("Game Mode unsupported.");

		}
	}

	@Override
	public boolean initialized(){
		return initialized;
	}

	@Override
	public int order(){
		return 1;
	}

	private boolean crossesMenu(Input input){
		return input.getMouseX() > GUIProcessor.GAMEMENU_X && input.getMouseY() < GUIProcessor.GAMEMENU_Y;
	}

	public boolean isDebug(){
		return debug;
	}

	public void setDebug(boolean debug){
		this.debug = debug;
	}

	public void resetLevel(){
		ballsExpanded = level.getLevelThreshold();
		level = newLevelFromGameMode(gameMode);
		score = 0;
	}

	public void setStarted(boolean started){
		this.started = started;
	}

	public boolean isStarted(){
		return started;
	}
}
