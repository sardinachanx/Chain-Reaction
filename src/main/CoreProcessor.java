package main;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class CoreProcessor extends BasicGame{

	GameProcessor gp;
	GUIProcessor gup;
	StartupScreenProcessor ssp;
	Set<Processor> processors;
	Set<Processor> running;

	public CoreProcessor(){
		super("Chain Reaction");
		gp = new GameProcessor();
		gup = new GUIProcessor(this);
		ssp = new StartupScreenProcessor(this);
		processors = new HashSet<Processor>();
		processors.add(gp);
		processors.add(gup);
		processors.add(ssp);
		running = new TreeSet<Processor>(new Comparator<Processor>(){

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

	public void setGp(GameProcessor gp){
		this.gp = gp;
	}

	public GUIProcessor getGup(){
		return gup;
	}

	public void setGup(GUIProcessor gup){
		this.gup = gup;
	}

	public Set<Processor> getProcessors(){
		return processors;
	}

	public Set<Processor> getRunning(){
		return running;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		//TODO
		//gp.render(gc, g);
		for(Processor processor : running){
			processor.render(gc, g);
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		// TODO Auto-generated method stub
		//gp.init(gc);
		running.add(ssp);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		// TODO Auto-generated method stub
		//gp.update(gc, delta);
		if(ssp.hasStartUp()){
			running.remove(ssp);
			running.add(gp);
			running.add(gup);
		}
		for(Processor processor : running){
			processor.update(gc, delta);
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
			running.add(gp);
		}
		else{
			running.remove(gp);
		}
	}

}
