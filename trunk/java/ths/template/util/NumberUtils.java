package ths.template.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * NumberUtils. (Tool, Static, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class NumberUtils {

    private static final String DEFAULT_FORMAT = "###,##0.###";

    private static final ThreadLocal<Map<String, DecimalFormat>> LOCAL = new ThreadLocal<Map<String, DecimalFormat>>();

    public static DecimalFormat getDecimalFormat(String format) {
        if (format == null || format.length() == 0) {
            format = DEFAULT_FORMAT;
        }
        Map<String, DecimalFormat> formatters = LOCAL.get();
        if (formatters == null) {
            formatters= new HashMap<String, DecimalFormat>();
            LOCAL.set(formatters);
        }
        DecimalFormat formatter = formatters.get(format);
        if (formatter == null) {
            formatter = new DecimalFormat(format);
            formatters.put(format, formatter);
        }
        return formatter;
    }
    
    public static String formatNumber(Number value, String format) {
        return getDecimalFormat(format).format(value);
    }
    
}
