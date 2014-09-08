package com.ludum.entity.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.ludum.configuration.ConfigManager;
import com.ludum.map.WorldState;
import com.ludum.physics.PhysicsDataStructure;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;


public class Jupiter extends Player{
	private boolean isUsed = false;
	private ArrayList<Player> contactPlayers = new ArrayList<Player>();
	
	public Jupiter(Vector2 spawn, Vector2 mapSize, Texture port, Texture port2, WorldState s) {
		super(spawn, mapSize, port,port2, s);
		height = ConfigManager.jupiterHeight;
		physicsSize = ConfigManager.jupiterPhysicsSize;
	}	
	
	@Override
	public void update(float dt){
		if(state == PlayerState.JUMPING || state == PlayerState.FALLING){
			textureType = TextureType.JupiterJumpFall;
		}else if(state == PlayerState.RUNNING){
			textureType = TextureType.JupiterRun;
		}else if(state == PlayerState.STANDING){
			textureType = TextureType.JupiterIdle;
		}
		super.update(dt);
		//TODO clean
		if(state == PlayerState.JUMPING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
				textureType, 0);
		}
		if(state == PlayerState.FALLING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
					textureType, 1000);
		}
		if(state == PlayerState.DASHING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
					textureType, 1000);
		}
		if (currentFrame.isFlipX() != flipX)
			currentFrame.flip(true, flipY);	
	}
	
	@Override
	public void init() {
		super.init();
		s1 = dashLeft;
		s2 = dashRight;
	}

	@Override
	public void useSkill1(){
		if (isUsed) {
			return;
		}
		flipX = true;
		isUsed = true;
		if(contactPlayers.isEmpty()) {
			super.useSkill1();			
		} else if (!contactPlayers.isEmpty()) {
			for (Player p: contactPlayers) {
				p.dashLeft();
				//TODO transferLeftDashIfNecessary(p);
				// bug if contact
			}
		}
	}
	
	@Override
	public void useSkill2(){
		if (isUsed) {
			return;
		}
		flipX = false;
		isUsed = true;
		if (contactPlayers.isEmpty()) {
			super.useSkill2();			
		} else if (!contactPlayers.isEmpty()) {
			for (Player p: contactPlayers) {
				p.dashRight();
				//TODO transferRightDashIfNecessary(p);
				// bug if contact
			}
		}
	}
	
	@Override
	public void BeginContactHandler(PhysicsDataStructure struct, Contact contact) {
		super.BeginContactHandler(struct, contact);
		switch (struct.type) {
		case PLAYER:
			contactPlayers.add((Player) struct.obj);
			giveMyDashToThatGuyIfNecessary((Player) struct.obj); 
		default:;				
		}
	}
	
	@Override
	public void EndContactHandler(PhysicsDataStructure struct, Contact contact) {
		super.EndContactHandler(struct, contact);
		switch (struct.type) {
		case PLAYER:
			contactPlayers.remove((Player) struct.obj);
		default:;
		}
	}
	
	private void giveMyDashToThatGuyIfNecessary(Player p) {
		//TODO test contact sidewise
		if (s1.isActive()) {
			dashLeft.endDash();
			p.dashLeft();			
		} else if (s2.isActive()) {
			dashRight.endDash();
			p.dashRight();
		}		
	}
	
	@Override
	protected void updateState() {
		super.updateState();
		if (state == PlayerState.RUNNING ||
			state == PlayerState.STANDING)
			isUsed = false;
	}	
}
