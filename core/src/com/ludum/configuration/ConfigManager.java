package com.ludum.configuration;

import com.badlogic.gdx.math.Vector2;

public class ConfigManager {
	public static int minBlockSize = 32;
	
	public static float moveSpeed = 10;
	public static float accTime = 0.2f;
	public static float jumpHeight = 4.8f;
	public static float gravity = 60;
	
	public static float friction = 0.6f;
	
	/* Player display size */
	public static float swanHeight = 64;
	public static float jupiterHeight = 64;
	public static float sealHeight = 48;
	public static float sealLevitHeight = 74;

	/* Player physics size */
	public static Vector2 swanPhysicsSize = new Vector2(28,56);
	public static Vector2 sealPhysicsSize = new Vector2(28,40);
	public static Vector2 jupiterPhysicsSize = new Vector2(28,56);
	
	public static float portraitSizeX = 32;
	public static float portraitSizeY = 32;

	public static float physicsStepSize = 1f/60f;
	
	/* Limit settings */
	
	public static int outsideLimit = 10;
	
	/* Camera settings */
	public static float camWidth = 32;
	public static float camHeight = 18;
	
	/*Jupiter Dash*/
	public static float dashSpeed = 15;
	public static int   dashLengthMS = 800;
	
}
