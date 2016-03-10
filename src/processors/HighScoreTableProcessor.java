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
import main.GameMode;
import main.GraphicsEditor;

public class HighScoreTableProcessor implements Processor{

	private static final String HIGHSCORE = "high scores";
	private static final String RANKING = "rank";
	private static final String NAME = "name";
	private static final String LIVES = "lives";
	private static final String LEVEL = "level";
	private static final String SCORE = "score";
	private static final String HELP = "press esc to return";

	private static final int HEADER_Y = 60;
	private static final int RANKING_X = 320;
	private static final int NAME_X = 420;
	private static final int LIVES_X = 530;
	private static final int SCORE_X = 620;
	private static final int STARTING_Y = 180;
	private static final int OFFSET = 35;
	private static final int HELP_Y = 600;
	private static final int LINE_OFFSET = 10;

	protected HighScoreTable original;
	protected HighScoreTable survival;
	protected HighScoreTable currentTable;
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
		original = HighScoreTable.read(CoreProcessor.ORIGINAL_HIGH_SCORE_LOCATION);
		survival = HighScoreTable.read(CoreProcessor.SURVIVAL_HIGH_SCORE_LOCATION);
		if(original == null){
			original = new HighScoreTable(false);
		}
		if(survival == null){
			survival = new HighScoreTable(true);
		}
		currentTable = original;
		initialized = true;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.setColor(Color.white);
		g.drawLine(GameEngine.WIDTH / 2, GUIProcessor.SWITCH_Y - LINE_OFFSET, GameEngine.WIDTH / 2,
				GUIProcessor.SWITCH_Y + LINE_OFFSET);
		g.drawString(HIGHSCORE, GraphicsEditor.getCenterX(HIGHSCORE, GameEngine.WIDTH / 2, g),
				GraphicsEditor.getCenterY(HIGHSCORE, HEADER_Y, g));
		g.drawString(RANKING, GraphicsEditor.getCenterX(RANKING, RANKING_X, g),
				GraphicsEditor.getCenterY(RANKING, STARTING_Y, g));
		g.drawString(NAME, GraphicsEditor.getCenterX(NAME, NAME_X, g), GraphicsEditor.getCenterY(NAME, STARTING_Y, g));
		if(currentTable.equals(original)){
			g.drawString(LIVES, GraphicsEditor.getCenterX(LIVES, LIVES_X, g),
					GraphicsEditor.getCenterY(LIVES, STARTING_Y, g));
		}
		else{
			g.drawString(LEVEL, GraphicsEditor.getCenterX(LEVEL, LIVES_X, g),
					GraphicsEditor.getCenterY(LEVEL, STARTING_Y, g));
		}
		g.drawString(SCORE, GraphicsEditor.getCenterX(SCORE, SCORE_X, g),
				GraphicsEditor.getCenterY(SCORE, STARTING_Y, g));
		int tempY = HEADER_Y;
		int index = 1;
		for(HighScore highScore : currentTable.getHighScores()){
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
			cp.getGup().setHSButton(false);
			cp.getGup().setMenuButton(true);
		}
	}

	public HighScoreTable getCurrentHighScoreTable(){
		return currentTable;
	}

	public void setCurrentHighScoreTable(GameMode gameMode){
		switch(gameMode){
			case ORIGINAL:
				currentTable = original;
				break;
			case SURVIVAL:
				currentTable = survival;
				break;
			default:
				return;
		}
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
