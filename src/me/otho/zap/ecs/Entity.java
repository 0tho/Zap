package me.otho.zap.ecs;

import java.util.ArrayList;
import java.util.HashMap;

public class Entity {

	private HashMap<Class<?>, Component> components;
	
	public Entity(ArrayList<Component> components) {
		this.components = new HashMap<Class<?>, Component>();
		
		for( Component c : components) {
			this.components.put(c.getClass(), c);
		}
	}
	
	public Entity clone() {
		ArrayList<Component> components = new ArrayList<Component>();
		
		for( Component c : this.components.values() ) {
			components.add(c.clone());
		}
		
		Entity ret = new Entity(components);
		
		return ret;
	}
	
	public Component getComponent(Class<?> clas) {
		return components.get(clas); 
	}
	
	public ArrayList<Class<?>> getComponentTypes() {
		return new ArrayList<Class<?>>(components.keySet());
	}
	
}
