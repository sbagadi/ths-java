package ths.template.support.loggers;

import java.io.Serializable;

import com.googlecode.httl.Constants;
import com.googlecode.httl.support.Logger;

/**
 * SimpleLogger.
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class SimpleLogger implements Logger, Serializable {

	private static final long serialVersionUID = 1L;

	private static final String prefix = "[" + Constants.HTTL + "] ";

	private String getMessage(String msg) {
		if (prefix == null)
			return msg;
		return prefix + msg;
	}

	public void debug(String msg) {
		System.out.println(getMessage(msg));
	}

	public void debug(String msg, Throwable e) {
		System.out.println(getMessage(msg));
		if (e != null)
			e.printStackTrace();
	}

	public void info(String msg) {
		System.out.println(getMessage(msg));
	}

	public void info(String msg, Throwable e) {
		System.out.println(getMessage(msg));
		if (e != null)
			e.printStackTrace();
	}

	public void warn(String msg) {
		System.err.println(getMessage(msg));
	}

	public void warn(String msg, Throwable e) {
		System.err.println(getMessage(msg));
		if (e != null)
			e.printStackTrace();
	}

	public void error(String msg) {
		System.err.println(getMessage(msg));
	}

	public void error(String msg, Throwable e) {
		System.err.println(getMessage(msg));
		if (e != null)
			e.printStackTrace();
	}

	public boolean isDebugEnabled() {
		return true;
	}

	public boolean isInfoEnabled() {
		return true;
	}

	public boolean isWarnEnabled() {
		return true;
	}

	public boolean isErrorEnabled() {
		return true;
	}

	public boolean isFatalEnabled() {
		return true;
	}

};
