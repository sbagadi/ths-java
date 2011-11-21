package ths.template.support;

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
