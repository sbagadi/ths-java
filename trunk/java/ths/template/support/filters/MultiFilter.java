package ths.template.support.filters;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ths.core.Configurable;
import ths.template.Configs;
import ths.template.support.Filter;
import ths.template.util.ClassUtils;

/**
 * MultiFilter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFilter(Filter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class MultiFilter implements Filter, Configurable<Configs> {
    
    private final List<Filter> templateFilters = new CopyOnWriteArrayList<Filter>();
    
    @Override
    @SuppressWarnings("unchecked")
    public void configure(Configs config) {
        String value = config.getFilters();
        
        if (value != null && value.trim().length() > 0) {
            String[] values = value.trim().split("[\\s\\,]+");
            Filter[] filters = new Filter[values.length];
            for (int i = 0; i < values.length; i ++) {
                filters[i] = (Filter) ClassUtils.newInstance(values[i]);
                if (filters[i] instanceof Configurable) {
                    ((Configurable<Configs>)filters[i]).configure(config);
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
