package com.ludum.entity.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.ludum.configuration.ConfigManager;
import com.ludum.entity.Drawable;
import com.ludum.skill.Skill;
import com.ludum.entity.Entity;

public class Player extends Entity implements Drawable{
	
	private Skill s1;
	private Skill s2;

	private Vector2 position;
	private Vector2 size;
	private Vector2 physicsSize;
	
	private Vector2 acc;
	
	
	public Player(Skill s1, Skill s2){
		this.s1 = s1;
		this.s2 = s2;
		
		acc = new Vector2(0,0);
		size = new Vector2(ConfigManager.playerSizeX,ConfigManager.playerSizeY);
		physicsSize = new Vector2(ConfigManager.playerPhysSizeX,ConfigManager.playerPhysSizeY);
	}
	
	public void moveRight() {
		
	}
	
	public void moveLeft() {
		
	}
	
	public void jump() {
		
	}
	
	public void useSkill1(){
		s1.use();
	}
	
	public void useSkill2(){
		s2.use();
	}

	@Override
	public void draw(Batch batch) {
		
	}
}
