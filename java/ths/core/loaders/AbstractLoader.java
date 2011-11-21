package ths.core.loaders;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ths.core.Configurable;
import ths.core.Resource;
import ths.core.Loader;

public abstract class AbstractLoader implements Loader, Configurable {

	private String encoding;

	private String directory;

	private String[] suffixes;

	protected String getEncoding() {
		return encoding;
	}

	protected String getDirectory() {
		return directory;
	}

	public String[] getSuffixes() {
		return suffixes;
	}

	public void configure(Map<String, String> config) {
		String encoding = config.get(INPUT_ENCODING);
		if (encoding != null && encoding.trim().length() > 0) {
			encoding = encoding.trim();
			Charset.forName(encoding);
			this.encoding = encoding;
		}
		String directory = config.get(TEMPLATE_DIRECTORY);
		if (directory != null && directory.trim().length() > 0) {
			if (directory.endsWith("/") || directory.endsWith("\\")) {
				directory = directory.substring(0, directory.length() - 1);
			}
			this.directory = directory.trim();
		}
		String suffix = config.get(TEMPLATE_SUFFIX);
		if (suffix != null && suffix.trim().length() > 0) {
			this.suffixes = suffix.trim().split("\\s*\\,\\*");
		}
	}

	public List<String> list() throws IOException {
		String directory = getDirectory();
		if (directory == null || directory.length() == 0) {
			return new ArrayList<String>(0);
		}
		String[] suffixes = getSuffixes();
		if (suffixes == null || suffixes.length == 0) {
			return new ArrayList<String>(0);
		}
		List<String> list = doList(directory, suffixes);
		if (list == null) {
			list = new ArrayList<String>(0);
		}
		return list;
	}

	protected abstract List<String> doList(String directory, String[] suffixes)
			throws IOException;

	public Resource load(String name, String encoding) throws IOException {
		if (encoding == null || encoding.length() == 0) {
			encoding = this.encoding;
		}
		String dir = directory;
		return doLoad(name, encoding, dir == null ? name : dir + name);
	}

	protected abstract Resource doLoad(String name, String encoding, String path)
			throws IOException;

}
