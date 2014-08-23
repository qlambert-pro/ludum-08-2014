package com.ludum.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	private Vector2 pos;
	private Vector2 size;
	
	public void setPosition(float posX, float posY){
		this.pos.x = posX;
		this.pos.y = posY;
	}
	
	public float getX(){
		return pos.x;
	}
	
	public float getY(){
		return pos.y;
	}
	
	public Vector2 getPosition(){
		return pos;
	}
}
