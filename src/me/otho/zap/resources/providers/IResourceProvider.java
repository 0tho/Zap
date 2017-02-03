package me.otho.zap.resources.providers;

import java.io.InputStream;

public interface IResourceProvider {
	
	public InputStream getStream (Class<?> type, String fileName);

}
