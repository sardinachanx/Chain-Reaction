package main;

/**
 * A set of GameMode constants.
 * @author tchan17
 *
 */
public enum GameMode{

	ORIGINAL("Original"), INFINITE("Infinite"), SURVIVAL("Survival");

	private String name;

	private GameMode(String name){
		this.name = name;
	}

	/**
	 * Gets the name of the GameMode.
	 * @return the name of the GameMode
	 */
	public String getName(){
		return name;
	}
}
