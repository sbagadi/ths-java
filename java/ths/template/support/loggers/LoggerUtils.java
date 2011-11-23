package ths.template.support.loggers;

import com.googlecode.httl.support.Logger;

/**
 * LoggerUtils
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class LoggerUtils {
    
    public static Logger getDefaultLogger() {
        try {
            return (Logger) Class.forName("com.googlecode.httl.support.loggers.Log4jLogger").newInstance();
        } catch (Throwable e) {
            try {
                return (Logger) Class.forName("com.googlecode.httl.support.loggers.JdkLogger").newInstance();
            } catch (Throwable e2) {
                return new SimpleLogger();
            }
        }
    }

}
