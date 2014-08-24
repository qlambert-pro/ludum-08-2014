package com.ludum.rendering;

public enum TextureType {
	PlayerJump(0, 0, 4, "player.png"),
	PlayerIdle(4, 0, 4, "player.png"),
	PlayerRun(8, 0, 4, "player.png"),
	PlayerPortraits(0, 0, 2, "playerPortraits.png");

	private int rowBeginIndex;
	private int colBeginIndex;
	private int nbTexture;
	private String fileName;

	private TextureType(int rowBeginIndex, int colBeginIndex, int nbFrame,
			String fileName) {
		this.rowBeginIndex = rowBeginIndex;
		this.colBeginIndex = colBeginIndex;
		nbTexture = nbFrame;
		this.fileName = fileName;
	}

	public int getRow() {
		return rowBeginIndex;
	}

	public int getCol() {
		return colBeginIndex;
	}

	public int nbTexture() {
		return nbTexture;
	}

	public String getFileName() {
		return fileName;
	}

}
