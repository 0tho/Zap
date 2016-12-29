package me.otho.zap.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ResourceLoader {
	
	private HashMap<Class<?>, ITypeLoader> loaders;
	private IResourceProvider provider;
	
	public ResourceLoader (IResourceProvider streamProvider) {
		provider = streamProvider;
		loaders = new HashMap<Class<?>, ITypeLoader>();
	}
	
	public void registerLoaderType(Class<?> type, ITypeLoader newLoaderType) {
		loaders.put(type, newLoaderType);
	}
	
	public Object loadAsset(Class<?> type, String name) {
		ITypeLoader loader = loaders.get(type);
		InputStream stream = provider.getStream(type, name);
		
		try {
			return loader.load(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
