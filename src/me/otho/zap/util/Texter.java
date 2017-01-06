package me.otho.zap.util;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import me.otho.zap.ecs.components.Position2D;
import me.otho.zap.ecs.components.Sprite;

public class Texter {
	
	private HashMap<String, Sprite> characters;

	private int frameWidth;
	private int frameHeight;
	private Sprite[] sprites;

	public Texter(BufferedImage sprite, int frameWidth, int frameHeight) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		this.sprites = new Sprite[26*2 + 12];
		
		for(int i = 0; i < sprites.length; i++ ) {
			this.sprites[i] = new Sprite(sprite, i, frameWidth, frameHeight);
		}
		
		characters = new HashMap<String, Sprite>();
		characters.put("a", sprites[0]);
		characters.put("b", sprites[1]);
		characters.put("c", sprites[2]);
		characters.put("d", sprites[3]);
		characters.put("e", sprites[4]);
		characters.put("f", sprites[5]);
		characters.put("g", sprites[6]);
		characters.put("h", sprites[7]);
		characters.put("i", sprites[8]);
		characters.put("j", sprites[9]);
		characters.put("k", sprites[10]);
		characters.put("l", sprites[11]);
		characters.put("m", sprites[12]);
		characters.put("n", sprites[13]);
		characters.put("o", sprites[14]);
		characters.put("p", sprites[15]);
		characters.put("q", sprites[16]);
		characters.put("r", sprites[17]);
		characters.put("s", sprites[18]);
		characters.put("t", sprites[19]);
		characters.put("u", sprites[20]);
		characters.put("v", sprites[21]);
		characters.put("w", sprites[22]);
		characters.put("x", sprites[23]);
		characters.put("y", sprites[24]);
		characters.put("z", sprites[25]);
		characters.put("A", sprites[26]);
		characters.put("B", sprites[27]);
		characters.put("C", sprites[28]);
		characters.put("D", sprites[29]);
		characters.put("E", sprites[30]);
		characters.put("F", sprites[31]);
		characters.put("G", sprites[32]);
		characters.put("H", sprites[33]);
		characters.put("I", sprites[34]);
		characters.put("J", sprites[35]);
		characters.put("K", sprites[36]);
		characters.put("L", sprites[37]);
		characters.put("M", sprites[38]);
		characters.put("N", sprites[39]);
		characters.put("O", sprites[40]);
		characters.put("P", sprites[41]);
		characters.put("Q", sprites[42]);
		characters.put("R", sprites[43]);
		characters.put("S", sprites[44]);
		characters.put("T", sprites[45]);
		characters.put("U", sprites[46]);
		characters.put("V", sprites[47]);
		characters.put("W", sprites[48]);
		characters.put("X", sprites[49]);
		characters.put("Y", sprites[50]);
		characters.put("Z", sprites[51]);
		characters.put("0", sprites[52]);
		characters.put("1", sprites[53]);
		characters.put("2", sprites[54]);
		characters.put("3", sprites[55]);
		characters.put("4", sprites[56]);
		characters.put("5", sprites[57]);
		characters.put("6", sprites[58]);
		characters.put("7", sprites[59]);
		characters.put("8", sprites[60]);
		characters.put("9", sprites[61]);
		characters.put(" ", sprites[62]);
		characters.put(":", sprites[63]);
	}

	public void drawString(String text, int x, int y) {
		glEnable(GL_BLEND);
		for(int i = 0; i < text.length(); i++ ) {
			Position2D pos = new Position2D(x + frameWidth * i, y);
			//System.out.println(text.charAt(i));
			Sprite sprite = characters.get(Character.toString(text.charAt(i)));
		
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
		glDisable(GL_BLEND);		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
