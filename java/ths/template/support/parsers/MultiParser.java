package ths.template.support.parsers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ths.template.TemplateConfiguration;
import ths.template.Engine;
import ths.template.support.Parser;
import ths.template.support.Translator;
import ths.template.util.ClassUtils;

/**
 * MultiParser. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setParser(Parser)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class MultiParser extends AbstractParser {
    
    private Engine engine;
    
    private TemplateConfiguration config;
    
    private final List<AbstractParser> parsers = new ArrayList<AbstractParser>();
    
    public void add(AbstractParser... parsers) {
        if (parsers != null && parsers.length > 0) {
            for (AbstractParser parser : parsers) {
                if (parser != null) {
                    parser.setEngine(engine);
                    parser.configure(config);
                    this.parsers.add(parser);
                }
            }
        }
    }

    public void remove(AbstractParser... parsers) {
        if (parsers != null && parsers.length > 0) {
            for (AbstractParser parser : parsers) {
                if (parser != null) {
                    this.parsers.remove(parser);
                }
            }
        }
    }
    
    public void clear() {
        this.parsers.clear();
    }

    @Override
    public void setEngine(Engine engine) {
        super.setEngine(engine);
        this.engine = engine;
        for (AbstractParser parser : parsers) {
            parser.setEngine(engine);
        }
    }

    @Override
    public void configure(TemplateConfiguration config) {
        super.configure(config);
        this.config = config;
        String value = config.getParsers();
        if (value != null && value.length() > 0) {
            String[] values = value.split("\\,");
            for (String v : values) {
                if (v.length() > 0) {
                    AbstractParser parser = (AbstractParser) ClassUtils.newInstance(v);
                    parser.setEngine(engine);
                    parser.configure(config);
                    parsers.add(parser);
                }
            }
        }
    }

    protected String doParse(String name, String source, Translator resolver, 
                             List<String> parameters, List<Class<?>> parameterTypes, 
                             Set<String> variables, Map<String, Class<?>> types) throws IOException, ParseException {
        for (AbstractParser parser : parsers) {
            source = parser.doParse(name, source, resolver, parameters, parameterTypes, variables, types);
        }
        return source;
    }

}
