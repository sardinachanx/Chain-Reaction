package highscore;

import java.io.Serializable;
import java.util.Comparator;

public class HighScoreComparator implements Comparator<HighScore>, Serializable{

	private static final long serialVersionUID = 854381273166567600L;

	protected boolean survivalMode;

	@SuppressWarnings("unused")
	private HighScoreComparator(){

	}

	public HighScoreComparator(boolean survivalMode){
		this.survivalMode = survivalMode;
	}

	@Override
	public int compare(HighScore o1, HighScore o2){
		if(o1.lives == o2.lives){
			if(o1.score > o2.score){
				return -1;
			}
			else if(o1.score < o2.score){
				return 1;
			}
			else{
				return Long.compare(o1.getCreationTime(), o2.getCreationTime());
			}
		}
		else{
			if(survivalMode){
				return -Integer.compare(o1.lives, o2.lives);
			}
			else{
				return Integer.compare(o1.lives, o2.lives);
			}
		}
	}

}
