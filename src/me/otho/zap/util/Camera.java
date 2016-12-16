package me.otho.zap.util;

import me.otho.zap.ecs.components.Position2D;

public class Camera {
	
	private Position2D pos;
	private int width;
	private int height;

	public Camera(Position2D pos, int width, int height) {
		this.pos = pos;
		this.width = width;
		this.height = height;
	}

	public Position2D getPos() {
		return pos;
	}

	public void setPos(Position2D pos) {
		this.pos = pos;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
