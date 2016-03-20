package processors;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import main.GameEngine;
import main.GameMode;
import menu.Button;
import menu.DualGraphicButton;
import menu.GraphicButton;
import menu.TextButton;
import objects.Ball;
import objects.GameBall;

/**
 * The GUIProcessor class controls the buttons and menus of the game, including the startup screen,
 * pause/quit/sound buttons, high score button, and the logo/helper button.
 * @author tchan17
 *
 */
public class GUIProcessor implements Processor{

	//The constants for the locations of buttons.
	public static final int GAMEMENU_X = GameEngine.WIDTH - 110;
	public static final int GAMEMENU_Y = 45;
	public static final int SWITCH_Y = 130;

	private static final int SOUND_X = GameEngine.WIDTH - 25;
	private static final int MENU_ICON_OFFSET = 40;
	private static final int QUIT_X = SOUND_X - MENU_ICON_OFFSET;
	private static final int PAUSE_X = QUIT_X - MENU_ICON_OFFSET;
	private static final int MENU_ICON_Y = 25;
	private static final int STARTUP_MENU_X = GameEngine.WIDTH / 2;
	private static final int STARTUP_OFFSET = 100;
	private static final int ORIGINAL_Y = GameEngine.HEIGHT / 2 - 10;
	private static final int INFINITE_Y = ORIGINAL_Y + STARTUP_OFFSET;
	private static final int SURVIVAL_Y = INFINITE_Y + STARTUP_OFFSET;
	private static final int TITLE_Y = GameEngine.HEIGHT / 4 + 10;
	private static final int ORIGINAL_HS_X = GameEngine.WIDTH / 2 - 62;
	private static final int SURVIVAL_HS_X = GameEngine.WIDTH / 2 + 62;
	private static final String ICON_LOCATION = "assets";

	private static final int STARTUP_MENU_WIDTH = 300;
	private static final int STARTUP_MENU_HEIGHT = 80;

	private static final int BACKGROUND_BALLS = 20;

	//All the buttons.
	protected Set<Button> buttons;
	protected Set<Button> menuButtons;
	protected Set<Button> gameButtons;
	protected Set<Button> highScoreButtons;
	protected CoreProcessor cp;
	protected boolean initialized;

	protected Set<Ball> backgroundBalls;
	protected boolean backgroundOn;

	public GUIProcessor(CoreProcessor cp){
		this.cp = cp;
		initialized = false;
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		// TODO Auto-generated method stub
		buttons = new HashSet<Button>();
		menuButtons = new HashSet<Button>();
		gameButtons = new HashSet<Button>();
		highScoreButtons = new HashSet<Button>();
		backgroundBalls = new HashSet<Ball>();

		Button pause = new GraphicButton(PAUSE_X, MENU_ICON_Y,
				new Image(ICON_LOCATION + File.separator + "PauseButton.png")){

			@Override
			public void clicked(GameContainer gc){
				if(!cp.getGameProcessor().isPaused()){
					cp.getGameProcessor().setPaused(true);
				}
				else{
					cp.getGameProcessor().setPaused(false);
				}
				cp.playPauseCurrentAudio();
			}

		};
		buttons.add(pause);
		gameButtons.add(pause);

		Button quit = new GraphicButton(QUIT_X, MENU_ICON_Y,
				new Image(ICON_LOCATION + File.separator + "QuitButton.png")){

			@Override
			public void clicked(GameContainer gc){
				cp.setHighScoreTableProcessorState(false);
				cp.setGameProcessorState(false);
				cp.getGameProcessor().setStarted(false);
				setGameButton(false);
				backgroundOn = true;
				cp.setCurrentAudio(cp.getAudioFiles().get(CoreProcessor.MENU_AUDIO), true);

				setMenuButton(true);
			}
		};
		buttons.add(quit);
		gameButtons.add(quit);

		Button highScore = new GraphicButton(QUIT_X, MENU_ICON_Y,
				new Image(ICON_LOCATION + File.separator + "HighScore.png")){

			@Override
			public void clicked(GameContainer gc){
				cp.getHighScoreTableProcessor().setInGame(false);
				cp.setHighScoreTableProcessorState(true);
				setHSButton(true);
				setMenuButton(false);
				setHSButtonState(0);
				cp.getHighScoreTableProcessor().setCurrentHighScoreTable(GameMode.ORIGINAL);
			}

		};

		buttons.add(highScore);
		menuButtons.add(highScore);

		Button sound = new DualGraphicButton(SOUND_X, MENU_ICON_Y,
				new Image(ICON_LOCATION + File.separator + "SoundOn.png"),
				new Image(ICON_LOCATION + File.separator + "SoundOff.png")){

			@Override
			public void clicked(GameContainer gc){
				setCurrentImage((getCurrentImage() + 1) % 2);
				if(getCurrentImage() == 0){
					cp.setAudioOn(true);
					cp.playPauseCurrentAudio();
				}
				else{
					cp.setAudioOn(false);
					cp.pauseAll();
				}
			}

		};

		buttons.add(sound);

		Button original = new TextButton(STARTUP_MENU_X, ORIGINAL_Y, STARTUP_MENU_WIDTH, STARTUP_MENU_HEIGHT, true,
				"original"){

			@Override
			public void clicked(GameContainer gc){
				cp.getGameProcessor().setGameMode(GameMode.ORIGINAL);
				cp.setCurrentAudio(cp.getCurrentAudioList(GameMode.ORIGINAL), true);
				startGame(gc);
			}

		};
		buttons.add(original);
		menuButtons.add(original);

		Button infinite = new TextButton(STARTUP_MENU_X, INFINITE_Y, STARTUP_MENU_WIDTH, STARTUP_MENU_HEIGHT, true,
				"infinite"){

			@Override
			public void clicked(GameContainer gc){
				cp.getGameProcessor().setGameMode(GameMode.INFINITE);
				cp.setCurrentAudio(cp.getCurrentAudioList(GameMode.INFINITE), true);
				startGame(gc);
			}

		};
		buttons.add(infinite);
		menuButtons.add(infinite);

		Button survival = new TextButton(STARTUP_MENU_X, SURVIVAL_Y, STARTUP_MENU_WIDTH, STARTUP_MENU_HEIGHT, true,
				"survival"){

			@Override
			public void clicked(GameContainer gc){
				cp.getGameProcessor().setGameMode(GameMode.SURVIVAL);
				cp.setCurrentAudio(cp.getCurrentAudioList(GameMode.SURVIVAL), true);
				startGame(gc);
			}

		};
		buttons.add(survival);
		menuButtons.add(survival);

		Button title = new GraphicButton(STARTUP_MENU_X, TITLE_Y,
				new Image(ICON_LOCATION + File.separator + "logo.png")){

			@Override
			public void clicked(GameContainer gc){
				cp.setGUIProcessorState(false);
				cp.setHelpProcessorState(true);
			}

		};
		buttons.add(title);
		menuButtons.add(title);

		Button originalHS = new DualGraphicButton(ORIGINAL_HS_X, SWITCH_Y,
				new Image(ICON_LOCATION + File.separator + "original1.png"),
				new Image(ICON_LOCATION + File.separator + "original2.png")){

			@Override
			public void clicked(GameContainer gc){
				setHSButtonState(0);
				cp.getHighScoreTableProcessor().setCurrentHighScoreTable(GameMode.ORIGINAL);
			}

		};

		buttons.add(originalHS);
		highScoreButtons.add(originalHS);

		Button survivalHS = new DualGraphicButton(SURVIVAL_HS_X, SWITCH_Y,
				new Image(ICON_LOCATION + File.separator + "survival1.png"),
				new Image(ICON_LOCATION + File.separator + "survival2.png")){

			@Override
			public void clicked(GameContainer gc){
				setHSButtonState(1);
				cp.getHighScoreTableProcessor().setCurrentHighScoreTable(GameMode.SURVIVAL);
			}

		};

		buttons.add(survivalHS);
		highScoreButtons.add(survivalHS);

		setGameButton(false);
		setHSButton(false);

		Random random = new Random();
		for(int i = 0; i < BACKGROUND_BALLS; i++){
			GameBall ball = new GameBall(GameProcessor.INITIAL_BALL_RADIUS,
					new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)).darker());
			backgroundBalls.add(ball);
		}
		backgroundOn = true;
		initialized = true;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		// TODO Auto-generated method stub
		if(backgroundOn){
			for(Ball ball : backgroundBalls){
				g.setColor(ball.getColor());
				g.fillOval(ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(), ball.getRadius() * 2,
						ball.getRadius() * 2);
			}
		}
		for(Button button : buttons){
			if(button.isEnabled()){
				button.render(g);
			}
		}
		if(!cp.gameProcessorOn()){
			g.setBackground(Color.black);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		for(Button button : buttons){
			if(cp.clicked() && clickingButton(button, input) && button.isEnabled()){
				button.clicked(gc);
				break;
			}
		}
		if(backgroundOn){
			for(Ball ball : backgroundBalls){
				ball.move(gc);
			}
		}
	}

	/**
	 * Adds a button to the list.
	 * @param button the button to be added.
	 */
	public void addButton(TextButton button){
		buttons.add(button);
	}

	@Override
	public boolean initialized(){
		return initialized;
	}

	@Override
	public int order(){
		return 2;
	}

	/**
	 * Gets the buttons processed by this processor.
	 * @return the set of buttons
	 */
	public Set<Button> getButtons(){
		return buttons;
	}

	/**
	 * Gets the menu buttons processed by this processor.
	 * @return the set of menu buttons
	 */
	public Set<Button> getMenuButtons(){
		return menuButtons;
	}

	/**
	 * Gets the game buttons processed by this processor.
	 * @return the set of game buttons
	 */
	public Set<Button> getGameButtons(){
		return gameButtons;
	}

	/**
	 * Checks whether a button is being clicked.
	 * @param button the button being checked
	 * @param input the Input of the game
	 * @return whether the mouse is on the button
	 */
	private boolean clickingButton(Button button, Input input){
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		return mouseX > button.getX() - button.getWidth() / 2 && mouseX < button.getX() + button.getWidth() / 2
				&& mouseY > button.getY() - button.getHeight() / 2 && mouseY < button.getY() + button.getHeight() / 2;
	}

	/**
	 * Starts the game.
	 * @param gc the GameContainer of the game.
	 */
	private void startGame(GameContainer gc){
		backgroundOn = false;
		setMenuButton(false);
		setGameButton(true);
		cp.getGameProcessor().resetLevel();
		cp.getGameProcessor().restart();
		cp.getRunning().add(cp.getGameProcessor());
	}

	/**
	 * Turns on/off menu buttons
	 * @param on whether the buttons are on or off.
	 */
	public void setMenuButton(boolean on){
		for(Button button : menuButtons){
			button.setEnabled(on);
		}
	}

	/**
	 * Turns on/off game buttons
	 * @param on whether the buttons are on or off.
	 */
	public void setGameButton(boolean on){
		for(Button button : gameButtons){
			button.setEnabled(on);
		}
	}

	/**
	 * Turns on/off high score buttons
	 * @param on whether the buttons are on or off.
	 */
	public void setHSButton(boolean on){
		for(Button button : highScoreButtons){
			button.setEnabled(on);
		}
	}

	/**
	 * Sets the state of high score buttons
	 * @param index 0 or 1
	 */
	public void setHSButtonState(int index){
		for(Button button : highScoreButtons){
			if(button instanceof DualGraphicButton){
				((DualGraphicButton) button).setCurrentImage(index % 2);
			}
		}
	}

}
