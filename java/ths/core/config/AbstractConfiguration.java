package ths.core.config;

import java.util.Map;

import static ths.commons.lang.Assert.*;
import ths.core.Configuration;
import ths.core.exception.ConfigurationException;
import ths.commons.util.StringUtils;

public abstract class AbstractConfiguration implements Configuration {
	
	@Override
	public void mergeConfig(Map<String, String> config) {
		CONFIG.putAll(config);		
	}
	
	@Override
	public boolean containsParameter(String key) {
		return CONFIG.containsKey(key);
	}
	
	@Override
	public void setParameter(String key, String val) {
		CONFIG.put(key, val);
	}
	
	@Override
	public String getParameter(String paramName, boolean hasEmpty) throws ConfigurationException {
		String key = StringUtils.trimToNull(paramName);
		assertNotNull(key, "paramName must not be null");
		
		String val = CONFIG.get(key);
		if (!hasEmpty) StringUtils.trimToNull(val);
		
		if (null == val) {
			throw new ConfigurationException("Configuration["+ paramName +"] must not null.");
		}
		
		return val;
	}
	
	@Override
	public int getParameterAsInteger(String paramName) throws ConfigurationException {
		String val = this.getParameter(paramName, false);
		
		int result = 0;
		try {
			result = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			throw new ConfigurationException("Configuration["+ paramName +"] formart error.");
		}

		return result;
	}
	
	@Override
	public long getParameterAsLong(String paramName) throws ConfigurationException {
		String val = this.getParameter(paramName, false);
		long result = 0L;
		try {
			result = Long.parseLong(val);
		} catch (NumberFormatException e) {
			throw new ConfigurationException("Configuration["+ paramName +"] formart error.");
		}
		
		return result;		
	}
	
	@Override
	public float getParameterAsFloat(String paramName) throws ConfigurationException {
		String val = this.getParameter(paramName, false);
		float result = 0F;
		try {
			result = Float.parseFloat(val);
		} catch (NumberFormatException e) {
			throw new ConfigurationException("Configuration["+ paramName +"] formart error.");
		}
		
		return result;		
	}
	
	@Override
	public boolean getParameterAsBoolean(String paramName) throws ConfigurationException {
		String[] vals = {"1", "0", "ON", "OFF"};
		String val = this.getParameter(paramName, false);
		boolean find = false;
		boolean result = false;
		
		for (int i = 0; i < vals.length; i++) {
			if (vals[i].equalsIgnoreCase(val)) {
				find = true;
				break;
			}
		}
		
		if (!find) {
			throw new ConfigurationException("Configuration["+ paramName +"] formart error.");
		}
		
		if ("1".equalsIgnoreCase(val) || "ON".equalsIgnoreCase(val)) {
			result = true;
		}
		
		return result;
	}
}
