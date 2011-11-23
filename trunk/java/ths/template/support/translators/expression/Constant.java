package ths.template.support.translators.expression;

import java.text.ParseException;

/**
 * Constant
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public final class Constant extends AbstractExpression {
    
    public static final Constant NULL = new Constant(null, null, "null");

    public static final Constant EMPTY = new Constant(null, null, "");

    public static final Constant TRUE = new Constant(true, boolean.class, "true");

    public static final Constant FALSE = new Constant(false, boolean.class, "false");
    
    private final Object value;

    private final Class<?> type;
    
    private final String literal;

    public Constant(Object value, Class<?> type, String literal){
        super(null, null, 0, null);
        this.value = value;
        this.type = type;
        this.literal = literal;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getReturnType() throws ParseException {
        return type;
    }

    public String getCode() throws ParseException {
        return literal;
    }

    @Override
    public String toString() {
        return literal;
    }

}
