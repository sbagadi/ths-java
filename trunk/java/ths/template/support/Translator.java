package ths.template.support;

import java.text.ParseException;
import java.util.Map;

import ths.template.Expression;

/**
 * Expression Translator. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setTranslator(translator)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public interface Translator {
    
	/**
	 * Translate the expression.
	 * 
	 * @param source - Template expression source
	 * @param parameterTypes Expression parameter types
	 * @param offset - Template expression offset
	 * @return Java expression
	 */
	Expression translate(String source, Map<String, Class<?>> parameterTypes, int offset) throws ParseException;
	
}
