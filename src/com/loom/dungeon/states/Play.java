package com.loom.dungeon.states;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.loom.dungeon.Objects.ConsoleListener;
import com.loom.dungeon.Objects.Enemy;
import com.loom.dungeon.Objects.Item;
import com.loom.dungeon.Objects.Player;
import com.loom.dungeon.Objects.Tile;

public class Play extends BasicGameState {

	static TextField console;
	static Tile[][] map;
	public static Player player;
	static Font font;
	static TrueTypeFont ttf;
	static Rectangle msg;
	static Rectangle invMsg;
	static boolean fill = false;
	static boolean draw = true;
	public static boolean debug;
	static int lvl = 1;
	static int xp = 0;
	static int xpToLevel=25*(lvl+1)*(1+(lvl+1));
	static Image s_player;
	static Image s_item;
	static Image s_wall;
	static ArrayList<Enemy> enemies;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//Instantiate everything
		font = new Font("Arial", Font.PLAIN, 15);
		ttf = new TrueTypeFont(font, false);
		console = new TextField(gc, ttf, 0, 0, 440, 25);
		console.addListener(new ConsoleListener(console));
		map = new Tile[20][20];
		player = new Player();
		msg = new Rectangle(10, gc.getHeight()-180, 780, 170);
		invMsg = new Rectangle(gc.getWidth()-180, 10, 170, 400);
		
		enemies = new ArrayList<Enemy>();
		
		s_player = new Image("res/player.png");
		s_item = new Image("res/item.png");
		s_wall = new Image("res/wall.png");
		
		console.setBackgroundColor(Color.darkGray);
		
		//Create map
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = new Tile(0);
			}
		}
		
		for (int i = 0; i < map.length; i++) {
			map[i][0].type = 1;
		}for (int i = 0; i < map.length; i++) {
			map[0][i].type = 1;
		}for (int i = 0; i < map.length; i++) {
			map[19][i].type = 1;
		}for (int i = 0; i < map.length; i++) {
			map[i][19].type = 1;
		}

		map[5][5].type=2;
		map[6][5].type=2;
		map[7][5].type=2;
		map[8][5].type=2;
		map[9][5].type=2;
		
		Random r = new Random();
		
		for (int i = 1; i < map.length-1; i++) {
			for (int j = 1; j < map.length-1; j++) {
				if (!(i==1 && j==1)) {
					map[i][j].type=0+r.nextInt(2);
					System.out.println(r.nextInt());
				}
			}
		}
		
		map[3][3].item = new Item("Brick");
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//Start World Rendering
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {

				if (map[i][j].type == 1) {
					s_wall.draw((i+3)*16, (j+3)*16);
				}
				if (map[i][j].hasItem()) {
					s_item.draw((i+3)*16, (j+3)*16);
				}
				for (int z = 0; z < enemies.size(); z++) {
					Enemy e = enemies.get(z);
					if (i==e.x && j==e.y) {
						e.sprite.draw((i+3)*16, (j+3)*16);
					}
				}
				if (i==player.x && j==player.y){
					s_player.draw((i+3)*16, (j+3)*16);
				}
				
			}
		}
		//End World Rendering
		
		//Start Message Rendering
		g.setColor(Color.darkGray);
		g.draw(msg);
		
		g.setColor(Color.gray);
		g.drawString("POS: "+player.x+","+player.y, msg.getX()+10, msg.getY()+10);
		g.drawString("Name: "+player.toString(), msg.getX()+10, msg.getY()+25);
		g.drawString("Level: "+lvl, msg.getX()+10, msg.getY()+40);
		g.drawString("Exp To Level: "+xpToLevel, msg.getX()+10, msg.getY()+55);
		g.drawString("Inv Size: "+player.inv.size(), msg.getX()+10, msg.getY()+70);
		g.drawString("Debug: "+debug, msg.getX()+10, msg.getY()+85);
		//End Message Rendering
		
		//Start Inventory Rendering
		g.draw(invMsg);
		for (int i=0; i < player.inv.size(); i++) {
			Item n = player.inv.get(i);
			ttf.drawString(invMsg.getX()+10, invMsg.getY()+(i*15), n.name);
		}
		//End Inventory Rendering
		console.render(gc, g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException {
		if (!(console.hasFocus()))
			getInput(gc);
		else
			gc.getInput().clearKeyPressedRecord();
	}
	
	public void getInput(GameContainer gc) {
		Input i =  gc.getInput();
		i.enableKeyRepeat();
		if (i.isKeyPressed(Input.KEY_UP)) {
			move(0, -1);
		}if (i.isKeyPressed(Input.KEY_DOWN)) {
			move(0, 1);
		}if (i.isKeyPressed(Input.KEY_LEFT)) {
			move(-1, 0);
		}if (i.isKeyPressed(Input.KEY_RIGHT)) {
			move(1, 0);
		}
		if (i.isKeyPressed(Input.KEY_G)) {
			if (map[player.x][player.y].hasItem()) {
				player.inv.add(map[player.x][player.y].item);
				map[player.x][player.y].item=null;
			}
		}
		if (i.isKeyPressed(Input.KEY_D)) {
			if (!(map[player.x][player.y].hasItem()) && !(player.inv.isEmpty())) {
				map[player.x][player.y].item=player.inv.get(0);
				player.inv.remove(0);
			}
		}
		
	}
	
	public void move(int x, int y) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
		}
		if (!(map[player.x+x][player.y+y].type == 1)) {
			if (map[player.x+x][player.y+y].type == 2) {
				map[player.x+x][player.y+y].type=3;
			}else {
				player.x+=x;
				player.y+=y;
			}
		}
	}

	public int getID() {
		return 0;
	}

}