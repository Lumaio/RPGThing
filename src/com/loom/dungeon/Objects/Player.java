package com.loom.dungeon.Objects;

import java.util.ArrayList;

public class Player {
	public int x;
	public int y;
	public ArrayList<Item> inv;
	
	public Player() {
		this.x = 1;
		this.y = 1;
		this.inv = new ArrayList<Item>();
	}
	
}
