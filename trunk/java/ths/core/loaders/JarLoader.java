package ths.core.loaders;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

import ths.core.Resource;
import ths.commons.util.UrlUtils;

public class JarLoader extends AbstractLoader {
	
	private File file;
	
	public void configure(Map<String, String> config) {
	    super.configure(config);
	    file = new File(getDirectory());
	}
	
	protected List<String> doList(String directory, String[] suffixes) throws IOException {
        JarFile jarFile = new JarFile(file);
        try {
            return UrlUtils.listJar(jarFile, suffixes);
        } finally {
            jarFile.close();
        }
    }
	
	public Resource doLoad(String name, String encoding, String path) throws IOException {
		return new JarResource(this, name, encoding, file);
	}

}
