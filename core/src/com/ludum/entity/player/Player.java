package com.ludum.entity.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.ludum.configuration.ConfigManager;
import com.ludum.entity.Drawable;
import com.ludum.entity.Entity;
import com.ludum.map.WorldState;
import com.ludum.map.WorldType;
import com.ludum.physics.PhysicsDataStructure;
import com.ludum.physics.PhysicsManager;
import com.ludum.physics.PhysicsObject;
import com.ludum.physics.PhysicsObjectType;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;
import com.ludum.skill.Skill;

public class Player extends Entity implements Drawable, PhysicsObject {

	private Skill s1;
	private Skill s2;

	private WorldState worldState;
	private PlayerState state;

	private Body body;
	private Vector2 physicsSize;
	private Vector2 acc;
	private ArrayList<PhysicsDataStructure> botContactList;
	private float stateTime = 0;
	private TextureRegion currentFrame;

	private TextureRegion portrait;
	
	private int endContact;
	
	private TextureType textureType;
	
	public Player(Vector2 p, Skill s1, Skill s2, TextureRegion port, WorldState s) {
		this.s1 = s1;
		this.s2 = s2;
		portrait = port;
		this.worldState = s;
		endContact = 0;
		pos = p.cpy();

		botContactList = new ArrayList<PhysicsDataStructure>();

		acc = new Vector2(0, 0);
		size = new Vector2(ConfigManager.playerSizeX, ConfigManager.playerSizeY);
		physicsSize = new Vector2(ConfigManager.playerPhysSizeX,
				ConfigManager.playerPhysSizeY);
		state = PlayerState.FALLING;
		init(pos);
	}

	public void init(Vector2 p) {
		PhysicsDataStructure s = new PhysicsDataStructure(this,
				PhysicsObjectType.PLAYER);
		this.body = PhysicsManager.getInstance().createDynamicRectangle(
				p.cpy().mulAdd(size, 0.5f), physicsSize, s);
		pos.set(p);
	}

	public void reset(Vector2 p) {
		pos.set(p);
		body.setTransform((p.x + size.x / 2) * PhysicsManager.WORLD_TO_BOX,
				(p.y + size.y / 2) * PhysicsManager.WORLD_TO_BOX,
				body.getAngle());
		body.setLinearVelocity(0, 0);
	}

	public void moveRight() {
		acc.x += 1;
	}

	public void stopRight() {
		acc.x -= 1;
	}

	public void moveLeft() {
		acc.x -= 1;
	}

	public void stopLeft() {
		acc.x += 1;
	}

	public void jump() {
		if (!botContactList.isEmpty())
			acc.y += 1;
	}

	public void stopJump() {
		acc.y -= 1;
	}

	public void useSkill1() {
		s1.use();
	}

	public void useSkill2() {
		s2.use();
	}

	public void updatePhysics(float dt) {

			Vector2 speed = body.getLinearVelocity();


			updateRunning(speed.x, dt);
			updateJumping(speed, dt);
			updateState();		

	}
	
	protected void updateRunning(float horizontalSpeed, float dt) {
		float nextSpeedX;
		if (acc.x > 0) {
			nextSpeedX = Math.min(horizontalSpeed + ConfigManager.moveSpeed * dt
					/ ConfigManager.accTime, ConfigManager.moveSpeed);
		} else if (acc.x < 0) {
			nextSpeedX = Math.max(horizontalSpeed - ConfigManager.moveSpeed * dt
					/ ConfigManager.accTime, -ConfigManager.moveSpeed);
		} else {
			nextSpeedX = ConfigManager.friction * horizontalSpeed;
		}
		float speedChangeX = nextSpeedX - horizontalSpeed;
		float impulseX = body.getMass() * speedChangeX;
		body.applyLinearImpulse(new Vector2(impulseX, 0),
				body.getWorldCenter(), true);
	}

	
	protected void updateJumping(Vector2 speed, float dt) {
		if (acc.y > 0) {
			float speedChangeY = (float) (Math.sqrt(2 * ConfigManager.gravity
					* ConfigManager.jumpHeight) - speed.y);
			float impulseY = body.getMass() * speedChangeY;
			body.applyLinearImpulse(new Vector2(0, impulseY),
					body.getWorldCenter(), true);			
		} else if (acc.y < 0) {
			if (speed.y > 0) {
				body.setLinearVelocity(speed.x, 0);			
			}
		} 

		
		acc.y = 0;
	}
	
	protected void updateState() {
		Vector2 speed = body.getLinearVelocity();
		
		if (speed.y > 0) {
			state = PlayerState.JUMPING;
		} else if (speed.y < 0) {
			state = PlayerState.FALLING;
		} else if (acc.x != 0) {
			state = PlayerState.RUNNING;
		} else {
			state = PlayerState.STANDING;
		}
	}
	
	
	public void update(float dt) {
		Vector2 newPos = body.getPosition().scl(PhysicsManager.BOX_TO_WORLD);
		pos.set(newPos.x - size.x / 2, newPos.y - size.y / 2);
		stateTime += dt;
		if(state == PlayerState.JUMPING){
			textureType = TextureType.SwanJump;
		}else if(state == PlayerState.RUNNING){
			textureType = TextureType.SwanRun;
		}else if(state == PlayerState.STANDING){
			textureType = TextureType.SwanIdle;
		}
		currentFrame = TextureManager.getInstance().getTextureRegion(textureType, stateTime);
	}

	public Vector2 getPosition() {
		return pos;
	}
	
	public boolean isAtEnd() {
		return endContact>0;
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(currentFrame, pos.x, pos.y, ConfigManager.playerSizeX,
				ConfigManager.playerSizeY);
	}


	public void drawUI(Batch spriteBatch, Vector2 pos, boolean isSelected) {
		spriteBatch.begin();
		spriteBatch.draw(portrait,pos.x,pos.y,
				ConfigManager.portraitSizeX,ConfigManager.portraitSizeY);
		if (isSelected)
			spriteBatch.draw(portrait, pos.x + ConfigManager.portraitSizeX/2 -5,
					pos.y + ConfigManager.portraitSizeY, 5, 5);

		spriteBatch.end();
	}


	private void checkBotContact(PhysicsDataStructure struct, Contact contact) {
		WorldManifold manifold = contact.getWorldManifold();
		Vector2 normal = manifold.getNormal();
		if (!contact.getFixtureA().getBody().getUserData().equals(struct)) {
			normal.scl(-1);
		}

		Vector2 bot = new Vector2(0, 1);

		if (bot.isCollinear(normal)) {
			botContactList.add(struct);
		}
	}

	@Override
	public void BeginContactHandler(PhysicsDataStructure struct, Contact contact) {
		switch (struct.type) {
		case LIGHTEDGE:
		case DARKEDGE:
			checkBotContact(struct, contact);
			break;
		case PLAYER:
			checkBotContact(struct, contact);
			break;
		case END:
			endContact++;
			break;
		default:
			break;
		}
	}

	@Override
	public void EndContactHandler(PhysicsDataStructure struct, Contact contact) {
		switch (struct.type) {
		case LIGHTEDGE:
		case DARKEDGE:
			if (botContactList.contains(struct)) {
				botContactList.remove(struct);
			}
			break;
		case PLAYER:
			if (botContactList.contains(struct)) {
				botContactList.remove(struct);
			}
			break;
		case END:
			endContact--;
			break;
		default:
			break;
		}
	}

	@Override
	public void PreContactHandler(PhysicsDataStructure struct, Contact contact) {
		switch (struct.type) {
		case LIGHTEDGE:
			if(worldState.getState() == WorldType.DARK)
				contact.setEnabled(false);
			break;
		case DARKEDGE:
			if(worldState.getState() == WorldType.LIGHT)
				contact.setEnabled(false);
			break;
		default:
			break;
		}
	}
}
