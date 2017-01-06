package me.otho.zap.ecs.components;


import java.awt.image.BufferedImage;

import me.otho.zap.ecs.IComponent;
import me.otho.zap.util.Timer;

public class Clip implements IComponent {

	Sprite[] sprites;
	
	private int pointer;
	
	private double elapsedTime;
	private double currentTime;
	private double lastTime;
	private double fps;
	
	private int frameWidth;
	private int frameHeight;
	
	public Clip(BufferedImage sprite, int frameWidth, int frameHeight, int[] framesIds, int fps) {
		this.pointer = 0;
		this.elapsedTime = 0;
		this.currentTime = 0;
		this.lastTime = Timer.getTime();
		this.fps = 1.0/(double)fps;
		
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		this.sprites = new Sprite[framesIds.length];
		
		for(int i = 0; i < framesIds.length; i++ ) {
			this.sprites[i] = new Sprite(sprite, framesIds[i], frameWidth, frameHeight);
		}
		
	}

	public void reset() {
		this.pointer = 0;
		this.elapsedTime = 0;
		this.currentTime = 0;
		this.lastTime = Timer.getTime();
	}
	public void bind() {
		this.currentTime = Timer.getTime();
		this.elapsedTime += currentTime - lastTime;
		
		if ( elapsedTime >= fps ) {
			elapsedTime -= fps;
			pointer++;
		}
		
		if(pointer >= sprites.length ) pointer = 0;
		
		this.lastTime = currentTime;
		
		sprites[pointer].bind();
	}
	
	public Clip clone() {
		return this;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
}
