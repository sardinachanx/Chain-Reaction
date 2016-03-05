package level;

public class SurvivalLevel extends ProceduralLevel{

	public SurvivalLevel(int level){
		super(level);
	}

	@Override
	public double thresholdPercent(int level){
		return -1 / (Math.pow(level, 1.5) / 16.0 + 1) + 1;
	}

	@Override
	public Level getNextLevel(){
		return new SurvivalLevel(getLevel() + 1);
	}

}
