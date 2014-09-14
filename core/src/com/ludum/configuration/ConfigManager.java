package com.ludum.configuration;

import com.badlogic.gdx.math.Vector2;

public class ConfigManager {
	public static int minBlockSize = 60;
	
	public static float moveSpeed = 10;
	public static float accTime = 0.2f;
	public static float jumpHeight = 4.8f;
	public static float gravity = 60;
	
	public static float friction = 0.6f;
	
	/* Player display size */
	public static float swanHeight = minBlockSize * 2;
	public static float jupiterHeight = minBlockSize *2;
	public static float sealHeight = minBlockSize * 1.5f;
	public static float sealLevitHeight = minBlockSize * 2.3f;

	/* Player physics size */
	public static Vector2 swanPhysicsSize = new Vector2(minBlockSize * 0.875f,minBlockSize * 1.75f);
	public static Vector2 sealPhysicsSize = new Vector2(minBlockSize * 0.875f,minBlockSize * 1.25f);
	public static Vector2 jupiterPhysicsSize = new Vector2(minBlockSize * 0.875f,minBlockSize * 1.75f);
	
	public static float portraitSizeX = 100;
	public static float portraitSizeY = 100;

	public static float physicsStepSize = 1f/60f;
	
	/* Limit settings */
	
	public static int outsideLimit = 10;
	
	/* Camera settings */
	public static float camWidth = 32;
	public static float camHeight = 18;
	
	/*Jupiter Dash*/
	public static float dashSpeed = 30;
	public static int   dashLengthMS = 400;
	
}
