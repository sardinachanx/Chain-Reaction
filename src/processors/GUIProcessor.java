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
import menu.GraphicButton;
import menu.TextButton;
import objects.Ball;
import objects.GameBall;

public class GUIProcessor implements Processor{

	public static final int PAUSE_X = GameEngine.WIDTH - 65;
	public static final int QUIT_X = GameEngine.WIDTH - 25;
	public static final int QUIT_Y = 25;
	public static final int STARTUP_MENU_X = GameEngine.WIDTH / 2;
	public static final int STARTUP_OFFSET = 100;
	public static final int ORIGINAL_Y = GameEngine.HEIGHT / 2 - 10;
	public static final int INFINITE_Y = ORIGINAL_Y + STARTUP_OFFSET;
	public static final int SURVIVAL_Y = INFINITE_Y + STARTUP_OFFSET;
	public static final int TITLE_Y = GameEngine.HEIGHT / 4 + 10;

	public static final int GAMEMENU_X = GameEngine.WIDTH - 80;
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

	private boolean mouseWasDown;

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
		Button pause = new GraphicButton(PAUSE_X, QUIT_Y, new Image("assets" + File.separator + "PauseButton.png")){

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
		Button quit = new GraphicButton(QUIT_X, QUIT_Y, new Image("assets" + File.separator + "QuitButton.png")){

			@Override
			public void clicked(GameContainer gc){
				cp.setGameProcessorState(false);
				cp.getGp().setStarted(false);
				for(Button button : getMenuButtons()){
					button.setEnabled(true);
				}
				for(Button button : getGameButtons()){
					button.setEnabled(false);
				}
				backgroundOn = true;
				cp.setCurrentAudio(cp.getAudioFiles().get(CoreProcessor.MENU_AUDIO), true);
			}
		};
		buttons.add(quit);
		gameButtons.add(quit);
		Button original = new TextButton(STARTUP_MENU_X, ORIGINAL_Y, STARTUP_MENU_WIDTH, STARTUP_MENU_HEIGHT, true,
				"Original"){

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
				"Infinite"){

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
				"Survival"){

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
					new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)).brighter(), gc);
			backgroundBalls.add(ball);
		}
		backgroundOn = true;
		initialized = true;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		// TODO Auto-generated method stub
		for(Button button : buttons){
			if(button.isEnabled()){
				button.render(g);
			}
		}
		if(!cp.gameProcessorOn()){
			g.setBackground(Color.black);
		}
		if(backgroundOn){
			for(Ball ball : backgroundBalls){
				g.setColor(ball.getColor());
				g.fillOval(ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(), ball.getRadius() * 2,
						ball.getRadius() * 2);
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		boolean mouseClicked = false;
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			if(!mouseWasDown){
				mouseClicked = true;
			}
			mouseWasDown = true;

		}
		else{
			mouseWasDown = false;
		}

		for(Button button : buttons){
			if(mouseClicked && clickingButton(button, input) && button.isEnabled()){
				button.clicked(gc);
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

	private boolean clickingButton(Button button, Input input){
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		return mouseX > button.getX() - button.getWidth() / 2 && mouseX < button.getX() + button.getWidth() / 2
				&& mouseY > button.getY() - button.getHeight() / 2 && mouseY < button.getY() + button.getHeight() / 2;
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

	private void startGame(GameContainer gc){
		backgroundOn = false;
		for(Button button : getMenuButtons()){
			button.setEnabled(false);
		}
		for(Button button : getGameButtons()){
			button.setEnabled(true);
		}
		cp.getGp().resetLevel();
		try{
			cp.getGp().restart(gc);
		}
		catch(SlickException e){
			e.printStackTrace();
		}
		cp.getRunning().add(cp.getGp());
	}

}
