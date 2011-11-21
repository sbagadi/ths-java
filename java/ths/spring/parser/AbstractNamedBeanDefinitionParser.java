package ths.spring.parser;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public abstract class AbstractNamedBeanDefinitionParser<T> extends AbstractSingleBeanDefinitionParser<T> {
	
	protected abstract String getDefaultName();
	
	@Override
	protected final String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) throws BeanDefinitionStoreException {
		String id = super.resolveId(element, definition, parserContext);
		if (StringUtils.hasText(id)) {
			return id;
		}
		
		return getDefaultName();
	}
}
