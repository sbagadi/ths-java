package ths.template.support.caches;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import ths.core.Configurable;
import ths.template.Configs;
import ths.template.support.Cache;
import ths.template.util.ConfigUtils;

/**
 * LruCache. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setCache(Cache)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class LruCache extends MapCache implements Configurable<Configs> {
    
    private static final int DEFAULT_CAPACITY = 1000;
    
	private static class LruMap<K, V> extends LinkedHashMap<K, V> {

        private static final long serialVersionUID = -7831882959160110063L;
        
        private final AtomicInteger capacity;
        
        private LruMap(AtomicInteger capacity) {
            this.capacity = capacity;
        }

        @Override
	    protected boolean removeEldestEntry(Entry<K, V> eldest) {
	        return size() > capacity.get();
	    }

	};

    private final AtomicInteger capacity;
    
	public LruCache() {
	    this(DEFAULT_CAPACITY);
	}
	
	public LruCache(int capacity) {
	    this(new AtomicInteger(capacity));
	}

    public LruCache(AtomicInteger capacity) {
        super(Collections.synchronizedMap(new LruMap<Object, Object>(capacity)));
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    @Override
    public void configure(Configs config) {
        String capacity = config.getCacheCapacity();
        
        if (capacity != null && ConfigUtils.isInteger(capacity.trim())) {
            this.capacity.set(Integer.parseInt(capacity.trim()));
        }
    }

}
