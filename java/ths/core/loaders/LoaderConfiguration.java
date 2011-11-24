package ths.core.loaders;

import java.util.Map;

import ths.core.config.AbstractConfiguration;
import ths.core.exception.ConfigurationException;

public class LoaderConfiguration extends AbstractConfiguration {

	public String getInputEncoding() {
		return getLoaderParameter("loader.input.encoding", true);
	}

	public String getDirectory() {
		return getLoaderParameter("loader.directory", true);
	}

	public String getSuffixes() {
		return getLoaderParameter("loader.suffix", true);
	}
	
	public String getLoaders() {
		return getLoaderParameter("loader.loaders", true);
	}

	@Override
	public void loadDefaultConfig() {
		this.setParameter("loader.input.encoding", "utf-8");
		this.setParameter("loader.directory", "");
		this.setParameter("loader.suffix", "");
		this.setParameter("loader.loaders", "");
	}

	@Override
	public void loadUserConfig(Map<String, String> config) {
		this.mergeConfig(config);
	}
	
	private String getLoaderParameter(String key, boolean hasEmpty) {
		String val = null;
		try {
			val = this.getParameter(key, hasEmpty);
		} catch (ConfigurationException e) {
			throw new IllegalArgumentException("Configuration["+ key +"] is empty.");
		}
		return val;
	}	
}
