package level;

public abstract class ProceduralLevel extends Level{

	public ProceduralLevel(int level){
		super(level);
	}

	@Override
	public int ballNum(int level){
		return (int) rawBallNum(level);
	}

	private double rawBallNum(int level){
		return 4 * Math.pow(Math.log(level + 1), 2);
	}

	@Override
	public int levelThreshold(int level){
		return (int) (rawBallNum(level) * thresholdPercent(level));
	}

	public abstract double thresholdPercent(int level);

}
