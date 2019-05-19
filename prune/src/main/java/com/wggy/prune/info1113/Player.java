package com.wggy.prune.info1113;

public class Player {
	private int gridsOwned = 0;
	private String colour;
	
	public Player(String colour, int gridsOwned){
		this.colour = colour;
		this.gridsOwned = gridsOwned;
	}
	
	public int getGridsOwned(){
		return gridsOwned;
	}
	
	public void setGridsOwned(int gridsOwned){
		this.gridsOwned = gridsOwned;
	}
	
	public String getColour(){
		return colour;
	}
	
	public void setColour(String colour){
		this.colour = colour;
	}

	public void inc() {
		this.gridsOwned++;
	}
}
