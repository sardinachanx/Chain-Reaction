package main;

public enum GameMode{

	ORIGINAL("Original"), INFINTE("Infinite"), SURVIVAL("Survival");

	private String name;

	private GameMode(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}
