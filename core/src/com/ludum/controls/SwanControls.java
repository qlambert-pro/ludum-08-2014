package com.ludum.controls;

import com.badlogic.gdx.Input.Keys;
import com.ludum.entity.player.Player;
import com.ludum.game.ClassicMode;

public class SwanControls extends PlayerControls {

	public SwanControls(Player p, ClassicMode m) {
		super(p, m);		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {		
		case Keys.F:			
			return false;
		}

		return super.keyDown(keycode);
	}

}
