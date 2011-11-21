package ths.template.base;

import java.io.Writer;
import java.util.Map;

import ths.template.Template;

/**
 * Context. (API, ThreadLocal, ThreadSafe)
 * 
 * @see com.googlecode.httl.support.runtime.AbstractTemplate#render(Map, Writer)
 */
public class Context {
    private Template template;
    private Map<String, Object> parameters;
    
    private static ThreadLocal<Context> LOCAL = new ThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    /**
     * Get current thread local context.
     * 
     * @return current thread local context.
     */
    public static Context getContext() {
        return LOCAL.get();
    }
    
    /**
     * Remove current thread local context.
     */
    public static void removeContext() {
        LOCAL.remove();
    }
        
    private Context() {}
    
    /**
     * Get current template.
     * 
     * @see #getContext()
     * @return current template.
     */
    public Template getTemplate() {
        return template;
    }
    
    /**
     * Set current template.
     * 
     * @see #getContext()
     * @param template - current template.
     * @return current context.
     */
    public Context setTemplate(Template template) {
        this.template = template;
        return this;
    }
    
    /**
     * Get current parameters.
     * 
     * @see #getContext()
     * @return current parameters.
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    /**
     * Set current parameters.
     * 
     * @see #getContext()
     * @param parameters - current parameters.
     * @return current context.
     */
    public Context setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
        return this;
    }
    
}
