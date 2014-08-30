package com.ludum.entity.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.ludum.skill.Dash;
import com.ludum.skill.LeftDash;
import com.ludum.skill.RightDash;
import com.ludum.skill.Skill;
import com.ludum.sound.SoundManager;

public abstract class Player extends Entity implements Drawable, PhysicsObject {

	protected enum PlayerJumpState {
		NONE, JUMP, STOPJUMP
	}

	protected Vector2 spawn;
	protected Vector2 mapSize;

	protected Skill s1;
	protected Skill s2;

	protected WorldState worldState;
	protected PlayerState state;

	protected Body body;
	protected Vector2 physicsSize;
	protected boolean moveRight;
	protected boolean moveLeft;
	protected PlayerJumpState jumpState;

	protected ArrayList<PhysicsDataStructure> botContactList;
	protected float stateTime = 0;
	protected TextureRegion currentFrame;
	protected float height;

	protected Texture portrait;
	protected Texture portraitSelected;

	protected int end1Contact;
	protected int end2Contact;
	protected int end3Contact;

	protected TextureType textureType;

	protected long dashTimer = 0;
	protected Dash dashLeft = null;
	protected Dash dashRight = null;

	protected boolean isDead;
	protected boolean canWalkOnWater = false;

	private boolean flipH = false;
	private boolean flipV = false;

	public Player(Vector2 spawn, Vector2 mapSize, Texture port,
			Texture portSelect, WorldState s) {
		portrait = port;
		portraitSelected = portSelect;
		this.worldState = s;
		end1Contact = 0;
		end2Contact = 0;
		end3Contact = 0;
		pos = spawn.cpy();
		this.spawn = spawn.cpy();
		this.mapSize = mapSize.cpy();

		botContactList = new ArrayList<PhysicsDataStructure>();

		state = PlayerState.FALLING;
		jumpState = PlayerJumpState.NONE;
		moveRight = false;
		moveLeft = false;
		isDead = false;

	}

	public void init() {
		PhysicsDataStructure s = new PhysicsDataStructure(this,
				PhysicsObjectType.PLAYER);
		this.body = PhysicsManager.getInstance().createDynamicRectangle(
				pos.cpy(), physicsSize, s);

		dashLeft = new LeftDash(body);
		dashRight = new RightDash(body);
	}

	public void respawn() {
		pos.set(spawn);
		body.setTransform((spawn.x) * PhysicsManager.WORLD_TO_BOX, (spawn.y)
				* PhysicsManager.WORLD_TO_BOX, body.getAngle());
		body.setLinearVelocity(0, 0);
		body.setGravityScale(1);
		state = PlayerState.FALLING;
		isDead = false;
	}

	public void stop() {
		moveRight = false;
		moveLeft = false;
		jumpState = PlayerJumpState.NONE;
	}

	public void moveRight() {
		moveRight = true;
	}

	public void stopRight() {
		moveRight = false;
	}

	public void moveLeft() {
		moveLeft = true;
	}

	public void stopLeft() {
		moveLeft = false;
	}

	public void jump() {
		jumpState = PlayerJumpState.JUMP;
	}

	public void stopJump() {
		jumpState = PlayerJumpState.STOPJUMP;
	}

	public void useSkill1() {
		if (s1 != null)
			state = s1.use();
	}

	public void useSkill2() {
		if (s2 != null)
			state = s2.use();
	}

	public void updatePhysics(float dt) {

		if (s1 != null && s1.isActive())
			s1.update(dt);
		else if (s2 != null && s2.isActive())
			s2.update(dt);
		else if (dashLeft != null && dashLeft.isActive())
			dashLeft.update(dt);
		else if (dashRight != null && dashRight.isActive())
			dashRight.update(dt);
		else {
			updateRunning(dt);
			updateJumping(dt);
		}

		updateState();

		checkDeath();
	}

	protected void dashLeft() {
		if (dashLeft == null)
			return;
		
		if ((s1 == null || !s1.isActive()) &&
			(s2 == null || !s2.isActive()) &&
			(dashRight == null || !dashRight.isActive()))
			state = dashLeft.use();		
	}

	protected void dashRight() {
		if (dashRight == null)
			return;
		
		if ((s1 == null || !s1.isActive()) &&
			(s2 == null || !s2.isActive()) &&
			(dashLeft == null || !dashLeft.isActive()))
			state = dashRight.use();
	}

	public void checkDeath() {
		if (isDead)
			respawn();

		/* check if the player is outside limit */
		if ((pos.x < -ConfigManager.outsideLimit * ConfigManager.minBlockSize)
				|| (pos.y < -ConfigManager.outsideLimit
						* ConfigManager.minBlockSize)
				|| (pos.x > mapSize.x + ConfigManager.outsideLimit
						* ConfigManager.minBlockSize)
				|| (pos.y > mapSize.y + ConfigManager.outsideLimit
						* ConfigManager.minBlockSize))
			respawn();
	}

	protected void updateRunning(float dt) {
		Vector2 speed = body.getLinearVelocity();
		float nextSpeedX;
		if (moveRight && !moveLeft) {
			nextSpeedX = Math.min(speed.x + ConfigManager.moveSpeed * dt
					/ ConfigManager.accTime, ConfigManager.moveSpeed);
		} else if (!moveRight && moveLeft) {
			nextSpeedX = Math.max(speed.x - ConfigManager.moveSpeed * dt
					/ ConfigManager.accTime, -ConfigManager.moveSpeed);
		} else {
			nextSpeedX = ConfigManager.friction * speed.x;
		}
		float speedChangeX = nextSpeedX - speed.x;
		float impulseX = body.getMass() * speedChangeX;
		body.applyLinearImpulse(new Vector2(impulseX, 0),
				body.getWorldCenter(), true);

	}

	protected void updateJumping(float dt) {
		Vector2 speed = body.getLinearVelocity();
		if (jumpState == PlayerJumpState.JUMP && !botContactList.isEmpty()) {
			SoundManager.getInstance().jump();
			float speedChangeY = (float) (Math.sqrt(2 * ConfigManager.gravity
					* ConfigManager.jumpHeight) - speed.y);
			float impulseY = body.getMass() * speedChangeY;
			body.applyLinearImpulse(new Vector2(0, impulseY),
					body.getWorldCenter(), true);
		} else if (jumpState == PlayerJumpState.STOPJUMP) {
			if (speed.y > 0) {
				body.setLinearVelocity(speed.x, 0);
			}
		}

		jumpState = PlayerJumpState.NONE;
	}

	protected void updateState() {
		if ((s1 == null || !s1.isActive()) && (s2 == null || !s2.isActive())
				&& (dashLeft == null || !dashLeft.isActive())
				&& (dashRight == null || !dashRight.isActive())) {

			Vector2 speed = body.getLinearVelocity();

			if (speed.y > 0) {
				state = PlayerState.JUMPING;
			} else if (speed.y < 0 || botContactList.isEmpty()) {
				state = PlayerState.FALLING;
			} else if (moveRight ^ moveLeft) {
				state = PlayerState.RUNNING;
			} else {
				state = PlayerState.STANDING;
			}
		}
	}

	public void update(float dt) {
		Vector2 newPos = body.getPosition().scl(PhysicsManager.BOX_TO_WORLD);
		pos.set(newPos.x, newPos.y);
		stateTime += dt;
		currentFrame = TextureManager.getInstance().getTextureRegion(
				textureType, stateTime);
	}

	public Vector2 getPosition() {
		return pos;
	}

	public boolean isAtEnd1() {
		return end1Contact > 0;
	}

	public boolean isAtEnd2() {
		return end2Contact > 0;
	}

	public boolean isAtEnd3() {
		return end3Contact > 0;
	}

	@Override
	public void draw(Batch batch) {
		float sizeX = height * currentFrame.getRegionWidth()
				/ currentFrame.getRegionHeight();
		batch.draw(currentFrame, pos.x - sizeX / 2, pos.y - height / 2, sizeX,
				height);
	}

	public void drawUI(Batch spriteBatch, Vector2 pos, boolean isSelected) {
		spriteBatch.begin();

		if (isSelected)
			spriteBatch.draw(portraitSelected, pos.x, pos.y,
					ConfigManager.portraitSizeX, ConfigManager.portraitSizeY);
		else
			spriteBatch.draw(portrait, pos.x, pos.y,
					ConfigManager.portraitSizeX, ConfigManager.portraitSizeY);
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
			if (worldState.getState() == WorldType.LIGHT)
				checkBotContact(struct, contact);
			break;
		case DARKEDGE:
			if (worldState.getState() == WorldType.DARK)
				checkBotContact(struct, contact);
			break;
		case PLAYER:
			checkBotContact(struct, contact);
			break;
		case END1:
			end1Contact++;
			break;
		case END2:
			end2Contact++;
			break;
		case END3:
			end3Contact++;
			break;
		case SPIKE:
			isDead = true;
			break;
		case LIGHTWATER:
			if (worldState.getState() == WorldType.LIGHT) {
				if (canWalkOnWater) {
					checkBotContact(struct, contact);
				} else {
					isDead = true;
				}
			}
			break;
		case DARKWATER:
			if (worldState.getState() == WorldType.DARK) {
				if (canWalkOnWater) {
					checkBotContact(struct, contact);
				} else {
					isDead = true;
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void EndContactHandler(PhysicsDataStructure struct, Contact contact) {
		switch (struct.type) {
		case LIGHTWATER:
		case DARKWATER:
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
		case END1:
			end1Contact--;
			break;
		case END2:
			end2Contact--;
			break;
		case END3:
			end3Contact--;
			break;
		default:
			break;
		}
	}

	@Override
	public void PreContactHandler(PhysicsDataStructure struct, Contact contact) {
		switch (struct.type) {
		case LIGHTEDGE:
			if (worldState.getState() == WorldType.DARK)
				contact.setEnabled(false);
			break;
		case DARKEDGE:
			if (worldState.getState() == WorldType.LIGHT)
				contact.setEnabled(false);
			break;
		case LIGHTWATER:
			if (worldState.getState() == WorldType.DARK) {
				contact.setEnabled(false);
			}
			break;
		case DARKWATER:
			if (worldState.getState() == WorldType.LIGHT) {
				contact.setEnabled(false);
			}
			break;
		default:
			break;
		}
	}
}
