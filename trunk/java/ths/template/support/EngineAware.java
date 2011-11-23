package ths.template.support;

import ths.template.Engine;

/**
 * EngineAware. (SPI, ThreadLocal, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public interface EngineAware {
    
    /**
     * Set the engine.
     * 
     * @param engine
     */
    void setEngine(Engine engine);
    
}
