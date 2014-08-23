package com.ludum.entity.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.ludum.entity.Drawable;
import com.ludum.skill.Skill;
import com.ludum.entity.Entity;

public class Player extends Entity implements Drawable{
	
	private Skill s1;
	private Skill s2;
	
	
	public Player(Skill s1, Skill s2){
		this.s1 = s1;
		this.s2 = s2;
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
