package me.otho.zap.resources.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderLoader implements ITypeLoader{

	@Override
	public Object load(InputStream resourceStream) throws IOException {
		
		StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceStream, "UTF-8"));
        String line = bufferedReader.readLine();
        while(line != null){
            inputStringBuilder.append(line);inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        return inputStringBuilder.toString();
	}

}
