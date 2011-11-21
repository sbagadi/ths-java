package ths.core;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

public interface Resource extends Serializable {
	/**
	 * Get the template name.
	 * 
	 * @return Name
	 */
	String getName();
	
	/**
	 * Get the the template encoding.
	 * 
	 * @return Encoding
	 */
	String getEncoding();
	
	/**
	 * Get the the template last modified.
	 * 
	 * @return Last modified
	 */
	long getLastModified();

    /**
     * Get the the template length.
     * 
     * @return length
     */
    long getLength();

	/**
	 * Get the template source code reader.
	 * 
	 * @return Source code reader
	 */
	Reader getReader() throws IOException;
	
	/**
	 * Get the template source loader.
	 * 
	 * @return Source loader
	 */
	Loader getLoader();
}
