package ths.core.loaders;

import java.util.Map;

import ths.core.config.AbstractConfiguration;

public class LoaderConfiguration extends AbstractConfiguration {
	
	private String inputEncoding;

	private String directory;

	private String suffixes;
	
	private String loaders;

	public String getInputEncoding() {
		return inputEncoding;
	}

	public String getDirectory() {
		return directory;
	}

	public String getSuffixes() {
		return suffixes;
	}
	
	public String getLoaders() {
		return loaders;
	}

	@Override
	public void loadDefaultConfig() {
		this.setParameter("loader.input.encoding", "utf-8");
		this.setParameter("loader.directory", "./");
		this.setParameter("loader.suffix", "*");
		this.setParameter("loader.loaders", "");
	}

	@Override
	public void loadUserConfig(Map<String, String> config) {
		this.mergeConfig(config);
	}
}
