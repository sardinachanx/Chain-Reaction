package processors;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import highscore.HighScore;
import levels.InfiniteLevel;
import levels.Level;
import levels.OriginalLevel;
import levels.SurvivalLevel;
import main.GameEngine;
import main.GameMode;
import main.GraphicsEditor;
import objects.Ball;
import objects.ExpandBall;
import objects.GameBall;

public class GameProcessor implements Processor{

	protected static final int INITIAL_BALL_RADIUS = 8;
	protected static final int INITIAL_EXPANDBALL_RADIUS = 50;
	protected static final int EXPANDED_BALL_RADIUS = 50;
	protected static final int MAX_TIMER = 3000;
	protected static final int EXPAND_SPEED = 3;
	protected static final int SHRINK_SPEED = 4;

	private static final int SCORE_X = 10;
	private static final int SCORE_Y = 10;
	private static final int OFFSET = 25;
	private static final int BALL_Y = SCORE_Y + OFFSET;
	private static final int MODE_Y = BALL_Y + OFFSET;
	private static final int SCORE_FACTOR = 100;

	private static final String LEVEL_PASSED = "click anywhere or press space for the next level";
	private static final String LEVEL_FAILED = "click anywhere or press space to replay level";
	private static final String NAME_INPUT = "enter your name and press enter: ";
	private static final int END_OFFSET = 45;

	protected Set<Ball> balls;
	protected Set<Ball> removed;
	protected ExpandBall expandBall;
	protected long score;
	protected long currentLevelScore;
	protected int ballsExpanded;
	protected Random random;
	protected int lives;

	protected GameMode gameMode;
	protected Level level;

	protected String name;

	protected boolean started;
	protected boolean finished;
	protected boolean paused;
	protected boolean initialized;
	protected boolean debug;
	protected boolean needsInput;
	protected boolean inputDone;

	protected CoreProcessor cp;

	public GameProcessor(CoreProcessor cp, boolean debug){
		initialized = false;
		this.cp = cp;
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
						g.drawString(ballScore, GraphicsEditor.getCenterX(ballScore, ball.getX(), g),
								GraphicsEditor.getCenterY(ballScore, ball.getY(), g));
					}
				}
			}
		}
		g.setColor(Color.white);
		g.drawString("score: " + (currentLevelScore + score), SCORE_X, SCORE_Y);
		g.drawString(
				ballsExpanded + " out of " + level.getBallNum() + " expanded"
						+ (gameMode == GameMode.INFINITE ? "" : " (" + level.getLevelThreshold() + " needed)"),
				SCORE_X, BALL_Y);
		g.drawString("mode: " + gameMode.getName().toLowerCase() + " level " + level.getLevelNumber(), SCORE_X, MODE_Y);
		if(finished){
			boolean drawExpanded = true;
			if(ballsExpanded >= level.getLevelThreshold()){
				if(level.getLevelNumber() != 12){
					g.drawString(LEVEL_PASSED, GraphicsEditor.getCenterX(LEVEL_PASSED, GameEngine.WIDTH / 2, g),
							GraphicsEditor.getCenterY(LEVEL_PASSED, GameEngine.HEIGHT / 2, g) + END_OFFSET);
				}
				else{
					if(needsInput){
						String inputString = NAME_INPUT + name;
						g.drawString(inputString, GraphicsEditor.getCenterX(inputString, GameEngine.WIDTH / 2, g),
								GraphicsEditor.getCenterY(inputString, GameEngine.HEIGHT / 2, g) + END_OFFSET);
					}
					else{
						drawExpanded = false;
					}
				}
			}
			else{
				if(gameMode != GameMode.SURVIVAL){
					g.drawString(LEVEL_FAILED, GraphicsEditor.getCenterX(LEVEL_FAILED, GameEngine.WIDTH / 2, g),
							GraphicsEditor.getCenterY(LEVEL_FAILED, GameEngine.HEIGHT / 2, g) + END_OFFSET);
				}
				else{
					if(needsInput){
						String inputString = NAME_INPUT + name;
						g.drawString(inputString, GraphicsEditor.getCenterX(inputString, GameEngine.WIDTH / 2, g),
								GraphicsEditor.getCenterY(inputString, GameEngine.HEIGHT / 2, g) + END_OFFSET);
					}
					else{
						drawExpanded = false;
					}
				}
			}
			if(drawExpanded){
				String s = ballsExpanded + " out of " + level.getBallNum() + " expanded";
				g.drawString(s, GraphicsEditor.getCenterX(s, GameEngine.WIDTH / 2, g),
						GraphicsEditor.getCenterY(s, GameEngine.HEIGHT / 2, g));
			}
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		name = "";
		balls = new HashSet<Ball>();
		removed = new HashSet<Ball>();
		random = new Random();
		started = false;
		level = newLevelFromGameMode(GameMode.ORIGINAL);
		score = 0;
		paused = false;
		gameMode = GameMode.ORIGINAL;
		restart();
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
			if(cp.isDebug()){
				if(cp.clicked() && !crossesMenu(input)){
					started = true;
				}
			}
			else{
				started = true;
			}
		}
		if(finished){
			if(cp.clicked() && !crossesMenu(input) || input.isKeyDown(Input.KEY_SPACE)){
				cp.setHighScoreTableProcessorState(false);
				restart();
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
		if(cp.clicked() && !expandBall.isExpanding() && !expandBall.isExpanded() && !crossesMenu(input)){
			expandBall.startExpanding();
		}
		balls.removeAll(removed);
		removed.clear();
		if(!frameCheck && expandBall.isDone()){
			finished = true;
		}
	}

	public GameMode getGameMode(){
		return gameMode;
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

	public void restart(){
		balls.clear();
		if(ballsExpanded >= level.getLevelThreshold()){
			if(level.getLevelNumber() == 12 && gameMode == GameMode.ORIGINAL && !inputDone){
				needsInput = true;
				return;
			}
			else{
				inputDone = false;
				level = level.getNextLevel();
				if(level.getLevelNumber() > 1){
					score += currentLevelScore;
				}
				else{
					score = 0;
				}
			}
		}
		else{
			lives++;
			if(gameMode == GameMode.SURVIVAL && !inputDone){
				needsInput = true;
				return;
			}
			else{
				inputDone = false;
			}
		}
		for(int i = 0; i < level.getBallNum(); i++){
			GameBall ball = new GameBall(INITIAL_BALL_RADIUS,
					new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)).brighter());
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
			case INFINITE:
				return new InfiniteLevel(0);
			case ORIGINAL:
				return new OriginalLevel(0);
			default:
				throw new IllegalArgumentException("Game Mode unsupported.");

		}
	}

	private void loseSurvival(){
		resetLevel();
		cp.getCurrentAudio().setPaused(true);
		cp.getCurrentAudio().setRestart(true);
		if(cp.isAudioOn()){
			cp.getCurrentAudio().setPaused(false);
		}
		restart();
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
		level = newLevelFromGameMode(gameMode);
		ballsExpanded = level.getLevelThreshold();
		score = 0;
		lives = 0;
	}

	public void setStarted(boolean started){
		this.started = started;
	}

	public boolean isStarted(){
		return started;
	}

	public boolean needsInput(){
		return needsInput;
	}

	public void receiveInput(int code, char c){
		if(!needsInput()){
			return;
		}
		if(code == Input.KEY_BACK || code == Input.KEY_DELETE){
			if(name.length() > 0){
				name = name.substring(0, name.length() - 1);
			}
		}
		else if(code == Input.KEY_ESCAPE){
			needsInput = false;
			inputDone = true;
			name = "";
		}
		else if(code == Input.KEY_ENTER){
			needsInput = false;
			score += currentLevelScore;
			currentLevelScore = 0;
			if(name.startsWith(" ")){
				name = name.substring(1);
			}
			if(gameMode == GameMode.ORIGINAL){
				cp.getHsp().getOriginal().addHighScore(new HighScore(name, score, lives));
				cp.getHsp().save();
			}
			else if(gameMode == GameMode.SURVIVAL){
				cp.getHsp().getSurvival().addHighScore(new HighScore(name, score, level.getLevelNumber()));
				cp.getHsp().save();
				loseSurvival();
			}
			cp.getHsp().setInGame(true);
			cp.setHighScoreTableProcessorState(true);
			inputDone = true;
			name = "";
		}
		else{
			name += c;
		}
	}

}
