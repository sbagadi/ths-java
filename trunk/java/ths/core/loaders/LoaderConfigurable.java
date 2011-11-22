package ths.core.loaders;

import java.nio.charset.Charset;

import ths.core.Configurable;
import ths.core.Configuration;
import ths.core.exception.ConfigurationException;

public class LoaderConfigurable implements Configurable {
	
	public static final String CONF_PARA_LOADER_ENCODING = "loader.encoding";
	public static final String CONF_PARA_LOADER_DIRECTORY = "loader.directory";
	public static final String CONF_PARA_LOADER_SUFFIX = "loader.suffix";
	
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
	
	@Override
	public void configure(Configuration config) throws ConfigurationException {
		String encoding = config.getParameter(CONF_PARA_LOADER_ENCODING, false);
		String directory = config.getParameter(CONF_PARA_LOADER_DIRECTORY, false);
		String suffix = config.getParameter(CONF_PARA_LOADER_SUFFIX, false);
		
		Charset.forName(encoding);
		this.encoding = encoding;
		
		if (directory.endsWith("/") || directory.endsWith("\\")) {
			directory = directory.substring(0, directory.length() - 1);
		}
		this.directory = directory.trim();
		
		this.suffixes = suffix.split("\\s*\\,\\*");
	}
}
