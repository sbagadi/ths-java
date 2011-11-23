package ths.template.support.formatters;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import ths.core.Configurable;
import ths.template.TemplateConfiguration;
import ths.template.support.Formatter;

/**
 * NumberFormatter. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setFormatter(Formatter)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class NumberFormatter implements Formatter<Number>, Configurable<TemplateConfiguration> {
    
    private String numberFormat;
    
    @Override
    public void configure(TemplateConfiguration config) {
        String format = config.getNumberFormat();
        
        new DecimalFormat(format).format(0);
        this.numberFormat = format;
    }
    
    public String format(Number value) {
        if (numberFormat == null || numberFormat == null) {
            return NumberFormat.getNumberInstance().format(value);
        }
        return new DecimalFormat(numberFormat).format(value);
    }

}
