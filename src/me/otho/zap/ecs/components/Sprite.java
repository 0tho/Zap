package me.otho.zap.ecs.components;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import me.otho.zap.ecs.IComponent;

public class Sprite implements IComponent{
	
	private int id;
	private int width;
	private int height;
	
	public Sprite(BufferedImage sprite) {
		this(sprite, 0, 0, sprite.getWidth(), sprite.getHeight());		
	}
	
	public Sprite(BufferedImage spriteSheet, int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		
		int[] pixels_raw = new int[width * height * 4];
		pixels_raw = spriteSheet.getRGB(x, y, width, height, null, 0, width);
				
		ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
		
		for ( int i = 0; i< height; i++ ) {
			for ( int j = 0; j< width; j++ ) {
				int pixel = pixels_raw[i*width + j];
				
				pixels.put((byte) ((pixel >> 16) & 0xFF));
				pixels.put((byte) ((pixel >> 8) & 0xFF));
				pixels.put((byte) ((pixel >> 0) & 0xFF));
				pixels.put((byte) ((pixel >> 24) & 0xFF));
			}
		}		
		
		pixels.flip();
		
		id = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public Sprite(Sprite sprite) {
		this.id = sprite.getId();
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
	}
	
	public Sprite(int id, int width, int height) {
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	public Sprite(BufferedImage spriteSheet, int frame, int width, int height) {
		this(spriteSheet,
				(frame % (spriteSheet.getWidth() / width))*width, 
				((int)(frame / (spriteSheet.getWidth() / width)))*height, 
				width, 
				height);
	}

	public Sprite clone() {
		return new Sprite(this);
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public int getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
