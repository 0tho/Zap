package me.otho.zap.resources.providers;

import java.io.InputStream;

import me.otho.zap.resources.IResourceProvider;

public class JarProvider implements IResourceProvider {

	@Override
	public InputStream getStream(Class<?> type, String fileName) {
		return	System.class.getResourceAsStream(fileName);
	}

}
