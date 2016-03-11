package highscore;

import java.io.Serializable;

public class HighScore implements Serializable{

	private static final long serialVersionUID = 200042479976968312L;

	private long creationTime;
	protected String name;
	protected long score;
	protected int lives;

	public HighScore(String name, long score, int lives){
		this.name = name;
		this.score = score;
		this.lives = lives;
		creationTime = System.currentTimeMillis();
	}

	protected HighScore(){

	}

	public String getName(){
		return name;
	}

	public long getScore(){
		return score;
	}

	public int getLives(){
		return lives;
	}

	public long getCreationTime(){
		return creationTime;
	}

	@Override
	public String toString(){
		return "{" + name + ", " + score + ", " + lives + "}";
	}

}
