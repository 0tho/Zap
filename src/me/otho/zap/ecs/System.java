package me.otho.zap.ecs;

import java.util.Comparator;

public class System implements Comparator<System>, Comparable<System>{
	
	private Integer priority = 0;
	private Class<?> componentType;
	
	public System(Class<?> componentType, int priority) {
		this.priority = priority;
		this.componentType = componentType;
	}
	
	public void process(Entity en, double detlaTime) {
		
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public Class<?> getComponentType() {
		return componentType;
	}

	@Override
	public int compareTo(System sys) {
		return priority.compareTo(sys.priority);
	}

	@Override
	public int compare(System sys0, System sys1) {
		return sys0.priority - sys1.priority;
	}
}
