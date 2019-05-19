package com.wggy.prune.info1113;

public class Grid {
	private Player owner;
	private int atomCount;
	
	public Grid(Player owner, int atomCount){
		this.owner = owner;
		this.atomCount = atomCount;
	}
	
	public Player getOwner(){
		return owner;
	}
	
	public void setOwner(Player owner){
		this.owner = owner;
	}
	
	public int getAtomCount(){
		return atomCount;
	}
	
	public void setAtomCount(int atomCount){
		this.atomCount = atomCount;
	}

	public void inc() {
		this.atomCount++;
	}

	public void revert() {
		this.atomCount = 0;
		this.owner = null;
	}
}
