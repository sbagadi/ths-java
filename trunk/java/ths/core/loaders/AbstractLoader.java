package ths.core.loaders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ths.core.Resource;
import ths.core.Loader;

public abstract class AbstractLoader implements Loader {
	protected LoaderConfigurable configurable = null;
	
	public void setLoaderConfigurable(LoaderConfigurable configurable) {
		this.configurable = configurable;
	}

	public List<String> list() throws IOException {
		String directory = this.configurable.getDirectory();
		if (directory == null || directory.length() == 0) {
			return new ArrayList<String>(0);
		}
		String[] suffixes = this.configurable.getSuffixes();
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
			encoding = this.configurable.getEncoding();
		}
		String dir = this.configurable.getDirectory();
		return doLoad(name, encoding, dir == null ? name : dir + name);
	}

	protected abstract Resource doLoad(String name, String encoding, String path)
			throws IOException;

}
