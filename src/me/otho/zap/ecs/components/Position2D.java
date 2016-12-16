package me.otho.zap.ecs.components;

import me.otho.zap.ecs.Component;

public class Position2D extends Component{
	
	private float x;
	private float y;

	public Position2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Position2D point) {
		set(point.getX(), point.getY());
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Position2D clone() {
		return new Position2D(x, y);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float distanceTo(Position2D point) {
		return distanceTo(point.getX(), point.getY());
	}
	
	public float distanceTo(float x, float y) {
		return Position2D.distance(this, new Position2D(x, y));
	}
	
	static public float distance(Position2D a, Position2D b) {
		float dx = a.getX() - b.getX();
		float dy = a.getY() - b.getY();
		
		return (float) Math.sqrt(dx*dx + dy*dy);
	}
	
}
