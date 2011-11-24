package ths.template.support.translators;

import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

import ths.core.Configurable;
import ths.template.Engine;
import ths.template.Expression;
import ths.template.Configs;
import ths.template.support.EngineAware;
import ths.template.support.Translator;
import ths.template.support.translators.expression.ExpressionImpl;
import ths.template.util.StringUtils;

/**
 * DfaResolver. (SPI, Singleton, ThreadSafe)
 * 
 * Deterministic Finite state Automata (DFA)
 * 
 * @see com.googlecode.httl.Engine#setTranslator(Translator)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class DfaTranslator implements Translator, Configurable<Configs>, EngineAware {
    
    private Engine engine;

    protected String[] importPackages;

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    
    @Override
    public void configure(Configs config) {
        String packages = config.getImportPackages(); 
        this.importPackages = packages.trim().split("\\s*\\,\\s*");
    }
    
	public Expression translate(String source, Map<String, Class<?>> parameterTypes, int offset) throws ParseException {
	    source = StringUtils.unescapeHtml(source);
	    Collection<Class<?>> functions = engine.getFunctions().keySet();
	    Expression node = new DfaParser(this, parameterTypes, functions, importPackages, offset).parse(source);
	    return new ExpressionImpl(engine, engine.getCompiler(), this, source, parameterTypes, offset, node.getCode(), node.getReturnType(), importPackages);
	}

}
