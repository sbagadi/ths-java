package ths.template.support;

/**
 * Filter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFilter(Filter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public interface Filter {
    
    /**
     * Filter the variable value.
     * 
     * @param value - Original variable value
     * @return Filtered variable value
     */
    String filter(String value);
    
}
