package ths.commons.lang.mutable;

/**
 * Provides mutable access to a value.
 * <p>
 * <code>Mutable</code> is used as a generic interface to the implementations in this package.
 * <p>
 * A typical use case would be to enable a primitive or string to be passed to a method and allow that method to
 * effectively change the value of the primitive/string. Another use case is to store a frequently changing primitive in
 * a collection (for example a total in a map) without needing to create new Integer/Long wrapper objects.
 * 
 * @since 2.1
 * @param <T> the type to set and get 
 * @version $Id: Mutable.java 1153213 2011-08-02 17:35:39Z ggregory $
 */
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
     * @param value
     *            the value to store
     * @throws NullPointerException
     *             if the object is null and null is invalid
     * @throws ClassCastException
     *             if the type is invalid
     */
    void setValue(T value);

}
