package ths.core;

import ths.core.exception.ConfigurationException;

public interface Configurable {
	
	void configure(Configuration config) throws ConfigurationException;
}