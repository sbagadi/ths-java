package ths.template.support.caches;

import java.util.Map;

import ths.template.support.Cache;

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
