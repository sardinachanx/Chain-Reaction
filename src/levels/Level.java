package levels;

public abstract class Level{

	protected int ballNum;
	protected int levelThreshold;
	protected int level;

	public Level(int level){
		this.level = level;
		ballNum = ballNum(level);
		levelThreshold = levelThreshold(level);
	}

	public int getBallNum(){
		return ballNum;
	}

	public int getLevelThreshold(){
		return levelThreshold;
	}

	public int getLevel(){
		return level;
	}

	public abstract Level getNextLevel();

	public abstract int ballNum(int level);

	public abstract int levelThreshold(int level);
}
