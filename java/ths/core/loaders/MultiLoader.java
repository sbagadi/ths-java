package ths.core.loaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ths.core.Resource;
import ths.core.Loader;
import ths.template.util.ClassUtils;

public class MultiLoader implements Loader {

    private final List<Loader> resourceLoaders = new CopyOnWriteArrayList<Loader>();
    
	public void createLoaders(String loaderNames, LoaderConfigurable configurable) {
	    String[] values = loaderNames.trim().split("[\\s\\,]+");
	    Loader[] loaders = new Loader[values.length];
	    
	    for (int i = 0; i < values.length; i ++) {
	        loaders[i] = (Loader)ClassUtils.newInstance(values[i]);
	        ((AbstractLoader)loaders[i]).setLoaderConfigurable(configurable);
	    }
	    
	    add(loaders);
	}

    public void add(Loader... loaders) {
        if (loaders != null && loaders.length > 0) {
            for (Loader loader : loaders) {
                if (loader != null && ! resourceLoaders.contains(loader)) {
                    resourceLoaders.add(loader);
                }
            }
        }
    }
    
    public void remove(Loader... loaders) {
        if (loaders != null && loaders.length > 0) {
            for (Loader loader : loaders) {
                if (loader != null) {
                    resourceLoaders.remove(loader);
                }
            }
        }
    }
    
    public void clear() {
        resourceLoaders.clear();
    }

    public Resource load(String name, String encoding) throws IOException {
        for (Loader loader : resourceLoaders) {
            try {
                return loader.load(name, encoding);
            } catch (Exception e) {
            }
        }
        throw new FileNotFoundException("No such template file: " + name);
    }

    public List<String> list() {
        List<String> all = new ArrayList<String>();
        for (Loader loader : resourceLoaders) {
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
