package ths.template.support;

/**
 * Cache. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setCache(Cache)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public interface Cache {
    
    /**
     * Get template cache.
     * 
     * @param key - Template key.
     * @return Template instance.
     */
    Object get(Object key);
    
    /**
     * Put template cache.
     * 
     * @param key - Template key.
     * @param value - Template instance.
     */
    void put(Object key, Object value);
    
    /**
     * Remove template cache.
     * 
     * @param key - Template key.
     */
    void remove(Object key);
    
}
