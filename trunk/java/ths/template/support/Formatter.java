package ths.template.support;

public interface Formatter<T> {
    
    /**
     * Format the value to a string.
     * 
     * @param value - variable value.
     * @return string value
     */
    String format(T value);
    
}
