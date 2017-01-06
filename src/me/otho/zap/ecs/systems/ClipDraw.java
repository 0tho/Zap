package me.otho.zap.ecs.systems;

import static org.lwjgl.opengl.GL11.*;



import me.otho.zap.ecs.Entity;
import me.otho.zap.ecs.System;
import me.otho.zap.ecs.components.Clip;
import me.otho.zap.ecs.components.ClipMap;
import me.otho.zap.ecs.components.Position2D;
import me.otho.zap.ecs.components.Scale;

public class ClipDraw extends System{

	public ClipDraw() {
		super(ClipMap.class, 10000);
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
		ClipMap clipMap = (ClipMap) en.getComponent(ClipMap.class);
		
		Scale scale = (Scale) en.getComponent(Scale.class);
		
		Clip clip = clipMap.getClip();
		
		int width = clip.getFrameWidth();
		int height = clip.getFrameHeight();
	
		glPushMatrix();		
		glTranslatef(pos.getX(), pos.getY(), 0);
		glScalef(scale.x, scale.y, scale.z);
		glTranslatef(-pos.getX(), -pos.getY(), 0);
		glTranslatef(pos.getX() - width/2, pos.getY() - height/2, 0);
		glLineWidth(3);
		glColor3f(1, 1, 1);
		
		clipMap.bind();
		
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);
			
			glTexCoord2f(1, 0);
			glVertex2f(width, 0);
			
			glTexCoord2f(1, 1);
			glVertex2f(width, height);
			
			glTexCoord2f(0, 1);
			glVertex2f(0, height);
		glEnd();
		
		glPopMatrix();
	}

}
