package ths.template.support.formatters;

import java.text.SimpleDateFormat;
import java.util.Date;

import ths.core.Configurable;
import ths.template.Configs;
import ths.template.support.Formatter;
import ths.template.util.DateUtils;

/**
 * DateFormatter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFormatter(Formatter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class DateFormatter implements Formatter<Date>, Configurable<Configs> {
    
    private String dateFormat;
    
    @Override
    public void configure(Configs config) {
        String format = config.getDateFormat();
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
