package ths.template.support.filters;

import ths.template.support.Filter;

/**
 * ClearBlankFilter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setTextFilter(Filter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ClearBlankFilter implements Filter {
    
    public String filter(String text) {
        if (text == null)
            return null;
        return text.replaceAll("\\s+", "");
    }
    
}
