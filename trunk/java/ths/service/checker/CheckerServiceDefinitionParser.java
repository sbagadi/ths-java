package ths.service.checker;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import ths.spring.parser.AbstractNamedBeanDefinitionParser;

public class CheckerServiceDefinitionParser extends AbstractNamedBeanDefinitionParser<CheckerService> {
	
    protected void doParse(Element element, BeanDefinitionBuilder builder) {  
        String name = element.getAttribute("name");  
        String age = element.getAttribute("age");
        
        if (StringUtils.hasText(name)) {
        	builder.addPropertyValue("name", name);
        }
        
        if (StringUtils.hasText(age)) {  
        	builder.addPropertyValue("age", Integer.valueOf(age));  
        }
        
        List<Element> elements = DomUtils.getChildElementsByTagName(element, "include");
        Iterator<Element> iterators = elements.iterator();
        while (iterators.hasNext()) {
        	Element include = iterators.next();
        	include.getAttribute("method");
        }
    }

	@Override
	protected String getDefaultName() {
		return CheckerService.DEFAULT_BEAN_NAME;
	}
}
