package ths.template.support;

import java.text.ParseException;

/**
 * Compiler. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setCompiler(Compiler)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public interface Compiler {
    
	/**
	 * Compile java source code.
	 * 
	 * @param code Java source code
	 * @return Compiled class
	 */
	Class<?> compile(String code) throws ParseException;
	
}
