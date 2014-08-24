package com.ludum.rendering;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.ludum.configuration.ConfigManager;
import com.ludum.entity.player.Player;

public class CharacterCenteredCamera extends OrthographicCamera{
	private Player player;

	public CharacterCenteredCamera(Player p) {
		super(ConfigManager.camWidth * ConfigManager.minBlockSize, ConfigManager.camHeight * ConfigManager.minBlockSize);
		this.player = p;
		position.set(player.getPosition(), 5);
		update();
	}

	public void changeCharacter(Player p) {
		this.player = p;
	}

	public void follow() {
		position.set(player.getPosition(), 5);
		update();
	}
}
