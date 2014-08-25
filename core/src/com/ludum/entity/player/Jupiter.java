package com.ludum.entity.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.ludum.configuration.ConfigManager;
import com.ludum.map.WorldState;
import com.ludum.physics.PhysicsDataStructure;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;
import com.ludum.skill.Dash;


public class Jupiter extends Player{
	private boolean isUsed = false;
	private ArrayList<Player> contactPlayers = new ArrayList<Player>();
	
	public Jupiter(Vector2 spawn, Vector2 mapSize, Texture port, Texture port2, WorldState s) {
		super(spawn, mapSize, port,port2, s);
		height = ConfigManager.jupiterHeight;
		physicsSize = ConfigManager.jupiterPhysicsSize;
	}
	
	@Override
	public void updatePhysics(float dt) {
		Vector2 speed = body.getLinearVelocity();

		if (state == PlayerState.DASHING)
			updateDashing(s1, s2);
		else
			updateRunning(speed.x, dt);
		
		updateJumping(speed, dt);
		updateState();
		
		checkDeath();
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
		if(state == PlayerState.JUMPING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
				textureType, 0);
		}
		if(state == PlayerState.FALLING){
			currentFrame = TextureManager.getInstance().getTextureRegion(
					textureType, 1000);
		}
	}
	
	@Override
	public void init() {
		super.init();
		s1 = dashLeft;//new LeftDash(body);
		s2 = dashRight;//new RightDash(body);
	}

	@Override
	public void useSkill1(){
		if (!isUsed) {
			super.useSkill1();
			startDash();
		}
	}
	
	@Override
	public void useSkill2(){
		if (!isUsed) {
			super.useSkill2();
			startDash();
		}
	}
	
	protected void startDash() {
		/* If in contact with player, don't ! */
		isUsed = true;			
		state = PlayerState.DASHING;
		dashTimer = 0;
		body.setGravityScale(0);
		boolean found = false;
		for (Player p: contactPlayers) {
			giveMyDashToThatGuyIfNecessary(p);
			found = true;
		}
		if (found)
			stopDash();
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
		if (((Dash) s1).isDashing()) {
			stopDash();
			p.dashLeft();
		} else if (((Dash) s2).isDashing()) {
			stopDash();
			p.dashRight();
		}		
	}
	
	@Override
	protected void updateState() {
		Vector2 speed = body.getLinearVelocity();
		if(state != PlayerState.DASHING && speed.y == 0)
				isUsed = false;
		super.updateState();
	}	
}
