package com.loom.dungeon.Objects;

import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

import com.loom.dungeon.states.Play;

public class ConsoleListener implements ComponentListener {
	
	public TextField tf;
	Player player;
	
	public ConsoleListener(TextField tf) {
		this.tf = tf;
		player=new Player();
	}

	@Override
	public void componentActivated(AbstractComponent ac) {
		String[] s = tf.getText().split("\\s+");
		StringBuilder sb = new StringBuilder();
		
		if (s[0].equals("give")) {
			for (int i = 1; i < s.length; i++) {
				sb.append(s[i]+" ");
			}
			System.out.println(sb.toString());
			Play.player.inv.add(new Item(sb.toString()));
		} else if (s[0].equals("debug")) {
			if (s[1].equals("on")) {
				Play.debug=true;
			}else if (s[1].equals("off")) {
				Play.debug=false;
			}
		}
		tf.setText("");
	}

}
