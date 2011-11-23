package ths.template.support.translators.expression;

import java.text.ParseException;
import java.util.Map;

import ths.template.support.Translator;

/**
 * Variable
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public final class Variable extends AbstractExpression {
    
    private final String name;
    
    public Variable(Translator resolver, String source, int offset, Map<String, Class<?>> parameterTypes, String name){
        super(resolver, source, offset, parameterTypes);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public Class<?> getReturnType() throws ParseException {
        return getParameterTypes().get(name);
    }

    public String getCode() throws ParseException {
        return name;
    }

}
