package ths.template.support.compilers;

import java.text.ParseException;

import ths.core.Configurable;
import ths.template.Configs;
import ths.template.support.Compiler;
import ths.template.util.ClassUtils;

/**
 * AdaptiveCompiler. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setCompiler(Compiler)
 * 
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class AdaptiveCompiler implements Compiler, Configurable<Configs> {
    
    private Compiler compiler;

    @Override
    @SuppressWarnings("unchecked")
    public void configure(Configs config) {
        String version = config.getJavaVersion();
        
        if (version == null || ClassUtils.isBeforeJava6(version.trim())) {
            compiler = new JavassistCompiler();
        } else {
            compiler = new JdkCompiler();
        }
        if (compiler instanceof Configurable) {
            ((Configurable<Configs>)compiler).configure(config);
        }
    }

    public Class<?> compile(String code) throws ParseException {
        return compiler.compile(code);
    }

}
