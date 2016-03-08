package levels;

public class OriginalLevel extends Level{

	private static final int[] ORIGINAL_THRESHOLD = {1, 2, 4, 6, 10, 15, 18, 22, 30, 39, 48, 55};

	public OriginalLevel(int level){
		super(level);
	}

	@Override
	public int ballNum(int level){
		return ((level - 1) % 12 + 1) * 5;
	}

	@Override
	public int levelThreshold(int level){
		if(level == 0){
			return 0;
		}
		return ORIGINAL_THRESHOLD[(level - 1) % 12];
	}

	@Override
	public Level getNextLevel(){
		return new OriginalLevel(getLevelNumber() % 12 + 1);
	}

}
