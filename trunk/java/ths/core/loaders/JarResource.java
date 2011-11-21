package ths.core.loaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;

import ths.core.Loader;


public class JarResource extends InputStreamResource {

	private static final long serialVersionUID = 1L;

	private final File file;

	public JarResource(Loader loader, String name, String encoding, File file) {
		super(loader, name, encoding);
		this.file = file;
	}

	protected InputStream getInputStream() throws IOException {
		// 注：JarFile与File的设计是不一样的，File相当于C#的FileInfo，只持有信息，
		// 而JarFile构造时即打开流，所以每次读取数据时，重新new新的实例，而不作为属性字段持有。
		JarFile zipFile = new JarFile(file);
		return zipFile.getInputStream(zipFile.getEntry(getName()));
	}

	public long getLastModified() {
		try {
			JarFile zipFile = new JarFile(file);
			return zipFile.getEntry(getName()).getTime();
		} catch (Throwable e) {
			return super.getLastModified();
		}
	}

}
