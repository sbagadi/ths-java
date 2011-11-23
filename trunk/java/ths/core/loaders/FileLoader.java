package ths.core.loaders;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ths.core.Resource;
import ths.core.Loader;
import ths.commons.util.UrlUtils;

/**
 * FileLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class FileLoader extends AbstractLoader {

    public List<String> doList(String directory, String[] suffixes) throws IOException {
        File file = new File(directory);
        return UrlUtils.listFile(file, suffixes);
    }
    
    protected Resource doLoad(String name, String encoding, String path) throws IOException {
        return new FileResource(this, name, encoding, path);
    }
    
}
