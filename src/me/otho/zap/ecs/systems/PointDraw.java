package me.otho.zap.ecs.systems;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import me.otho.zap.ecs.Entity;
import me.otho.zap.ecs.System;
import me.otho.zap.ecs.components.Position2D;

public class PointDraw extends System{

	public PointDraw() {
		super(Position2D.class, 10000);
	}
	
	@Override
	public void process(Entity en, double detlaTime) {
		Position2D pos = (Position2D) en.getComponent(Position2D.class);
		
		// java.lang.System.out.println(pos.getX() + " / " + pos.getY());
		
		glPushMatrix();		
		glTranslatef(pos.getX(), pos.getY(), 0);
		glLineWidth(1);
		glColor3f(1, 0, 1);
		glBegin(GL_POINTS);;
			glVertex2f(-1, 0);
			glVertex2f(1, 0);
			glVertex2f(0, -1);
			glVertex2f(0, 1);
			
			glVertex2f(-2, 0);
			glVertex2f(2, 0);
			glVertex2f(0, -2);
			glVertex2f(0, 2);
			
			glVertex2f(0, 0);
		glEnd();
		glPopMatrix();
	}
}
