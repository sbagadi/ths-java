package ths.core.loaders;

import java.io.IOException;
import java.util.List;

import ths.core.Resource;
import ths.core.Loader;
import ths.commons.util.UrlUtils;

/**
 * ClasspathLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ClasspathLoader extends AbstractLoader {
    
    public List<String> doList(String directory, String[] suffixes) throws IOException {
        return UrlUtils.listUrl(Thread.currentThread().getContextClassLoader().getResource(directory), suffixes);
    }
    
    protected Resource doLoad(String name, String encoding, String path) throws IOException {
		return new ClasspathResource(this, name, encoding, path);
	}
	
}
