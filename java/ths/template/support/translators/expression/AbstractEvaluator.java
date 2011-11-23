package ths.template.support.translators.expression;

import ths.template.Engine;
import ths.template.Evaluable;

/**
 * AbstractEvaluator
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public abstract class AbstractEvaluator implements Evaluable {
    
    private final Engine engine;

    public AbstractEvaluator(Engine engine){
        this.engine = engine;
    }
    
    public Engine getEngine() {
        return engine;
    }

}
