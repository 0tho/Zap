package me.otho.zap.ecs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public abstract class World {
	
	protected Random rng;
	
	private HashMap<Class<?>, System> classSystemMap;
	private HashMap<Class<?>, ArrayList<Entity>> classEntityMap;
	private ArrayList<System> orderedSystems;

	public World(Long seed) {
		if ( seed == null ) {
			rng = new Random();
		} else {
			rng = new Random(seed);			
		}
		
		classSystemMap = new HashMap<Class<?>, System>();
		classEntityMap = new HashMap<Class<?>, ArrayList<Entity>>();
	}
	
	public Random getRng() {
		return rng;
	}
	
	public void addEntity(Entity en) {
		ArrayList<Class<?>> componentTypes = en.getComponentTypes();
		
		for( Class<?> clas : componentTypes ) {
			java.lang.System.out.println(clas.getName());
			if ( !classEntityMap.containsKey(clas)) {
				classEntityMap.put(clas, new ArrayList<Entity>());
			}
			classEntityMap.get(clas).add(en);
		}
	}
	
	
	public void installComponentSystem(Class<?> componentType, System sys) {
		if( classSystemMap.containsKey(componentType) ) {
			throw new Error("Component Type: " + componentType.getName() + " already installed");
		}
		
		classSystemMap.put(componentType, sys);
		
		orderedSystems = new ArrayList<System>(classSystemMap.values());
		
		Collections.sort(orderedSystems);
		for(System s: orderedSystems) 
			java.lang.System.out.println(s.getClass().getName());
	}
	
	public void update(double deltaTime) {
		for ( System sys : orderedSystems ) {
			Class<?> componentType = sys.getComponentType();
			sys.setup();
			//java.lang.System.out.println(componentType.getName());
			ArrayList<Entity> entities = classEntityMap.get(componentType);
			if( entities != null ) {
				for( Entity en : classEntityMap.get(componentType)) {
					sys.process(en, deltaTime);
				}
			}
			sys.finish();
		}
	}
	
	public void clearEntities() {
		classEntityMap = new HashMap<Class<?>, ArrayList<Entity>>();
	}
}
