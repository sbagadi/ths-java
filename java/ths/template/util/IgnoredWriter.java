package ths.template.util;

import java.io.IOException;
import java.io.Writer;

/**
 * IgnoredWriter. (Tool, Singleton, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class IgnoredWriter extends Writer {

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void close() throws IOException {
	}

}
