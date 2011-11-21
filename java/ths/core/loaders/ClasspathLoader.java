package ths.core.loaders;

import java.io.IOException;
import java.util.List;

import ths.core.Resource;
import ths.commons.util.UrlUtils;

public class ClasspathLoader extends AbstractLoader {
    
    public List<String> doList(String directory, String[] suffixes) throws IOException {
        return UrlUtils.listUrl(Thread.currentThread().getContextClassLoader().getResource(directory), suffixes);
    }
    
    protected Resource doLoad(String name, String encoding, String path) throws IOException {
		return new ClasspathResource(this, name, encoding, path);
	}
	
}
