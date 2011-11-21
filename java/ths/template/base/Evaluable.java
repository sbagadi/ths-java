package ths.template.base;

import java.text.ParseException;
import java.util.Map;

/**
 * Evaluable. (API, Prototype, ThreadSafe)
 * 
 * @see com.googlecode.httl.Statement
 * @see com.googlecode.httl.Expression
 * @see com.googlecode.httl.Template
 */
public interface Evaluable {

    /**
     * Evaluate the expression.
     * 
     * @param parameters - parameters
     * @return result.
     */
    Object evaluate(Map<String, Object> parameters) throws ParseException;
    
}
