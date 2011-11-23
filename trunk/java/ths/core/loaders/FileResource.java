package ths.core.loaders;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import ths.core.Loader;

/**
 * FileResource. (SPI, Prototype, ThreadSafe)
 * 
 * @see com.googlecode.httl.support.loaders.FileLoader#load(String, String)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class FileResource extends AbstractResource {
    
    private static final long serialVersionUID = 1L;
    
    private final File file;
    
    public FileResource(Loader loader, String name, String encoding, String path) {
        super(loader, name, encoding);
        this.file = new File(path);
    }

    public long getLastModified() {
        return file.lastModified();
    }

    public long getLength() {
        return file.length();
    }

    public Reader getReader() throws IOException {
        return new FileReader(file);
    }
    
}
