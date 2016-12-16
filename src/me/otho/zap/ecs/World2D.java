package me.otho.zap.ecs;

import me.otho.zap.ecs.components.Position2D;

public class World2D extends World{
	
	private int width;
	private int height;
	
	public World2D(int width, int height, Long seed) {
		super(seed);
		this.width = width;
		this.height = height;
	}
	
	public Position2D getRandomPos() {
		float x = rng.nextFloat() * width;
		float y = rng.nextFloat() * height;
		return new Position2D(x, y);
	}
	
	public boolean doesWorldContains(Position2D p) {
		if ( p.getX() >= 0 && p.getX() < width && p.getY() >= 0  && p.getY() < height ) {
			return true;
		}
		
		return false;
	}

}
