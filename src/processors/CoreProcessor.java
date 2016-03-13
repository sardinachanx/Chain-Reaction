package processors;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import audio.AudioLooper;
import main.GameMode;

/**
 * A class to process other processors. The actual Game Container.
 * @author tchan17
 *
 */
public class CoreProcessor extends BasicGame{

	// The constant for the index of the menu audio in the list of music.
	public static final int MENU_AUDIO = 0;
	// The constant for the index of the original game mode audio in the list of
	// music.
	public static final int ORIGINAL_AUDIO = 1;
	// The constant for the index of the infinite game mode audio in the list of
	// music.
	public static final int INFINITE_AUDIO = 2;
	// The constant for the index of the survival game mode audio in the list of
	// music.
	public static final int SURVIVAL_AUDIO = 3;
	// The folder in which the high scores will be stored
	public static final String HIGH_SCORE_FOLDER = "data";
	// The file in which the original game mode high score will be stored.
	public static final String ORIGINAL_HIGH_SCORE_LOCATION = HIGH_SCORE_FOLDER + File.separator + "original.dat";
	// The file in which the survival game mode high score will be stored.
	public static final String SURVIVAL_HIGH_SCORE_LOCATION = HIGH_SCORE_FOLDER + File.separator + "survival.dat";

	//The GameProcessor (handles the game itself) for this instance of CoreProcessor.
	protected GameProcessor gp;
	//The GUIProcessor (handles the buttons and layout) for this instance of CoreProcessor.
	protected GUIProcessor guip;
	//The StartUpScreenProcessor (handles the startup loading screen) for this instance of CoreProcessor.
	protected StartupScreenProcessor ssp;
	//The HelpProcessor (handles the help screen) for this instance of CoreProcessor.
	protected HelpProcessor hp;
	//The HighScoreTableProcessor (handles the r/w and display of high scores)
	//for this instance of CoreProcessor.
	protected HighScoreTableProcessor hsp;
	//The list of processed audio files that will be used in the game.
	protected List<AudioLooper> audioFiles;
	//The set of processors that will be controlled by this CoreProcessor.
	protected Set<Processor> processors;
	//The set of running processors (aka. their update function will be processed).
	protected Set<Processor> running;

	//Notifies the CoreProcessor if the initialization (font switching etc.) is complete.
	protected boolean startUpComplete;
	//Notifies the GameProcessor whether debug mode/controls are on.
	protected boolean debug;
	//Controls whether audio is on or off (for pause, mute)
	protected boolean audioOn;
	//The font used throughout the game. Initialized during
	protected Font font;
	//The current audio playing, depends on menu and game mode.
	protected AudioLooper currentAudio;

	//Mouse control booleans.
	private boolean mouseWasDown;
	private boolean mouseClicked;

	/**
	 * Creates a new processor.
	 * @param debug whether the game should be running in debug mode
	 */
	public CoreProcessor(boolean debug){
		super("Chain Reaction");
		this.debug = debug;
		audioOn = true;
		audioFiles = new ArrayList<AudioLooper>();
		gp = new GameProcessor(this, debug);
		guip = new GUIProcessor(this);
		ssp = new StartupScreenProcessor(this);
		hp = new HelpProcessor(this);
		hsp = new HighScoreTableProcessor(this);
		AudioLooper original = new AudioLooper("assets" + File.separator + "original.wav");
		AudioLooper infinite = new AudioLooper("assets" + File.separator + "infinite.wav");
		AudioLooper survival = new AudioLooper("assets" + File.separator + "survival.wav");
		AudioLooper starting = new AudioLooper("assets" + File.separator + "starting.wav");
		audioFiles.add(starting);
		audioFiles.add(original);
		audioFiles.add(infinite);
		audioFiles.add(survival);
		processors = new HashSet<Processor>();
		processors.add(gp);
		processors.add(guip);
		processors.add(ssp);
		processors.add(hp);
		processors.add(hsp);
		running = new ConcurrentSkipListSet<Processor>(new Comparator<Processor>(){

			@Override
			public int compare(Processor o1, Processor o2){
				return Integer.compare(o1.order(), o2.order());
			}

		});
		/*
		 * The comparator and order are implemented in order to ensure that processors are rendered
		 * correctly (e.g. the GUI is always on top)
		 */
	}

	/**
	 * A method to get the GameProcessor of this instance of CoreProcessor.
	 * @return GameProcessor of this CoreProcessor
	 */
	public GameProcessor getGameProcessor(){
		return gp;
	}

	/**
	 * A method to get the GUIProcessor of this instance of CoreProcessor.
	 * @return GUIProcessor of this CoreProcessor
	 */
	public GUIProcessor getGUIProcessor(){
		return guip;
	}

	/**
	 * A method to get the StartupScreenProcessor of this instance of CoreProcessor.
	 * @return StartupScreenProcessor of this CoreProcessor
	 */
	public StartupScreenProcessor getStartupScreenProcessor(){
		return ssp;
	}

	/**
	 * A method to get the HelpProcessor of this instance of CoreProcessor.
	 * @return HelpProcessor of this CoreProcessor
	 */
	public HelpProcessor getHelpProcessor(){
		return hp;
	}

	/**
	 * A method to get the HighScoreTableProcessor of this instance of HighScoreTableProcessor.
	 * @return HighScoreTableProcessor of this CoreProcessor
	 */
	public HighScoreTableProcessor getHighScoreTableProcessor(){
		return hsp;
	}

	/**
	 * A method to get the set of processors that are controlled by this instance of CoreProcessor.
	 * @return processors currently controlled by this CoreProcessor
	 */
	public Set<Processor> getProcessors(){
		return processors;
	}

	/**
	 * A method to get the set of currently running processors.
	 * @return currently running processors.
	 */
	public Set<Processor> getRunning(){
		return running;
	}

	/**
	 * A method to get the audio files being controlled by this CoreProcessor.
	 * @return the list of audio files under this CoreProcessor
	 */
	public List<AudioLooper> getAudioFiles(){
		return audioFiles;
	}

	/**
	 * A method to get the current audio playing.
	 * @return the current audio playing
	 */
	public AudioLooper getCurrentAudio(){
		return currentAudio;
	}

	/**
	 * A method to change the currently playing audio. Accepts a processed audio file and a boolean
	 * to decide if the current audio playing (the one being switched out) will restart (aka. play
	 * from the beginning next time it plays).
	 * @param currentAudio the new current audio file to be played
	 * @param restart whether the old audio file should be restarted
	 */
	public void setCurrentAudio(AudioLooper currentAudio, boolean restart){
		this.currentAudio.setPaused(true);
		if(restart){
			this.currentAudio.setRestart(true);
		}
		this.currentAudio = currentAudio;
		if(audioOn){
			this.currentAudio.setPaused(false);
		}
	}

	/**
	 * Calls the render() function of all currently running processors. Sets the font to the
	 * initial font loaded.
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		if(font != null){
			g.setFont(font);
		}
		for(Processor processor : running){
			processor.render(gc, g);
		}
	}

	/**
	 * Runs the StartupProcessor at the initialization of the CoreProcessor in GameEngine.
	 */
	@Override
	public void init(GameContainer gc) throws SlickException{
		running.add(ssp);
	}

	/**
	 * The update of CoreProcessor.
	 * Checks if the mouse was clicked and sets the boolean accordingly, then checks for startup
	 * initialization (for the beginning of the game). Calls the update methods of running processors
	 * as well as debug mode entry.
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		mouseClicked = false;
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			if(!mouseWasDown){
				mouseClicked = true;
			}
			mouseWasDown = true;

		}
		else{
			mouseWasDown = false;
		}
		if(!startUpComplete && ssp.hasStartUp()){
			running.remove(ssp);
			running.add(hp);
			audioFiles.get(MENU_AUDIO).setPaused(false);
			currentAudio = audioFiles.get(MENU_AUDIO);
			startUpComplete = true;
		}
		for(Processor processor : running){
			processor.update(gc, delta);
		}
		if(input.isKeyPressed(Input.KEY_GRAVE)){
			if(gp.isDebug()){
				gp.setDebug(false);
				debug = false;
			}
			else{
				gp.setDebug(true);
				debug = true;
			}
		}
	}

	/**
	 * Sets the GameProcessor's state to either running (true) or not running (false).
	 * @param on whether the game processor is running or not
	 */
	public void setGameProcessorState(boolean on){
		if(on){
			running.add(gp);
		}
		else{
			running.remove(gp);
		}
	}

	/**
	 * Sets the GUIProcessor's state to either running (true) or not running (false).
	 * @param on whether the GUI processor is running or not
	 */
	public void setGUIProcessorState(boolean on){
		if(on){
			running.add(guip);
		}
		else{
			running.remove(guip);
		}
	}

	/**
	 * Sets the HelpProcessor's state to either running (true) or not running (false).
	 * @param on whether the help processor is running or not
	 */
	public void setHelpProcessorState(boolean on){
		if(on){
			running.add(hp);
		}
		else{
			running.remove(hp);
		}
	}

	/**
	 * Sets the HighScoreTableProcessor's state to either running (true) or not running (false).
	 * @param on whether the high score processor is running or not
	 */
	public void setHighScoreTableProcessorState(boolean on){
		if(on){
			running.add(hsp);
		}
		else{
			running.remove(hsp);
		}
	}

	/**
	 * Checks whether game processor is running (in the set of running processors).
	 * @return whether game processor is running
	 */
	public boolean gameProcessorOn(){
		return running.contains(gp);
	}

	/**
	 * Checks whether GUI processor is running (in the set of running processors).
	 * @return whether GUI processor is running
	 */
	public boolean guiProcessorOn(){
		return running.contains(guip);
	}

	/**
	 * Checks whether help processor is running (in the set of running processors).
	 * @return whether help processor is running
	 */
	public boolean helpProcessorOn(){
		return running.contains(hp);
	}

	/**
	 * Checks whether high score processor is running (in the set of running processors).
	 * @return whether high score processor is running
	 */
	public boolean highScoreTableProcessorOn(){
		return running.contains(hsp);
	}

	/**
	 * Sets debug to true or false.
	 * @param debug whether debug mode is on or off
	 */
	public void setDebug(boolean debug){
		this.debug = debug;
	}

	/**
	 * Checks whether CoreProcessor is in debug mode.
	 * @return whether CoreProcessor is in debug mode.
	 */
	public boolean isDebug(){
		return debug;
	}

	/**
	 * Changes the font of the game to a particular font file.
	 * @param font the font that is to be used in the game
	 */
	public void setFont(Font font){
		this.font = font;
	}

	/**
	 * Gets the font file being used in the game
	 * @return the font being used in the game
	 */
	public Font getFont(){
		return font;
	}

	/**
	 * Switches audio according to the GameMode.
	 * @param gameMode the game mode music to be switched to
	 * @return the audio file corresponding to the game mode/menu mode
	 */
	public AudioLooper getCurrentAudioList(GameMode gameMode){
		switch(gameMode){
			case ORIGINAL:
				return audioFiles.get(ORIGINAL_AUDIO);
			case INFINITE:
				return audioFiles.get(INFINITE_AUDIO);
			case SURVIVAL:
				return audioFiles.get(SURVIVAL_AUDIO);
			default:
				return audioFiles.get(MENU_AUDIO);
		}
	}

	/**
	 * Plays/pauses the current music. Pause if it is currently playing and play
	 * if it is currently paused.
	 */
	public void playPauseCurrentAudio(){
		if(audioOn){
			if(currentAudio.isPaused()){
				currentAudio.setPaused(false);
			}
			else{
				currentAudio.setPaused(true);
			}
		}
	}

	/**
	 * Pauses any music for mute mode such that all music won't be playing even during mode switching.
	 */
	public void pauseAll(){
		for(AudioLooper al : audioFiles){
			al.setPaused(true);
		}
		audioOn = false;
	}

	/**
	 * Returns whether audio is playing.
	 * @return whether AudioOn is true
	 */
	public boolean isAudioOn(){
		return audioOn;
	}

	/**
	 * A method to set the current audio to on or off.
	 * @param audioOn if audioOn is on or off
	 */
	public void setAudioOn(boolean audioOn){
		this.audioOn = audioOn;
	}

	/**
	 * A method to detect if mouse is down.
	 * @return whether mouse is being clicked at this frame.
	 */
	public boolean clicked(){
		return mouseClicked;
	}

	/**
	 * Overrides the BasicGame method to enable name-typing.
	 */
	@Override
	public void keyReleased(int code, char c){
		gp.receiveInput(code, c);
	}

}
