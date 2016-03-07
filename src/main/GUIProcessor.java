package main;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import menu.Button;
import menu.GraphicButton;
import menu.TextButton;

public class GUIProcessor implements Processor{

	public static final int PAUSE_X = GameEngine.WIDTH - 60;
	public static final int QUIT_X = GameEngine.WIDTH - 20;
	public static final int QUIT_Y = 25;

	public static final int GAMEMENU_X = GameEngine.WIDTH - 80;
	public static final int GAMEMENU_Y = 45;

	Set<Button> buttons;

	CoreProcessor cp;

	boolean initialized;

	public GUIProcessor(CoreProcessor cp){
		this.cp = cp;
		initialized = false;
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		// TODO Auto-generated method stub
		buttons = new HashSet<Button>();
		Button pause = new GraphicButton(PAUSE_X, QUIT_Y, new Image("assets" + File.separator + "PauseButton.png")){

			@Override
			public void clicked(){
				cp.getGp().setPaused(true);
				System.out.println("clicked");
			}

		};
		buttons.add(pause);
		Button quit = new GraphicButton(QUIT_X, QUIT_Y, new Image("assets" + File.separator + "QuitButton.png")){

			@Override
			public void clicked(){
				cp.setGameProcessorState(false);
			}
		};
		buttons.add(quit);
		initialized = true;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		// TODO Auto-generated method stub
		for(Button button : buttons){
			if(button.isVisible()){
				button.render(g);
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		for(Button button : buttons){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && clickingButton(button, input)){
				button.clicked();
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

}
