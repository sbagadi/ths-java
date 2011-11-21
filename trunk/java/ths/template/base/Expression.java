package ths.template.base;

import java.text.ParseException;

import ths.template.support.Translator;

public interface Expression extends Statement {
    
    /**
     * Get the expression resolver.
     * 
     * @return expression resolver.
     */
    Translator getResolver();
    
    /**
     * Get offset.
     * 
     * @return offset.
     */
    int getOffset();
    
    /**
     * Get return type.
     * 
     * @return return type.
     * @throws ParseException TODO
     */
    Class<?> getReturnType() throws ParseException;
    
}
