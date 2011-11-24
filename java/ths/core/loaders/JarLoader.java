package ths.core.loaders;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;

import ths.core.Resource;
import ths.core.Loader;
import ths.commons.util.UrlUtils;

/**
 * JarLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class JarLoader extends AbstractLoader {
	
	private File file;
	
	@Override
	public void configure(LoaderConfiguration config) {
	    super.configure(config);
	    file = new File(this.directory);
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
