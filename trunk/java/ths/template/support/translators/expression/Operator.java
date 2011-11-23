package ths.template.support.translators.expression;

import java.util.Collection;
import java.util.Map;

import ths.template.support.Translator;

/**
 * BranchNode
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class Operator extends AbstractExpression {

    private final String name;
    
    private final int priority;

    private final Collection<Class<?>> functions;
    
    private final String[] packages;

    public Operator(Translator resolver, String source, int offset, Map<String, Class<?>> parameterTypes, Collection<Class<?>> functions, String[] packages, String name, int priority){
        super(resolver, source, offset, parameterTypes);
        this.name = name;
        this.priority = priority;
        this.functions = functions;
        this.packages = packages;
    }

    public Collection<Class<?>> getFunctions() {
        return functions;
    }

    public String[] getPackages() {
        return packages;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
