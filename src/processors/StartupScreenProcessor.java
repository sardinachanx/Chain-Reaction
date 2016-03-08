package processors;

import java.awt.Font;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import main.GameEngine;

public class StartupScreenProcessor implements Processor{

	CoreProcessor cp;
	boolean startUp;
	boolean initialized;

	public StartupScreenProcessor(CoreProcessor cp){
		this.cp = cp;
		startUp = false;
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		cp.setFont(new TrueTypeFont(new Font("American Typewriter", Font.PLAIN, 18), true));
		initialized = true;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		String loading = "Please wait while the little birds program the game for you...";
		g.drawString(loading, GameEngine.WIDTH / 2 - g.getFont().getWidth(loading) / 2,
				GameEngine.HEIGHT / 2 - g.getFont().getHeight(loading) / 2);
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

	public boolean hasStartUp(){
		return startUp;
	}

	@Override
	public int order(){
		return 0;
	}

}
