package me.otho.zap.ecs;

import java.util.ArrayList;
import java.util.HashMap;

public class Entity {

	private HashMap<Class<?>, IComponent> components;
	
	public Entity(ArrayList<IComponent> components) {
		this.components = new HashMap<Class<?>, IComponent>();
		
		for( IComponent c : components) {
			this.components.put(c.getClass(), c);
		}
	}
	
	public Entity clone() {
		ArrayList<IComponent> components = new ArrayList<IComponent>();
		
		for( IComponent c : this.components.values() ) {
			components.add(c.clone());
		}
		
		Entity ret = new Entity(components);
		
		return ret;
	}
	
	public IComponent getComponent(Class<?> clas) {
		return components.get(clas); 
	}
	
	public ArrayList<Class<?>> getComponentTypes() {
		return new ArrayList<Class<?>>(components.keySet());
	}
	
}
