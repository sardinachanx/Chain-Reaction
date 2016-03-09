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

public class GUIProcessor implements Processor{

	public static final int SOUND_X = GameEngine.WIDTH - 25;
	public static final int MENU_ICON_OFFSET = 40;
	public static final int QUIT_X = SOUND_X - MENU_ICON_OFFSET;
	public static final int PAUSE_X = QUIT_X - MENU_ICON_OFFSET;
	public static final int MENU_ICON_Y = 25;
	public static final int STARTUP_MENU_X = GameEngine.WIDTH / 2;
	public static final int STARTUP_OFFSET = 100;
	public static final int ORIGINAL_Y = GameEngine.HEIGHT / 2 - 10;
	public static final int INFINITE_Y = ORIGINAL_Y + STARTUP_OFFSET;
	public static final int SURVIVAL_Y = INFINITE_Y + STARTUP_OFFSET;
	public static final int TITLE_Y = GameEngine.HEIGHT / 4 + 10;

	public static final int GAMEMENU_X = GameEngine.WIDTH - 110;
	public static final int GAMEMENU_Y = 45;
	public static final int STARTUP_MENU_WIDTH = 300;
	public static final int STARTUP_MENU_HEIGHT = 80;

	private static final int BACKGROUND_BALLS = 20;

	protected Set<Button> buttons;
	protected Set<Button> menuButtons;
	protected Set<Button> gameButtons;
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
		backgroundBalls = new HashSet<Ball>();
		Button pause = new GraphicButton(PAUSE_X, MENU_ICON_Y,
				new Image("assets" + File.separator + "PauseButton.png")){

			@Override
			public void clicked(GameContainer gc){
				if(!cp.getGp().isPaused()){
					cp.getGp().setPaused(true);
				}
				else{
					cp.getGp().setPaused(false);
				}
				cp.playPauseCurrentAudio();
			}

		};
		buttons.add(pause);
		gameButtons.add(pause);

		Button quit = new GraphicButton(QUIT_X, MENU_ICON_Y, new Image("assets" + File.separator + "QuitButton.png")){

			@Override
			public void clicked(GameContainer gc){
				cp.setGameProcessorState(false);
				cp.getGp().setStarted(false);
				setGameButton(false);
				backgroundOn = true;
				cp.setCurrentAudio(cp.getAudioFiles().get(CoreProcessor.MENU_AUDIO), true);

				setMenuButton(true);
			}
		};
		buttons.add(quit);
		gameButtons.add(quit);

		Button highScore = new GraphicButton(QUIT_X, MENU_ICON_Y,
				new Image("assets" + File.separator + "HighScore.png")){

			@Override
			public void clicked(GameContainer gc){
				cp.setHighScoreTableProcessorState(true);
				setMenuButton(false);
			}

		};

		buttons.add(highScore);
		menuButtons.add(highScore);

		Button sound = new DualGraphicButton(SOUND_X, MENU_ICON_Y, new Image("assets" + File.separator + "SoundOn.png"),
				new Image("assets" + File.separator + "SoundOff.png")){

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
				cp.getGp().setGameMode(GameMode.ORIGINAL);
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
				cp.getGp().setGameMode(GameMode.INFINITE);
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
				cp.getGp().setGameMode(GameMode.SURVIVAL);
				cp.setCurrentAudio(cp.getCurrentAudioList(GameMode.SURVIVAL), true);
				startGame(gc);
			}

		};
		buttons.add(survival);
		menuButtons.add(survival);

		Button title = new GraphicButton(STARTUP_MENU_X, TITLE_Y, new Image("assets" + File.separator + "logo.png")){

			@Override
			public void clicked(GameContainer gc){
				cp.setGUIProcessorState(false);
				cp.setHelpProcessorState(true);
			}

		};
		buttons.add(title);
		menuButtons.add(title);

		for(Button button : getGameButtons()){
			button.setEnabled(false);
		}
		Random random = new Random();
		for(int i = 0; i < BACKGROUND_BALLS; i++){
			GameBall ball = new GameBall(GameProcessor.INITIAL_BALL_RADIUS,
					new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)).darker(), gc);
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

	public Set<Button> getButtons(){
		return buttons;
	}

	public Set<Button> getMenuButtons(){
		return menuButtons;
	}

	public Set<Button> getGameButtons(){
		return gameButtons;
	}

	private boolean clickingButton(Button button, Input input){
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		return mouseX > button.getX() - button.getWidth() / 2 && mouseX < button.getX() + button.getWidth() / 2
				&& mouseY > button.getY() - button.getHeight() / 2 && mouseY < button.getY() + button.getHeight() / 2;
	}

	private void startGame(GameContainer gc){
		backgroundOn = false;
		setMenuButton(false);
		setGameButton(true);
		cp.getGp().resetLevel();
		try{
			cp.getGp().restart(gc);
		}
		catch(SlickException e){
			e.printStackTrace();
		}
		cp.getRunning().add(cp.getGp());
	}

	public void setMenuButton(boolean on){
		for(Button button : menuButtons){
			button.setEnabled(on);
		}
	}

	public void setGameButton(boolean on){
		for(Button button : gameButtons){
			button.setEnabled(on);
		}
	}

}
