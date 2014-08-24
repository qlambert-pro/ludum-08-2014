package com.ludum.rendering;

public enum TextureType {
	
	SwanJump(1, "sprite_jump_swan.png"),
	SwanIdle(4, "sprite_idle_swan.png"),
	SwanRun(6, "sprite_run_swan.png"),
	SwanHightJump(1, "sprite_high_jump_swan.png"),
	SwanWallClimbing(1, "sprite_wall_climbing_swan.png"),
	SwanAttack(5, "sprite_attack_swan.png"),
	JupiterJumpFall(2, "sprite_jump_fall_jupiter.png"),
	JupiterIdle(6, "sprite_idle_jupiter.png"),
	JupiterRun(8, "sprite_walk_jupiter.png"),
	Portraits(2, "playerPortraits.png");

	private int nbFrame;
	private String fileName;

	private TextureType(int nbFrame,
			String fileName) {
		this.nbFrame = nbFrame;
		this.fileName = fileName;
	}


	public int nbFrame() {
		return nbFrame;
	}

	public String getFileName() {
		return fileName;
	}

}
