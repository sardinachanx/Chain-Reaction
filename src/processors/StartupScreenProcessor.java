package processors;

import java.awt.Font;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import main.GameEngine;
import main.GraphicsEditor;

/**
 * The StartupScreenProcessor runs the initialization method of all processors controlled by the associated
 * instance of CoreProcessor upon startup. It displays the loading screen and handles font loading when the
 * game is launched.
 * @author tchan17
 *
 */
public class StartupScreenProcessor implements Processor{

	//The CoreProcessor associated with this instance of the StartupScreenProcessor.
	CoreProcessor cp;
	//Whether the game has finished its starting up process.
	boolean startUp;
	//Whether the initialization method of this processor (font changing) is finished.
	boolean initialized;

	/**
	 * Constructs a new StartupScreenProcessor with an associated CoreProcessor.
	 * @param cp the CoreProcessor associated with this instance of StartupScreenProcessor
	 */
	public StartupScreenProcessor(CoreProcessor cp){
		this.cp = cp;
		startUp = false;
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		cp.setFont(new TrueTypeFont(new Font("American Typewriter", Font.PLAIN, 18), true));
		for(int i = 0; i < cp.getAudioFiles().size(); i++){
			Thread thread = new Thread(cp.getAudioFiles().get(i));
			thread.start();
		}
		initialized = true;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		String loading = "Please wait while the little birds program the game for you...";
		g.drawString(loading, GraphicsEditor.getCenterX(loading, GameEngine.WIDTH / 2, g),
				GraphicsEditor.getCenterY(loading, GameEngine.HEIGHT / 2, g));
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		if(startUp){
			return;
		}
		boolean allDone = true;
		Set<Processor> processors = cp.getProcessors();
		for(Processor processor : processors){
			if(!processor.initialized()){
				allDone = false;
				processor.init(gc);
			}
		}
		if(allDone){
			startUp = true;
		}
	}

	@Override
	public boolean initialized(){
		return initialized;
	}

	/**
	 * Returns whether the whole game has finished initializing.
	 * @return whether the game is fully initialized
	 */
	public boolean hasStartUp(){
		return startUp;
	}

	@Override
	public int order(){
		return 0;
	}

}
