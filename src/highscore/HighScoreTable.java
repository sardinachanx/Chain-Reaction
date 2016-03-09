package highscore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NavigableSet;
import java.util.TreeSet;

public class HighScoreTable implements Serializable{

	private static final long serialVersionUID = -6706569067121394792L;

	private NavigableSet<HighScore> highScores;

	public HighScoreTable(){
		highScores = new TreeSet<HighScore>();
	}

	public void addHighScore(HighScore highScore){
		highScores.add(highScore);
		highScores.remove(highScores.last());
	}

	public NavigableSet<HighScore> getHighScores(){
		return highScores;
	}

	public static void write(HighScoreTable hst, String name){
		File highscore = new File(name);
		ObjectOutputStream outputStream = null;
		try{
			outputStream = new ObjectOutputStream(new FileOutputStream(highscore));
			outputStream.writeObject(hst);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(outputStream != null){
				try{
					outputStream.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	public static HighScoreTable read(String name){
		File highscore = new File(name);
		ObjectInputStream inputStream = null;
		try{
			highscore.createNewFile();
			inputStream = new ObjectInputStream(new FileInputStream(highscore));
			Object object = inputStream.readObject();
			if(object instanceof HighScoreTable){
				return (HighScoreTable) object;
			}
			else{
				return null;
			}
		}
		catch(IOException e){
		}
		catch(ClassNotFoundException e){
		}
		finally{
			if(inputStream != null){
				try{
					inputStream.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
