package ths.template.support.caches;

import java.util.Map;

import ths.template.support.Cache;

/**
 * MapCache. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setCache(Cache)
 * @see com.googlecode.httl.support.caches.StrongCache
 * @see com.googlecode.httl.support.caches.LruCache
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class MapCache implements Cache {

    private final Map<Object, Object> map;

    public MapCache(Map<Object, Object> map){
        if (map == null) {
            throw new IllegalArgumentException("map == null");
        }
        this.map = map;
    }

    public Object get(Object key) {
        return map.get(key);
    }

    public void put(Object key, Object value) {
        map.put(key, value);
    }

    public void remove(Object key) {
        map.remove(key);
    }

}
