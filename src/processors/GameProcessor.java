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

/**
 * The GameProcessor. Controls the game movements (the actual game).
 * @author tchan17
 *
 */
public class GameProcessor implements Processor{

	//The radius of the balls unexpanded.
	public static final int INITIAL_BALL_RADIUS = 8;
	//The radius of the balls expanded.
	public static final int INITIAL_EXPANDBALL_RADIUS = 50;
	//The radius of the ball to be controlled by the player.
	public static final int EXPANDED_BALL_RADIUS = 50;
	//The time after which the expanded balls will disappear.
	public static final int MAX_TIMER = 3000;
	//The expanding speed of the ball.
	public static final int EXPAND_SPEED = 3;
	//The shrinking speed of the ball.
	public static final int SHRINK_SPEED = 4;

	//X-Coordinate of the score.
	private static final int SCORE_X = 10;
	//Y-Coordinate of the score.
	private static final int SCORE_Y = 10;
	//The line width of the display.
	private static final int OFFSET = 25;
	//Y-Coordinate of the fraction display.
	private static final int BALL_Y = SCORE_Y + OFFSET;
	//Y-Coordinate of the mode display.
	private static final int MODE_Y = BALL_Y + OFFSET;
	//The multiplier of the score.
	private static final int SCORE_FACTOR = 100;

	//The message displayed if the level is passed.
	private static final String LEVEL_PASSED = "click anywhere or press space for the next level";
	//The message displayed if the level is not passed.
	private static final String LEVEL_FAILED = "click anywhere or press space to replay level";
	//The message displayed to prompt a high score entry input.
	private static final String NAME_INPUT = "enter your name and press enter: ";
	//The offset of the final display of high score table.
	private static final int END_OFFSET = 45;

	//The set of balls in the game.
	protected Set<Ball> balls;
	//The set of balls to be removed at the end of each frame.
	protected Set<Ball> removed;
	//The ball to be controlled by the user.
	protected ExpandBall expandBall;
	//The cumulative score of the current game.
	protected long score;
	//The score of the current level.
	protected long currentLevelScore;
	//The number of balls expanded.
	protected int ballsExpanded;
	//The random needed to generate random numbers.
	protected Random random;
	//The amount of lives used.
	protected int lives;

	//Current GameMode.
	protected GameMode gameMode;
	//Current level.
	protected Level level;

	//The name for high score entries.
	protected String name;

	//Whether a level has started.
	protected boolean started;
	//Whether a level has finished.
	protected boolean finished;
	//Whether a level was paused.
	protected boolean paused;
	//Whether the GameProcessor has been initialized.
	protected boolean initialized;
	//Whether debug mode is on.
	protected boolean debug;
	//Whether the score needs input.
	protected boolean needsInput;
	//Whether the input is done.
	protected boolean inputDone;

	//The CoreProcessor associated with this instance of GameProcessor.
	protected CoreProcessor cp;

	/**
	 * Creates an uninitialized GameProcessor.
	 * @param cp the CoreProcessor associated with this GameProcessor
	 * @param debug whether debug mode is on
	 */
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
			started = true;
		}
		if(finished){
			if(cp.clicked() && !crossesMenu(input) || input.isKeyDown(Input.KEY_SPACE)){
				cp.setHighScoreTableProcessorState(false);
				if(gameMode == GameMode.SURVIVAL && inputDone){
					loseSurvival();
				}
				else{
					restart();
				}
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

	/**
	 * Gets the current game mode of the function.
	 * @return current game mode
	 */
	public GameMode getGameMode(){
		return gameMode;
	}

	/**
	 * Sets the current game mode. Used by GUI.
	 * @param gameMode the GameMode to be displayed/set to.
	 */
	public void setGameMode(GameMode gameMode){
		this.gameMode = gameMode;
	}

	/**
	 * A method to check whether the game is paused.
	 * @return whether the game is paused.
	 */
	public boolean isPaused(){
		return paused;
	}

	/**
	 * Sets the game to play/pause.
	 * @param paused whether the game is playing (true) or paused (false)
	 */
	public void setPaused(boolean paused){
		this.paused = paused;
	}

	/**
	 * A method to restart the game.
	 */
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

	/**
	 * Initializes level 1 of the GameMode passed in.
	 * @param gameMode the GameMode from which the level is created.
	 * @return the new Level created.
	 */
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

	/**
	 * Method to control the audio when the player loses in Survival.
	 */
	public void loseSurvival(){
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

	/**
	 * Checks if the click falls under the GUI menu.
	 * @param input the Input of the game.
	 * @return whether the mouse location is inside the GUI menu.
	 */
	private boolean crossesMenu(Input input){
		return input.getMouseX() > GUIProcessor.GAMEMENU_X && input.getMouseY() < GUIProcessor.GAMEMENU_Y;
	}

	/**
	 * Checks if game is in debug mode.
	 * @return whether game is in debug mode.
	 */
	public boolean isDebug(){
		return debug;
	}

	/**
	 * Sets GameProcessor to debug/not debug.
	 * @param debug boolean to set whether debug is or not
	 */
	public void setDebug(boolean debug){
		this.debug = debug;
	}

	/**
	 * Resets the level to level 1.
	 */
	public void resetLevel(){
		level = newLevelFromGameMode(gameMode);
		ballsExpanded = level.getLevelThreshold();
		score = 0;
		lives = 0;
	}

	/**
	 * Sets level started to true or false.
	 * @param started whether the level has started or not.
	 */
	public void setStarted(boolean started){
		this.started = started;
	}

	/**
	 * Checks if the current level has started or not.
	 * @return whether the level has started or not
	 */
	public boolean isStarted(){
		return started;
	}

	/**
	 * Checks whether the current level needs input.
	 * @return whether the level needs input
	 */
	public boolean needsInput(){
		return needsInput;
	}

	/**
	 * Checks and processes the input when needed.
	 * @param code the integer code of the key
	 * @param c the corresponding character
	 */
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
				cp.getHighScoreTableProcessor().getOriginal().addHighScore(new HighScore(name, score, lives));
				cp.getHighScoreTableProcessor().save();
				cp.getHighScoreTableProcessor().setCurrentHighScoreTable(GameMode.ORIGINAL);
			}
			else if(gameMode == GameMode.SURVIVAL){
				cp.getHighScoreTableProcessor().getSurvival()
						.addHighScore(new HighScore(name, score, level.getLevelNumber()));
				cp.getHighScoreTableProcessor().save();
				cp.getHighScoreTableProcessor().setCurrentHighScoreTable(GameMode.SURVIVAL);
			}
			cp.getHighScoreTableProcessor().setInGame(true);
			cp.setHighScoreTableProcessorState(true);
			inputDone = true;
			name = "";
		}
		else{
			name += c;
		}
	}

}
