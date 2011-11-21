package ths.spring.parser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.w3c.dom.Element;

public abstract class AbstractSingleBeanDefinitionParser<T> 
	extends org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser {
	
    @Override
    protected final Class<?> getBeanClass(Element element) {
		Type genType = getClass().getGenericSuperclass();  
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
		return (Class<?>) params[0];
    }
}
