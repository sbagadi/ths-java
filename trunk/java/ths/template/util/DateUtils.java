package ths.template.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * DateUtils. (Tool, Static, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class DateUtils {
    
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final ThreadLocal<Map<String, SimpleDateFormat>> LOCAL = new ThreadLocal<Map<String, SimpleDateFormat>>();

    public static SimpleDateFormat getSimpleDateFormat(String format) {
        if (format == null || format.length() == 0) {
            format = DEFAULT_FORMAT;
        }
        Map<String, SimpleDateFormat> formatters = LOCAL.get();
        if (formatters == null) {
            formatters= new HashMap<String, SimpleDateFormat>();
            LOCAL.set(formatters);
        }
        SimpleDateFormat formatter = formatters.get(format);
        if (formatter == null) {
            formatter = new SimpleDateFormat(format);
            formatters.put(format, formatter);
        }
        return formatter;
    }
    
    public static String formatDate(Date value, String format) {
        return getSimpleDateFormat(format).format(value);
    }
    
    public static Date toDate(String value, String format) {
        try {
            return getSimpleDateFormat(format).parse(value);
        } catch (ParseException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
