package ths.template.support.caches;

import java.util.concurrent.ConcurrentHashMap;

import ths.template.support.Cache;

/**
 * StrongCache. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setCache(Cache)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class StrongCache extends MapCache {

    public StrongCache(){
        super(new ConcurrentHashMap<Object, Object>());
    }

}
