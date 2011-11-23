package ths.template.support.runtime;

import java.io.Serializable;

/**
 * ForeachCounter. (SPI, Prototype, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ForeachCounter implements Serializable {

    private static final long serialVersionUID = -6011370058720809056L;
    
    private final ForeachCounter parent;

    private final int size;
    
    private final int level;
    
    private int index = 0;

    public ForeachCounter(ForeachCounter parent, int s, int l) {
        this.parent = parent;
        this.size = s;
        this.level = l;
    }

    public void increment() {
        index ++;
    }
    
    public ForeachCounter getParent() {
        return parent;
    }

    public int getSize() {
        return size;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getIndex() {
        return index;
    }
    
}
