package ths.template.support.caches;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import com.googlecode.httl.Configurable;
import ths.template.support.Cache;
import ths.template.util.ConfigUtils;

public class LruCache extends MapCache implements Configurable {
    
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

    public void configure(Map<String, String> config) {
        String capacity = config.get(CACHE_CAPACITY);
        if (capacity != null && ConfigUtils.isInteger(capacity.trim())) {
            this.capacity.set(Integer.parseInt(capacity.trim()));
        }
    }

}
