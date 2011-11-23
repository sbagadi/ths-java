package ths.template.support.translators.expression;

import java.text.ParseException;
import java.util.Map;

import ths.template.Expression;
import ths.template.support.Translator;

/**
 * AbstractExpression
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class AbstractExpression implements Expression {
    
    private final Translator resolver;

    private final String source;
    
    private final int offset;
    
    private final Map<String, Class<?>> parameterTypes;

    public AbstractExpression(Translator resolver, String source, int offset, Map<String, Class<?>> parameterTypes){
        this.resolver = resolver;
        this.source = source;
        this.offset = offset;
        this.parameterTypes = parameterTypes;
    }

    public Translator getResolver() {
        return resolver;
    }
    
    public String getSource() {
        return source;
    }
    
    public int getOffset() {
        return offset;
    }

    public Map<String, Class<?>> getParameterTypes() {
        return parameterTypes;
    }
    
    public Class<?>[] getReturnTypes() throws ParseException {
        Class<?> type = getReturnType();
        return type == null ? new Class<?>[0] : new Class<?>[] { type };
    }
    
    public Object evaluate(Map<String, Object> parameters) throws ParseException {
        return null;
    }
    
}
