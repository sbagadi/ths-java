package ths.template.support.filters;

import ths.template.support.Filter;
import ths.template.util.StringUtils;

/**
 * EscapeHtmlFilter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFilter(Filter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class EscapeHtmlFilter implements Filter {

    public String filter(String value) {
        return StringUtils.escapeHtml(value);
    }

}
