package ths.template.support.caches;

import ths.core.Configurable;
import ths.template.TemplateConfiguration;
import ths.template.support.Cache;

/**
 * AdaptiveCache. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setCache(Cache)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class AdaptiveCache implements Cache, Configurable<TemplateConfiguration> {
    
    private Cache cache;
    
	@Override
	@SuppressWarnings("unchecked")
    public void configure(TemplateConfiguration config) {
        String capacity = config.getCacheCapacity();
        
        if (capacity != null && capacity.trim().length() > 0 && Integer.parseInt(capacity.trim()) > 0) {
            cache = new LruCache(Integer.parseInt(capacity.trim()));
        } else {
            cache = new StrongCache();
        }
        if (cache instanceof Configurable) {
            ((Configurable<TemplateConfiguration>)cache).configure(config);
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
