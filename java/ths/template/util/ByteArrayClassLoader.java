package ths.template.util;

/**
 * ByteArrayClassLoader. (Tool, Singleton, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ByteArrayClassLoader extends ClassLoader {

	public ByteArrayClassLoader() {
		super(ByteArrayClassLoader.class.getClassLoader());
	}

	public synchronized Class<?> getClass(String name, byte[] code) {
		if (name == null) {
			throw new IllegalArgumentException("");
		}
		return defineClass(name, code, 0, code.length);
	}

}