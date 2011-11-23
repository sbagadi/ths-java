package ths.template.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * ArraySet
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ArraySet<T> extends ArrayList<T> implements Set<T> {

    private static final long serialVersionUID = 4762663840185149857L;

    public ArraySet(){
        super();
    }

    public ArraySet(Collection<? extends T> c){
        super(c);
    }

    public ArraySet(int initialCapacity){
        super(initialCapacity);
    }

}
