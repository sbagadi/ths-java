package ths.template.support;

import java.text.ParseException;

public interface Compiler {
    
	/**
	 * Compile java source code.
	 * 
	 * @param code Java source code
	 * @return Compiled class
	 */
	Class<?> compile(String code) throws ParseException;
	
}
