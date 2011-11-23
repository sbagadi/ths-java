package ths.core.loaders;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ths.core.Resource;
import ths.core.Loader;
import ths.commons.util.UrlUtils;

/**
 * UrlLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class UrlLoader extends AbstractLoader {
    
    public List<String> doList(String directory, String[] suffixes) throws IOException {
        return UrlUtils.listUrl(new URL(directory), this.suffixes);
    }
    
    protected Resource doLoad(String name, String encoding, String path) throws IOException {
        return new UrlResource(this, name, encoding, path);
    }
    
}
