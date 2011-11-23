package ths.template.support.formatters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import ths.template.support.Formatter;

/**
 * NumberFormatter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFormatter(Formatter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class NumberFormatter implements Formatter<Number> {
    
    private String numberFormat;
    
    public void configure(Map<String, String> config) {
        String format = config.get(NUMBER_FORMAT);
        if (format != null && format.trim().length() > 0) {
            format = format.trim();
            new DecimalFormat(format).format(0);
            this.numberFormat = format;
        }
    }
    
    public String format(Number value) {
        if (numberFormat == null || numberFormat == null) {
            return NumberFormat.getNumberInstance().format(value);
        }
        return new DecimalFormat(numberFormat).format(value);
    }

}
