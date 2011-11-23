package ths.template.support.loggers;

import java.io.Serializable;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import com.googlecode.httl.Constants;
import com.googlecode.httl.support.Logger;

/**
 * Log4jLogger
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class Log4jLogger implements Logger, Serializable {

	private static final long serialVersionUID = 1L;

	private static final org.apache.log4j.Logger logger = LogManager.getLogger(Constants.HTTL);

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void debug(String msg, Throwable e) {
		logger.debug(msg, e);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void info(String msg, Throwable e) {
		logger.info(msg, e);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable e) {
		logger.warn(msg, e);
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(String msg, Throwable e) {
		logger.error(msg, e);
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public boolean isWarnEnabled() {
		return logger.getLevel().toInt() >= Level.WARN_INT;
	}

	public boolean isErrorEnabled() {
		return logger.getLevel().toInt() >= Level.ERROR_INT;
	}

	public boolean isFatalEnabled() {
		return logger.getLevel().toInt() >= Level.FATAL_INT;
	}

}
