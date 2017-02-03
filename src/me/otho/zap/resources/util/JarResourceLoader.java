package me.otho.zap.resources.util;

import java.awt.image.BufferedImage;

import me.otho.zap.resources.ResourceLoader;
import me.otho.zap.resources.loaders.ImageLoader;
import me.otho.zap.resources.loaders.ShaderLoader;
import me.otho.zap.resources.providers.JarProvider;

public class JarResourceLoader extends ResourceLoader{

	public JarResourceLoader() {
		super(new JarProvider());
		
		registerLoaderType(BufferedImage.class, new ImageLoader());
		registerLoaderType(String.class, new ShaderLoader());
	}
	
	public String loadTextFile(String path) {
		return (String) loadAsset(String.class, path);
	}
	
	public BufferedImage loadImage(String path) {
		return (BufferedImage) loadAsset(BufferedImage.class, path);
	}

}
