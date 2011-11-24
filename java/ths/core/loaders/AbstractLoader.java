package ths.core.loaders;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import ths.core.Resource;
import ths.core.Loader;

/**
 * AbstractLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class AbstractLoader implements Loader {
	protected String encoding;

	protected String directory;

	protected String[] suffixes;

    public void init(String inputEncoding, String directory, String suffix) {   	
        Charset.forName(inputEncoding);
        this.encoding = inputEncoding;
        
        if (directory.endsWith("/") || directory.endsWith("\\")) {
            directory = directory.substring(0, directory.length() - 1);
        }
        this.directory = directory.trim();
        this.suffixes = suffix.trim().split("\\s*\\,\\*");
    }
    
    public List<String> list() throws IOException {
        String directory = this.directory;
        if (directory == null || directory.length() == 0) {
            return new ArrayList<String>(0);
        }
        
        String[] suffixes = this.suffixes;
        if (suffixes == null || suffixes.length == 0) {
            return new ArrayList<String>(0);
        }
        List<String> list = doList(directory, suffixes);
        if (list == null) {
            list = new ArrayList<String>(0);
        }
        return list;
    }
    
    protected abstract List<String> doList(String directory, String[] suffixes) throws IOException;
    
    public Resource load(String name, String encoding) throws IOException {
        if (encoding == null || encoding.length() == 0) {
            encoding = this.encoding;
        }
        String dir = directory;
        return doLoad(name, encoding, dir == null ? name : dir + name);
    }
    
    protected abstract Resource doLoad(String name, String encoding, String path) throws IOException;
    
}
