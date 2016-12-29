package me.otho.zap.ecs.systems;

import static org.lwjgl.opengl.GL11.*;
import me.otho.zap.ecs.Entity;
import me.otho.zap.ecs.System;
import me.otho.zap.ecs.components.Position2D;
import me.otho.zap.ecs.components.Sprite;

public class SpriteDraw extends System{

	public SpriteDraw() {
		super(Sprite.class, 9999);
	}
	
	@Override
	public void setup() {
		glEnable(GL_BLEND);
	}
	
	@Override
	public void finish() {
		glDisable(GL_BLEND);		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	@Override
	public void process(Entity en, double detlaTime) {
		Position2D pos = (Position2D) en.getComponent(Position2D.class);
		Sprite sprite = (Sprite) en.getComponent(Sprite.class);
	
		glPushMatrix();		
		glTranslatef(pos.getX() - sprite.getWidth()/2, pos.getY() - sprite.getHeight()/2, 0);
		glLineWidth(3);
		glColor3f(1, 1, 1);
		
		sprite.bind();
		
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			
			glTexCoord2f(1, 0);
			glVertex2f(sprite.getWidth(), 0);
			
			glTexCoord2f(1, 1);
			glVertex2f(sprite.getWidth(), sprite.getHeight());
			
			glTexCoord2f(0, 1);
			glVertex2f(0, sprite.getHeight());
		glEnd();
		
		glPopMatrix();
	}

}
