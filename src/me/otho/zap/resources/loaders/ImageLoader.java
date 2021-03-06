package me.otho.zap.resources.loaders;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader implements ITypeLoader{

	@Override
	public Object load(InputStream resourceStream) throws IOException {
		return ImageIO.read(resourceStream);
	}

}
