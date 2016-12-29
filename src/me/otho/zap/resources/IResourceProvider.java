package me.otho.zap.resources;

import java.io.InputStream;

public interface IResourceProvider {
	
	public InputStream getStream (Class<?> type, String fileName);

}
