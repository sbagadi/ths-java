package ths.template.base;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * Statement. (API, Prototype, ThreadSafe)
 * 
 * @see com.googlecode.httl.Expression
 * @see com.googlecode.httl.Template
 */
public interface Statement extends Evaluable {
    
    /**
     * Get source.
     * 
     * @return source.
     */
    String getSource() throws IOException;
    
    /**
     * Get the template code.
     * 
     * @return Template code.
     */
    String getCode() throws ParseException;
    
    /**
     * Get parameter types. (Ordered)
     * 
     * @return parameter types.
     */
    Map<String, Class<?>> getParameterTypes();
    
}
