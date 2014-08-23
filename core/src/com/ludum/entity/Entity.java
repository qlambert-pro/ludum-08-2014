package com.ludum.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	protected Vector2 pos;
	protected Vector2 size;
	
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
