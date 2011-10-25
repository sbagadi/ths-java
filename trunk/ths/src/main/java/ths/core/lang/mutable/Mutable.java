package ths.core.lang.mutable;

public interface Mutable<T> {
    /**
     * Gets the value of this mutable.
     * 
     * @return the stored value
     */
    T getValue();

    /**
     * Sets the value of this mutable.
     * 
     * @param value the value to store
     * @throws NullPointerException
     *             if the object is null and null is invalid
     * @throws ClassCastException
     *             if the type is invalid
     */
    void setValue(T value);
}
