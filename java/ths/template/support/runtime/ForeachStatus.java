package ths.template.support.runtime;

import java.io.Serializable;

import ths.template.util.ClassUtils;
import ths.template.util.LinkedStack;

/**
 * ForeachStatus. (SPI, Prototype, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ForeachStatus implements Serializable {
    
    private static final long serialVersionUID = -6011370058720809056L;
    
    private final LinkedStack<ForeachCounter> stack = new LinkedStack<ForeachCounter>();
    
    public <T> T push(T list) {
        stack.push(new ForeachCounter(stack.peek(), ClassUtils.getSize(list), stack.size()));
        return list;
    }
    
    public void pop() {
        stack.pop();
    }
    
    public void increment() {
        stack.peek().increment();
    }

    public ForeachCounter getParent() {
        return stack.peek().getParent();
    }

    public int getSize() {
        return stack.peek().getSize();
    }
    
    public int getLevel() {
        return stack.peek().getLevel();
    }
    
    public int getIndex() {
        return stack.peek().getIndex();
    }
    
    public int getCount() {
        return stack.peek().getIndex() + 1;
    }
    
    public boolean isOdd() {
        return stack.peek().getIndex() % 2 != 0;
    }
    
    public boolean isEven() {
        return stack.peek().getIndex() % 2 == 0;
    }
    
    public boolean isFirst() {
        return stack.peek().getIndex() == 0;
    }
    
    public boolean isLast() {
        ForeachCounter counter = stack.peek();
        return counter.getIndex() >= counter.getSize() - 1;
    }
    
    public boolean isMiddle() {
        ForeachCounter counter = stack.peek();
        return counter.getIndex() > 0 && counter.getIndex() < counter.getSize() - 1;
    }
    
}
