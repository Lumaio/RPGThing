package com.loom.dungeon.Objects;

public class Tile {
	
	public int type;
	public Item item;
	
	public Tile(int type) {
		this.type = type;
	}
	
	public boolean hasItem() {
		return !(item == null);
	}
	
}
