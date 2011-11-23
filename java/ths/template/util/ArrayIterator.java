package ths.template.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayIterator. (Tool, Prototype, ThreadUnsafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ArrayIterator<T> implements Iterator<T> {

    private final Object array;

    private final int    length;

    private volatile int index;

    public ArrayIterator(Object array){
        this.array = array;
        this.length = Array.getLength(array);
    }

    public Object getArray() {
        return array;
    }

    public boolean hasNext() {
        return index < length;
    }

    @SuppressWarnings("unchecked")
    public T next() {
        if (! hasNext()) {
            throw new NoSuchElementException();
        }
        return (T) Array.get(array, index ++);
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported");
    }
}
