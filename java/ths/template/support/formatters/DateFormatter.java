package ths.template.support.formatters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ths.template.support.Formatter;
import ths.template.util.DateUtils;

/**
 * DateFormatter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFormatter(Formatter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class DateFormatter implements Formatter<Date> {
    
    private String dateFormat;
    
    public void configure(Map<String, String> config) {
        String format = config.get(DATE_FORMAT);
        if (format != null && format.trim().length() > 0) {
            format = format.trim();
            new SimpleDateFormat(format).format(new Date());
            this.dateFormat = format;
        }
    }
    
    public String format(Date value) {
        return DateUtils.formatDate(value, dateFormat);
    }
    
}
