package com.loom.dungeon.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.loom.dungeon.states.Play;

public class Main extends StateBasedGame {

	public Main(String name) {
		super(name);
		
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new Play());
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main("Dungeon Crawler"));
		app.setDisplayMode(800, 600, false);
        app.setSmoothDeltas(true);
        app.setTargetFrameRate(60);
        app.setShowFPS(false);
        app.start();
	}

}
