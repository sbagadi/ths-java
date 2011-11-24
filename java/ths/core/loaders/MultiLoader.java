package ths.core.loaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ths.core.Resource;
import ths.core.Loader;
import ths.commons.util.ClassUtils;

/**
 * MultiLoader. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setLoader(Loader)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class MultiLoader implements Loader {

    private final List<Loader> templateLoaders = new CopyOnWriteArrayList<Loader>();
    
    public void init(String inputEncoding, String directory, String suffix, String loaderNames) {
	
        if (loaderNames != null && loaderNames.trim().length() > 0) {
            String[] values = loaderNames.trim().split("[\\s\\,]+");
            Loader[] loaders = new Loader[values.length];
            for (int i = 0; i < values.length; i ++) {
                loaders[i] = (Loader)ClassUtils.newInstance(values[i]);
            }
            add(loaders);
        }
	}

    public void add(Loader... loaders) {
        if (loaders != null && loaders.length > 0) {
            for (Loader loader : loaders) {
                if (loader != null && ! templateLoaders.contains(loader)) {
                    templateLoaders.add(loader);
                }
            }
        }
    }
    
    public void remove(Loader... loaders) {
        if (loaders != null && loaders.length > 0) {
            for (Loader loader : loaders) {
                if (loader != null) {
                    templateLoaders.remove(loader);
                }
            }
        }
    }
    
    public void clear() {
        templateLoaders.clear();
    }

    public Resource load(String name, String encoding) throws IOException {
        for (Loader loader : templateLoaders) {
            try {
                return loader.load(name, encoding);
            } catch (Exception e) {
            }
        }
        throw new FileNotFoundException("No such template file: " + name);
    }

    public List<String> list() {
        List<String> all = new ArrayList<String>();
        for (Loader loader : templateLoaders) {
            try {
                List<String> list = loader.list();
                if (list != null && list.size() > 0) {
                    all.addAll(list);
                }
            } catch (Exception e) {
            }
        }
        return all;
    }

}
