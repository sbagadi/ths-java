package ths.core;

import java.util.HashMap;
import java.util.Map;

import ths.core.exception.ConfigurationException;

public interface Configuration {
	
	Map<String, String> CONFIG = new HashMap<String, String>();
	
	void loadDefaultConfig();
	void mergeConfig(Map<String, String> config);
	void loadUserConfig(Map<String, String> config);
	boolean containsParameter(String key);
	void setParameter(String key, String val);
	String getParameter(String key, boolean hasEmpty) throws ConfigurationException;
	int getParameterAsInteger(String paramName) throws ConfigurationException;
	long getParameterAsLong(String paramName) throws ConfigurationException;
	float getParameterAsFloat(String paramName) throws ConfigurationException;
	boolean getParameterAsBoolean(String paramName) throws ConfigurationException;
}
