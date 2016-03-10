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

public class CoreProcessor extends BasicGame{

	public static final int MENU_AUDIO = 0;
	public static final int ORIGINAL_AUDIO = 1;
	public static final int INFINITE_AUDIO = 2;
	public static final int SURVIVAL_AUDIO = 3;
	public static final String HIGH_SCORE_FOLDER = "data";
	public static final String ORIGINAL_HIGH_SCORE_LOCATION = HIGH_SCORE_FOLDER + File.separator + "original.dat";
	public static final String SURVIVAL_HIGH_SCORE_LOCATION = HIGH_SCORE_FOLDER + File.separator + "survival.dat";

	protected GameProcessor gp;
	protected GUIProcessor guip;
	protected StartupScreenProcessor ssp;
	protected HelpProcessor hp;
	protected HighScoreTableProcessor hsp;
	protected List<AudioLooper> audioFiles;
	protected Set<Processor> processors;
	protected Set<Processor> running;

	protected boolean startUpComplete;
	protected boolean debug;
	protected boolean audioOn;
	protected Font font;
	protected AudioLooper currentAudio;

	private boolean mouseWasDown;
	private boolean mouseClicked;

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
				if(o1.order() > o2.order()){
					return 1;
				}
				else if(o1.order() < o2.order()){
					return -1;
				}
				return 0;
			}

		});
	}

	public GameProcessor getGp(){
		return gp;
	}

	public GUIProcessor getGup(){
		return guip;
	}

	public StartupScreenProcessor getSsp(){
		return ssp;
	}

	public HelpProcessor getHp(){
		return hp;
	}

	public HighScoreTableProcessor getHsp(){
		return hsp;
	}

	public Set<Processor> getProcessors(){
		return processors;
	}

	public Set<Processor> getRunning(){
		return running;
	}

	public List<AudioLooper> getAudioFiles(){
		return audioFiles;
	}

	public AudioLooper getCurrentAudio(){
		return currentAudio;
	}

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

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		//TODO
		if(font != null){
			g.setFont(font);
		}
		for(Processor processor : running){
			processor.render(gc, g);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		// TODO Auto-generated method stub
		running.add(ssp);
	}

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

	public void setGameProcessorState(boolean on){
		if(on){
			running.add(gp);
		}
		else{
			running.remove(gp);
		}
	}

	public void setGUIProcessorState(boolean on){
		if(on){
			running.add(guip);
		}
		else{
			running.remove(guip);
		}
	}

	public void setHelpProcessorState(boolean on){
		if(on){
			running.add(hp);
		}
		else{
			running.remove(hp);
		}
	}

	public void setHighScoreTableProcessorState(boolean on){
		if(on){
			running.add(hsp);
		}
		else{
			running.remove(hsp);
		}
	}

	public boolean gameProcessorOn(){
		return running.contains(gp);
	}

	public boolean guiProcessorOn(){
		return running.contains(guip);
	}

	public boolean helpProcessorOn(){
		return running.contains(hp);
	}

	public boolean highScoreTableProcessorOn(){
		return running.contains(hsp);
	}

	public void setDebug(boolean debug){
		this.debug = debug;
	}

	public boolean isDebug(){
		return debug;
	}

	public void setFont(Font font){
		this.font = font;
	}

	public Font getFont(){
		return font;
	}

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

	public void pauseAll(){
		for(AudioLooper al : audioFiles){
			al.setPaused(true);
		}
		audioOn = false;
	}

	public boolean isAudioOn(){
		return audioOn;
	}

	public void setAudioOn(boolean audioOn){
		this.audioOn = audioOn;
	}

	public boolean clicked(){
		return mouseClicked;
	}

}
