package ths.core.loaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ths.core.Resource;
import ths.core.Loader;

/**
 * StringLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * @see com.googlecode.httl.Engine#addTemplate(String, String)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class StringLoader extends AbstractLoader {
    
    private final Map<String, String> templates = new ConcurrentHashMap<String, String>();
    
    public boolean has(String name) {
        return templates.containsKey(name);
    }
    
    public void add(String name, String source) {
        templates.put(name, source);
    }

    public void remove(String name) {
        templates.remove(name);
    }

    public void clear() {
        templates.clear();
    }

    public List<String> list() throws IOException {
        return new ArrayList<String>(templates.keySet());
    }

    protected List<String> doList(String directory, String[] suffixes) throws IOException {
        return new ArrayList<String>(templates.keySet());
    }
    
    protected Resource doLoad(String name, String encoding, String path) throws IOException {
        String source = templates.get(path);
        if (source == null) {
            throw new FileNotFoundException("Not found template " + name);
        }
        return new StringResource(this, name, encoding, source);
    }
    
}
