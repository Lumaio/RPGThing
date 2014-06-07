package com.loom.dungeon.Objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.loom.dungeon.states.Play;

public class Enemy {
	
	public Image sprite;
	Player p;
	
	public int x;
	public int y;
	public int hp=50;
	public int dmg=5;
	
	public Enemy(int x, int y) throws SlickException {
		sprite = new Image("res/enemy.png");
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		p=Play.player;
		if (!(x-1 == p.x && y-1 == p.y) || !(x+1 == p.x && y+1 == p.y)) {
			if (p.x < x)
				x-=1;
			if (p.y < y)
				y-=1;
			if (p.x > x)
				x+=1;
			if (p.y > y)
				y+=1;
		}
	}
	
}
