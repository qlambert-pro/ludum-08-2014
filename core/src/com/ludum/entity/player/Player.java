package com.ludum.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

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
import com.ludum.physics.PhysicsDataStructure;
import com.ludum.physics.PhysicsManager;
import com.ludum.physics.PhysicsObject;
import com.ludum.physics.PhysicsObjectType;
import com.ludum.rendering.TextureManager;
import com.ludum.rendering.TextureType;
import com.ludum.skill.Skill;
import com.ludum.entity.Entity;


public class Player extends Entity implements Drawable, PhysicsObject {

	private Skill s1;
	private Skill s2;

	private PlayerState state;

	private Body body;
	private Vector2 physicsSize;
	private Vector2 acc;
	private ArrayList<PhysicsDataStructure> botContactList;
	private float stateTime = 0;
	private TextureRegion currentFrame;
	private SpriteBatch spriteBatch;
	
	public Player(Vector2 p, Skill s1, Skill s2) {
		this.s1 = s1;
		this.s2 = s2;
		
		pos = p.cpy();

		botContactList = new ArrayList<PhysicsDataStructure>();

		acc = new Vector2(0, 0);
		size = new Vector2(ConfigManager.playerSizeX, ConfigManager.playerSizeY);
		physicsSize = new Vector2(ConfigManager.playerPhysSizeX,
				ConfigManager.playerPhysSizeY);
		state = PlayerState.JUMPING;
		init(pos);
		spriteBatch = new SpriteBatch();
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
		if (botContactList.isEmpty())
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

		float nextSpeedX;
		if (acc.x > 0) {
			nextSpeedX = Math.min(speed.x + ConfigManager.moveSpeed * dt
					/ ConfigManager.accTime, ConfigManager.moveSpeed);
		} else if (acc.x < 0) {
			nextSpeedX = Math.max(speed.x - ConfigManager.moveSpeed * dt
					/ ConfigManager.accTime, -ConfigManager.moveSpeed);
		} else {
			nextSpeedX = ConfigManager.friction * speed.x;
		}
		float speedChangeX = nextSpeedX - speed.x;
		float impulseX = body.getMass() * speedChangeX;
		body.applyLinearImpulse(new Vector2(impulseX, 0),
				body.getWorldCenter(), true);

		if (acc.y > 0) {
			float speedChangeY = (float) (Math.sqrt(2 * ConfigManager.gravity
					* ConfigManager.jumpHeight) - speed.y);
			float impulseY = body.getMass() * speedChangeY;
			body.applyLinearImpulse(new Vector2(0, impulseY),
					body.getWorldCenter(), true);
		}
		else if (acc.y < 0) {
			if(speed.y > 0)
				body.setLinearVelocity(speed.x, 0);
		}
		acc.y = 0;

		if (botContactList.isEmpty()) {
			state = PlayerState.JUMPING;
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
		currentFrame = TextureManager.getInstance().getTextureRegion(TextureType.PlayerRun, stateTime);
	}

	public Vector2 getPosition() {
		return pos;
	}

	@Override
	public void draw(Batch batch) {

		spriteBatch.begin();
		spriteBatch.draw(currentFrame,pos.x,pos.y,ConfigManager.playerSizeX,ConfigManager.playerPhysSizeY);
		spriteBatch.end();

	}

	private void checkBotContact(PhysicsDataStructure b, Contact contact) {
		WorldManifold manifold = contact.getWorldManifold();
		Vector2 normal = manifold.getNormal();
		if (contact.getFixtureA().getBody().getUserData().equals(b)) {
			normal.scl(-1);
		}

		Vector2 bot = new Vector2(0, 1);
		if (bot.isCollinear(normal)) {
			botContactList.add(b);
		}
	}

	@Override
	public void BeginContactHandler(PhysicsDataStructure struct, Contact contact) {
		switch (struct.type) {
		case EDGE:
			checkBotContact(struct, contact);
			break;
		case PLAYER:
			checkBotContact(struct, contact);
			break;
		default:
			break;
		}
	}

	@Override
	public void EndContactHandler(PhysicsDataStructure struct, Contact contact) {
		switch (struct.type) {
		case EDGE:
			if (botContactList.contains(struct)) {
				botContactList.remove(struct);
			}
			break;
		case PLAYER:
			if (botContactList.contains(struct)) {
				botContactList.remove(struct);
			}
			break;
		default:
			break;
		}
	}
}
