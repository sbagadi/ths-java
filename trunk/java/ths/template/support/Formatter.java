package ths.template.support;

/**
 * Formatter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFormatter(Formatter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public interface Formatter<T> {
    
    /**
     * Format the value to a string.
     * 
     * @param value - variable value.
     * @return string value
     */
    String format(T value);
    
}
