package com.ludum.configuration;

public class ConfigManager {
	public static int minBlockSize = 32;
	
	public static float moveSpeed = 10;
	public static float accTime = 0.1f;
	public static float jumpHeight = 5f;
	public static float gravity = 120;
	
	public static float friction = 0.5f;
	
	public static float playerSizeX = 64;
	public static float playerSizeY = 64;
	public static float playerPhysSizeX = 64;
	public static float playerPhysSizeY = 64;
	
	public static float portraitSizeX = 32;
	public static float portraitSizeY = 32;

	public static float physicsStepSize = 1f/60f;
	
	/* Limit settings */
	
	public static int outsideLimit = 10;
	
	/* Camera settings */
	public static float camWidth = 32;
	public static float camHeight = 18;
	
	/*Jupiter Dash*/
	public static float dashSpeed = 100;
	public static int   dashLengthMS = 200;
	public static float bumpX = 500;
	public static float bumpY = 200;
	
}
