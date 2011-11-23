package ths.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

import ths.commons.util.StringUtils;

public abstract class AbstractPropertiesConfiguration extends AbstractConfiguration {

	// Windows file path prefix pattern
	private static final Pattern WINDOWS_FILE_PATTERN = Pattern.compile("^[A-Za-z]+:");
	
	private void setDefaultConfig() {
		this.loadDefaultConfig();
	}
	
	@Override
	public void loadUserConfig(Map<String, String> config) {
		this.setDefaultConfig();
	}
	
	/**
	 * Load properties file
	 * 
	 * @param path - File path
	 */
    public void loadProperties(String path) {
		if (path == null || path.length() == 0) {
			throw new IllegalArgumentException("path == null");
		}
		
		try {
			Properties properties = new Properties();
			InputStream in = null;
			try {
    			if (path.startsWith("/") || path.startsWith("./") || path.startsWith("../") 
    					|| WINDOWS_FILE_PATTERN.matcher(path).matches()) {
    				in = new FileInputStream(new File(path));
    			} else {
    				in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    			}
    			
    			if (in == null) {
    			    throw new FileNotFoundException("Not found file " + path);
    			}
    			
    			this.loadUserConfig(null);
    			properties.load(in);
    			
    			Iterator<Entry<Object, Object>> itr = properties.entrySet().iterator();
    			while (itr.hasNext()) {
    				 Entry<Object, Object> e = itr.next();
    				 String key = (String)e.getKey();
    				 String val = (String)e.getValue();
    				 
    				 if (this.containsParameter(key)) {
    					 this.setParameter(key, StringUtils.trimToEmpty(val));
    				 }
    			}
			} finally {
			    if (in != null) {
			        in.close();
			    }
			}
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
}
