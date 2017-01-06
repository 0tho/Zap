package me.otho.zap.ecs.components;

import java.util.HashMap;

import me.otho.zap.ecs.IComponent;

public class ClipMap implements IComponent {

	private HashMap<String, Clip> clipMap;
	private Clip currentClip;
	private String current = "";
	
	public ClipMap () {
		clipMap = new HashMap<String, Clip>();
	}
	
	public void addClip(String name, Clip clip) {
		clipMap.put(name, clip);
	}
	
	public void setClip(String name) {
		if ( !current.equals(name) ) {
			currentClip = clipMap.get(name);
			currentClip.reset();
			current = name;
		}
	}
	
	public Clip getClip() {
		return currentClip;
	}
	
	public void bind() {
		currentClip.bind();
	}
	
	public ClipMap clone() {
		return this;
	}
}
