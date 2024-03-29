package ths.core.loaders;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import ths.core.Loader;

/**
 * StringResource. (SPI, Prototype, ThreadSafe)
 * 
 * @see com.googlecode.httl.support.loaders.StringLoader#load(String, String)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class StringResource extends AbstractResource {
    
    private static final long serialVersionUID = 1L;
    
    private final String source;
    
    public StringResource(Loader loader, String name, String encoding, String source) {
        super(loader, name, encoding);
        this.source = source;
    }
    
    public Reader getReader() throws IOException {
        return new StringReader(source);
    }
    
}
