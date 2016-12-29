package me.otho.zap.resources;

import java.io.IOException;
import java.io.InputStream;

public interface ITypeLoader {

	Object load (InputStream resourceStream) throws IOException;
	
}
