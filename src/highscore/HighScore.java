package highscore;

import java.io.Serializable;

public class HighScore implements Serializable{

	private static final long serialVersionUID = 200042479976968312L;

	protected String name;
	protected int score;
	protected int lives;

	public HighScore(String name, int score, int lives){
		this.name = name;
		this.score = score;
		this.lives = lives;
	}

	protected HighScore(){

	}

	public String getName(){
		return name;
	}

	public int getScore(){
		return score;
	}

	public int getLives(){
		return lives;
	}

	@Override
	public String toString(){
		return "{" + name + ", " + score + ", " + lives + "}";
	}

}
