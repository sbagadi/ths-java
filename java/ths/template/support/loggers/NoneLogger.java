package ths.template.support.loggers;

import java.io.Serializable;

import com.googlecode.httl.support.Logger;

/**
 * NoneLogger
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class NoneLogger implements Logger, Serializable {

	private static final long serialVersionUID = 1L;

	public NoneLogger(){}

	public void debug(String msg) {
	}

	public void debug(String msg, Throwable e) {
	}

	public void error(String msg) {
	}

	public void error(String msg, Throwable e) {
	}

	public void info(String msg) {
	}

	public void info(String msg, Throwable e) {
	}

	public boolean isDebugEnabled() {
		return false;
	}

	public boolean isErrorEnabled() {
		return false;
	}

	public boolean isFatalEnabled() {
		return false;
	}

	public boolean isInfoEnabled() {
		return false;
	}

	public boolean isWarnEnabled() {
		return false;
	}

	public void warn(String msg) {
	}

	public void warn(String msg, Throwable e) {
	}

}
