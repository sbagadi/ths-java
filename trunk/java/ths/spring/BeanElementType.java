package ths.spring;

import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.BeanDefinitionParser;

public enum BeanElementType {
    BEAN_DEFINITION_PARSER(BeanDefinitionParser.class, ".bean-definition-parsers"),
    BEAN_DEFINITION_DECORATOR(BeanDefinitionDecorator.class, ".bean-definition-decorators"),
    BEAN_DEFINITION_DECORATOR_FOR_ATTRIBUTE(BeanDefinitionDecorator.class, ".bean-definition-decorators-for-attribute");

    private final Class<?> interfaceClass;
    private final String configSuffix;

    private BeanElementType(Class<?> clazz, String suffix) {
        this.interfaceClass = clazz;
        this.configSuffix = suffix;
    }

    public Class<?> getElementInterface() {
        return interfaceClass;
    }

    public String getElementLocationSuffix() {
        return configSuffix;
    }
}
