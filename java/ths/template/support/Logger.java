package ths.template.support;

public interface Logger {

	/**
	 * debug.
	 * 
	 * @param msg
	 */
	public void debug(String msg);

	/**
	 * debug.
	 * 
	 * @param msg
	 * @param e
	 */
	public void debug(String msg, Throwable e);

	/**
	 * info.
	 * 
	 * @param msg
	 */
	public void info(String msg);

	/**
	 * info.
	 * 
	 * @param msg
	 * @param e
	 */
	public void info(String msg, Throwable e);

	/**
	 * warn.
	 * 
	 * @param msg
	 */
	public void warn(String msg);

	/**
	 * warn.
	 * 
	 * @param msg
	 * @param e
	 */
	public void warn(String msg, Throwable e);

	/**
	 * error.
	 * 
	 * @param msg
	 */
	public void error(String msg);

	/**
	 * error.
	 * 
	 * @param msg
	 * @param e
	 */
	public void error(String msg, Throwable e);

	/**
	 * isDebugEnabled.
	 * 
	 * @return debug enabled
	 */
	public boolean isDebugEnabled();

	/**
	 * isInfoEnabled.
	 * 
	 * @return info enabled
	 */
	public boolean isInfoEnabled();

	/**
	 * isWarnEnabled.
	 * 
	 * @return warn enabled
	 */
	public boolean isWarnEnabled();

	/**
	 * isErrorEnabled.
	 * 
	 * @return error nabled
	 */
	public boolean isErrorEnabled();

}
