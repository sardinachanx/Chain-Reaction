package processors;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * An interface for Processors. Provides the necessary functions of a processor such that all processors
 * have the necessary methods and can be called and manipulated by the CoreProcessor.
 * @author tchan17
 *
 */
public interface Processor{

	/**
	 * Checks if the processor is initialized.
	 * @return whether the processor is initialized.
	 */
	public boolean initialized();

	/**
	 * Returns the order of the processor i.e. the order it is rendered. A higher number means it
	 * will be rendered on top.
	 * @return
	 */
	public int order();

	/**
	 * Initializes the processor.
	 * @param gc the GameContainer of the processor
	 * @throws SlickException
	 */
	public void init(GameContainer gc) throws SlickException;

	/**
	 * Renders the items controlled by the processor.
	 * @param gc the GameContainer of the processor
	 * @param g the Graphics of the processor
	 * @throws SlickException
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException;

	/**
	 * The Update function of the processor i.e. what it carries out every frame.
	 * @param gc the GameContainer of the processor
	 * @param delta the time between two frames
	 * @throws SlickException
	 */
	public void update(GameContainer gc, int delta) throws SlickException;

}
