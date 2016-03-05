package level;

public class InfiniteLevel extends ProceduralLevel{

	public InfiniteLevel(int level){
		super(level);
	}

	@Override
	public double thresholdPercent(int level){
		return -1 / (Math.pow(level, 1.5) / 8.0 + 1) + 1;
	}

	@Override
	public Level getNextLevel(){
		return new InfiniteLevel(getLevel() + 1);
	}

}
