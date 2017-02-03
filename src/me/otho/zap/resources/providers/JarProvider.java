package me.otho.zap.resources.providers;

import java.io.InputStream;

public class JarProvider implements IResourceProvider {

	@Override
	public InputStream getStream(Class<?> type, String fileName) {
		return	System.class.getResourceAsStream(fileName);
	}

}
