package ths.template.support.parsers;

import java.util.regex.Pattern;

import ths.template.support.Parser;

/**
 * CommonTemplateParser. (SPI, Singleton, ThreadSafe)
 * 
 * @see com.googlecode.httl.Engine#setParser(Parser)
 *
 * @author Liang Fei (liangfei0201 AT gmail DOT com)
 */
public class CommonTemplateParser extends CommentParser {
    
    protected static final Pattern COMMON_TEMPLATE_PATTERN = Pattern.compile("$([a-z:]+)\\{([^}]*?)\\}");

    protected Pattern getPattern() {
        return COMMON_TEMPLATE_PATTERN;
    }
    
    protected String getDiretive(String name, String value) {
        return "$" + name + "{" + value + "}";
    }
    
}