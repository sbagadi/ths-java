package ths.template.support;

import java.io.IOException;
import java.text.ParseException;

import ths.core.Resource;
import ths.template.Template;

/**
 * Template Parser. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setParser(Parser)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public interface Parser {
    
	/**
	 * Parse the template.
	 * 
	 * @param source - Template source.
	 * @return Java source code
	 */
    Template parse(Resource source) throws IOException, ParseException;
	
}
