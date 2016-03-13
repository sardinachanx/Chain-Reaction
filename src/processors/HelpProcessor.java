package processors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import main.GameEngine;

/**
 * The help screen processor. Handles the help screen.
 * @author tchan17
 *
 */
public class HelpProcessor implements Processor{

	//The images used for the help screen.
	protected List<Image> instructions;
	protected Image currentImage;
	protected int currentImageIndex;

	protected boolean initialized;
	protected CoreProcessor cp;

	/**
	 * Creates a new HelpProcessor.
	 * @param cp the CoreProcessor associated with this HelpProcessor
	 */
	public HelpProcessor(CoreProcessor cp){
		this.cp = cp;
		initialized = false;
	}

	@Override
	public boolean initialized(){
		return initialized;
	}

	@Override
	public int order(){
		return 3;
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		instructions = new ArrayList<Image>();
		instructions.add(new Image("assets" + File.separator + "instructions1.png"));
		instructions.add(new Image("assets" + File.separator + "instructions2.png"));
		currentImageIndex = 0;
		currentImage = instructions.get(currentImageIndex);
		initialized = true;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.drawImage(currentImage, GameEngine.WIDTH / 2 - currentImage.getWidth() / 2,
				GameEngine.HEIGHT / 2 - currentImage.getHeight() / 2);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_LEFT)){
			currentImageIndex = (currentImageIndex - 1 + instructions.size()) % instructions.size();
			currentImage = instructions.get(currentImageIndex);
		}
		if(input.isKeyPressed(Input.KEY_RIGHT)){
			currentImageIndex = (currentImageIndex + 1) % instructions.size();
			currentImage = instructions.get(currentImageIndex);
		}
		if(input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_ESCAPE)){
			cp.setHelpProcessorState(false);
			cp.setGUIProcessorState(true);
		}
	}

}
