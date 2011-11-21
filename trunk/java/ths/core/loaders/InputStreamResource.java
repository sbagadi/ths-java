package ths.core.loaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ths.core.Loader;

/**
 * InputStreamResource. (SPI, Prototype, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class InputStreamResource extends AbstractResource {

    private static final long serialVersionUID = -5150738383353330217L;

    public InputStreamResource(Loader loader, String name, String encoding){
        super(loader, name, encoding);
    }

    public Reader getReader() throws IOException {
        InputStream in = getInputStream();
        if (in == null) {
            throw new FileNotFoundException("Not found template " + getName());
        }
        String encoding = getEncoding();
        return encoding == null || encoding.length() == 0 
            ? new InputStreamReader(in) : new InputStreamReader(in, encoding);
    }
    
    protected abstract InputStream getInputStream() throws IOException;

}
