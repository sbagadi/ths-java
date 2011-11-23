package ths.template.support.filters;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import ths.template.Constants;
import ths.template.support.Filter;
import ths.template.util.ClassUtils;

/**
 * MultiTextFilter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setTextFilter(Filter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class MultiTextFilter implements Filter {
    
    private final List<Filter> templateFilters = new CopyOnWriteArrayList<Filter>();
 
    public void configure(Map<String, String> config) {
        String value = config.get(Constants.TEXT_FILTERS);
        if (value != null && value.trim().length() > 0) {
            String[] values = value.trim().split("[\\s\\,]+");
            Filter[] filters = new Filter[values.length];
            for (int i = 0; i < values.length; i ++) {
                filters[i] = (Filter) ClassUtils.newInstance(values[i]);
                if (filters[i] instanceof Configurable) {
                    ((Configurable)filters[i]).configure(config);
                }
            }
            add(filters);
        }
    }
    
    public void add(Filter... filters) {
        if (filters != null && filters.length > 0) {
            for (Filter filter : filters) {
                if (filter != null && ! templateFilters.contains(filter)) {
                    templateFilters.add(filter);
                }
            }
        }
    }
    
    public void remove(Filter... filters) {
        if (filters != null && filters.length > 0) {
            for (Filter filter : filters) {
                if (filter != null) {
                    templateFilters.remove(filter);
                }
            }
        }
    }
    
    public void clear() {
        templateFilters.clear();
    }

    public String filter(String value) {
        if (templateFilters.size() > 0) {
            for (Filter filter : templateFilters) {
                value = filter.filter(value);
            }
        }
        return value;
    }

}
