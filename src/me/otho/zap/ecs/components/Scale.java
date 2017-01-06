package me.otho.zap.ecs.components;

import me.otho.zap.ecs.IComponent;

public class Scale implements IComponent{
	
	public float x = 1;
	public float y = 1;
	public float z = 1;
	
	public Scale(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Scale(){
		this(1,1,1);
	}

	public Scale clone() {
		return this;
	}
	
}
