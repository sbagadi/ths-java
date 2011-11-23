package ths.template.support.caches;

import java.util.Map;

import ths.template.support.Cache;

/**
 * AdaptiveCache. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setCache(Cache)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class AdaptiveCache implements Cache {
    
    private Cache cache;
    
    public void configure(Map<String, String> config) {
        String capacity = config.get(CACHE_CAPACITY);
        if (capacity != null && capacity.trim().length() > 0 && Integer.parseInt(capacity.trim()) > 0) {
            cache = new LruCache(Integer.parseInt(capacity.trim()));
        } else {
            cache = new StrongCache();
        }
        if (cache instanceof Configurable) {
            ((Configurable)cache).configure(config);
        }
    }

    public Object get(Object key) {
        return cache.get(key);
    }

    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    public void remove(Object key) {
        cache.remove(key);
    }

}
