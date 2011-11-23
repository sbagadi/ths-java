package ths.core;

public interface Configurable<T extends Configuration> {
	
	void configure(T config);
}