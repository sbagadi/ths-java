package ths.template.support.translators.expression;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import ths.template.Engine;
import ths.template.Evaluable;
import ths.template.Expression;
import ths.template.support.Compiler;
import ths.template.support.Translator;
import ths.template.util.ClassUtils;

/**
 * ExpressionImpl. (SPI, Prototype, ThreadSafe)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class ExpressionImpl implements Expression {

    private static final AtomicInteger sequence = new AtomicInteger();

    private final Engine engine;
    
    private final Compiler compiler;
    
    private final Translator resolver;
    
    private final String source;
    
    private final int offset;

    private final String code;

    private final Map<String, Class<?>> parameterTypes;

    private final Class<?> returnType;
    
    private volatile Evaluable evaluator;

    private final String[] importPackages;

    public ExpressionImpl(Engine engine, Compiler compiler, Translator resolver, String source, Map<String, Class<?>> parameterTypes, int offset, String code, Class<?> returnType, String[] importPackages){
        this.engine = engine;
        this.compiler = compiler;
        this.resolver = resolver;
        this.source = source;
        this.offset = offset;
        this.code = code;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.importPackages = importPackages;
    }
    
    public Translator getResolver() {
        return resolver;
    }
    
    public String getSource() {
        return source;
    }
    
    public String getCode() throws ParseException {
        return code;
    }

    public Map<String, Class<?>> getParameterTypes() {
        return parameterTypes;
    }
    
    public Class<?> getReturnType() throws ParseException {
        return returnType;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public Object evaluate(Map<String, Object> parameters) throws ParseException {
        if (evaluator == null) {
            synchronized (this) {
                if (evaluator == null) {
                    evaluator = newEvaluator();
                }
            }
        }
        return evaluator.evaluate(parameters);
    }
    
    private Evaluable newEvaluator() throws ParseException {
        StringBuilder imports = new StringBuilder();
        String[] packages = importPackages;
        if (packages != null && packages.length > 0) {
            for (String pkg : packages) {
                imports.append("import ");
                imports.append(pkg);
                imports.append(".*;\n");
            }
        }
        String className = (ExpressionImpl.class.getSimpleName() + "_" + sequence.incrementAndGet());
        String sourceCode = "package " + ExpressionImpl.class.getPackage().getName() + ";\n" 
                + imports.toString()
                + "public class " + className + " extends " + AbstractEvaluator.class.getName() + " {\n" 
                + "public " + className + "(" + Engine.class.getName() + " engine) {\n" 
                + "super(engine);\n" 
                + "}\n"
                + "public " + Object.class.getSimpleName() + " evaluate(" + Map.class.getName() + " parameters) throws " + ParseException.class.getName() + " {\n"
                + "return " + ClassUtils.class.getName() + ".boxed(" + getCode() + ");\n"
                + "}\n"
                + "}";
        try {
            return (Evaluable) compiler.compile(sourceCode).getConstructor(Engine.class).newInstance(engine);
        } catch (Exception e) {
            throw new ParseException("Failed to parse expression code: \n" + sourceCode + ", cause:" + ClassUtils.toString(e), getOffset());
        }
    }
    
}
