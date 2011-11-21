package ths.template.support.caches;

import java.util.concurrent.ConcurrentHashMap;

public class StrongCache extends MapCache {
	
    public StrongCache(){
        super(new ConcurrentHashMap<Object, Object>());
    }
}
