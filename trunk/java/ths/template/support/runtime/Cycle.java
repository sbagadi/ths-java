package ths.template.support.runtime;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Cycle.
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class Cycle {

	private final Object values;
	
	private final int length;

	private int index;

	public Cycle(Object values) {
		if (values == null) {
		    throw new IllegalArgumentException("cycle array == null");
		}
		if (values instanceof Collection) {
		    values = ((Collection<?>)values).toArray();
		}
		if (! values.getClass().isArray()
                || Array.getLength(values) == 0) {
            throw new IllegalArgumentException("cycle array length == 0");
        }
		this.values = values;
		this.length = Array.getLength(values);
		this.index = -1;
	}

	public Object getNext() {
		index += 1;
		if (index >= length)
			index = 0;
		return getValue();
	}

	public int getIndex() {
		return index;
	}

	public Object getValue() {
		if (index == -1)
			return null;
		return Array.get(values, index);
	}

	public Object values() {
		return values;
	}

	public String toString() {
		return String.valueOf(getNext());
	}

}
