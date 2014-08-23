package com.ludum.controls;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.ludum.entity.player.Player;
import com.ludum.game.ClassicMode;

public class PlayerControls extends InputAdapter {
	private Player player;
	private ClassicMode mode;

	public PlayerControls(Player p, ClassicMode m) {
		player = p;
		mode = m;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			player.moveLeft();
			return true;

		case Keys.RIGHT:
			player.moveRight();
			return true;

		case Keys.UP:
			player.jump();
			return true;

		case Keys.SPACE:
			mode.nextCharacter();
			return true;

		case Keys.D:
			player.useSkill1();
			return true;

		case Keys.F:
			player.useSkill2();
			return true;
		}

		return false;
	}
	
	   @Override
	   public boolean keyUp (int keycode) {
		   switch(keycode) {
		   case	Keys.LEFT:
				player.stopLeft();
				return true;
				
		   case Keys.UP:		
				player.stopJump();				
				return true;
				
		   case Keys.RIGHT:		
				player.stopRight();				
				return true;
		   }

		   return false;
	   }
}
