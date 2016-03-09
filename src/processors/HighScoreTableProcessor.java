package processors;

import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import highscore.HighScore;
import highscore.HighScoreTable;
import main.GameEngine;
import main.GraphicsEditor;

public class HighScoreTableProcessor implements Processor{

	protected static final String HIGHSCORE = "high scores";
	protected static final String RANKING = "rank";
	protected static final String NAME = "name";
	protected static final String LIVES = "lives";
	protected static final String SCORE = "score";
	protected static final String HELP = "press esc to return";

	protected static final int HEADER_Y = 100;
	protected static final int RANKING_X = 340;
	protected static final int NAME_X = 440;
	protected static final int LIVES_X = 540;
	protected static final int SCORE_X = 600;
	protected static final int STARTING_Y = 160;
	protected static final int OFFSET = 35;
	protected static final int HELP_Y = 600;

	protected HighScoreTable highScoreTable;
	protected boolean initialized;
	protected CoreProcessor cp;

	public HighScoreTableProcessor(CoreProcessor cp){
		this.cp = cp;
		initialized = false;
	}

	@Override
	public boolean initialized(){
		return initialized;
	}

	@Override
	public int order(){
		return 4;
	}

	@Override
	public void init(GameContainer gc) throws SlickException{
		File folder = new File(CoreProcessor.HIGH_SCORE_FOLDER);
		folder.mkdirs();
		highScoreTable = HighScoreTable.read(CoreProcessor.HIGH_SCORE_LOCATION);
		if(highScoreTable == null){
			highScoreTable = new HighScoreTable();
		}
		initialized = true;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.setColor(Color.white);
		g.drawString(HIGHSCORE, GraphicsEditor.getCenterX(HIGHSCORE, GameEngine.WIDTH / 2, g),
				GraphicsEditor.getCenterY(HIGHSCORE, HEADER_Y, g));
		g.drawString(RANKING, GraphicsEditor.getCenterX(RANKING, RANKING_X, g),
				GraphicsEditor.getCenterY(RANKING, STARTING_Y, g));
		g.drawString(NAME, GraphicsEditor.getCenterX(NAME, NAME_X, g), GraphicsEditor.getCenterY(NAME, STARTING_Y, g));
		g.drawString(LIVES, GraphicsEditor.getCenterX(LIVES, LIVES_X, g),
				GraphicsEditor.getCenterY(LIVES, STARTING_Y, g));
		g.drawString(SCORE, GraphicsEditor.getCenterX(SCORE, SCORE_X, g),
				GraphicsEditor.getCenterY(SCORE, STARTING_Y, g));
		int tempY = HEADER_Y;
		int index = 1;
		for(HighScore highScore : highScoreTable.getHighScores()){
			tempY = HEADER_Y + OFFSET;
			drawRow(highScore, g, index, tempY);
		}
		g.drawString(HELP, GraphicsEditor.getCenterX(HELP, GameEngine.WIDTH / 2, g),
				GraphicsEditor.getCenterY(HELP, HELP_Y, g));
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException{
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			cp.setHighScoreTableProcessorState(false);
			cp.getGup().setMenuButton(true);
		}
	}

	public HighScoreTable getHighScoreTable(){
		return highScoreTable;
	}

	private void drawRow(HighScore highScore, Graphics g, int index, int y){
		String rank = index + "";
		String name = highScore.getName();
		String lives = highScore.getLives() + "";
		String score = highScore.getScore() + "";
		g.drawString(rank, GraphicsEditor.getCenterX(rank, RANKING_X, g), GraphicsEditor.getCenterY(rank, y, g));
		g.drawString(name, GraphicsEditor.getCenterX(name, NAME_X, g), GraphicsEditor.getCenterY(name, y, g));
		g.drawString(lives, GraphicsEditor.getCenterX(lives, LIVES_X, g), GraphicsEditor.getCenterY(lives, y, g));
		g.drawString(score, GraphicsEditor.getCenterX(score, SCORE_X, g), GraphicsEditor.getCenterY(score, y, g));
	}

}
