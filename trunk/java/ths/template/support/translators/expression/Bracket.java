package ths.template.support.translators.expression;

import java.text.ParseException;

/**
 * Bracket
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class Bracket extends Operator {
    
    public static final Bracket ROUND = new Bracket("(");
    
    public static final Bracket SQUARE = new Bracket("[");

    private Bracket(String name) {
        super(null, null, 0, null, null, null, name, Integer.MAX_VALUE);
    }

    public Class<?> getReturnType() throws ParseException {
        return null;
    }

    public String getCode() throws ParseException {
        return getName();
    }
    
}
