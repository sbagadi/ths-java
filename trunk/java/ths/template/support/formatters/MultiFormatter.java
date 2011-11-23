package ths.template.support.formatters;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ths.template.Constants;
import ths.template.support.Formatter;
import ths.template.util.ClassUtils;
import ths.template.util.StringUtils;

/**
 * MultiFormatter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFormatter(Formatter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class MultiFormatter implements Formatter<Object> {
    
    private final Map<Class<?>, Formatter<?>> templateFormatters = new ConcurrentHashMap<Class<?>, Formatter<?>>();
    
    public void configure(Map<String, String> config) {
        String value = config.get(Constants.FORMATTERS);
        if (value != null && value.trim().length() > 0) {
            String[] values = value.trim().split("[\\s\\,]+");
            Formatter<?>[] formatters = new Formatter<?>[values.length];
            for (int i = 0; i < values.length; i ++) {
                formatters[i] = (Formatter<?>) ClassUtils.newInstance(values[i]);
                if (formatters[i] instanceof Configurable) {
                    ((Configurable)formatters[i]).configure(config);
                }
            }
            add(formatters);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> Formatter<T> get(Class<T> type) {
        return (Formatter)templateFormatters.get((Class)type);
    }
    
    public void add(Formatter<?>... formatters) {
        if (formatters != null && formatters.length > 0) {
            for (Formatter<?> formatter : formatters) {
                if (formatter != null) {
                    Class<?> type = ClassUtils.getGenericClass(formatter.getClass());
                    if (type != null) {
                        templateFormatters.put(type, formatter);
                    }
                }
            }
        }
    }
    
    public void remove(Formatter<?>... formatters) {
        if (formatters != null && formatters.length > 0) {
            for (Formatter<?> formatter : formatters) {
                if (formatter != null) {
                    Class<?> type = ClassUtils.getGenericClass(formatter.getClass());
                    if (type != null) {
                        if (templateFormatters.get(type) == formatter) {
                            templateFormatters.remove(type);
                        }
                    }
                }
            }
        }
    }
    
    public void clear() {
        templateFormatters.clear();
    }

    @SuppressWarnings("unchecked")
    public String format(Object value) {
        if (value == null) {
            Formatter<?> formatter = templateFormatters.get(Void.class);
            if (formatter != null) {
                return formatter.format(null);
            }
            return null;
        } else {
            Formatter<Object> formatter = (Formatter<Object>) templateFormatters.get(value.getClass());
            if (formatter != null) {
                return formatter.format(value);
            }
            return StringUtils.toString(value);
        }
    }
    
    public Class<? extends Object>[] getSupported() {
        return new Class<?>[]{ Object.class };
    }
    
}
