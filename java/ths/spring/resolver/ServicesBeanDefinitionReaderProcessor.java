package ths.spring.resolver;

import org.springframework.util.Assert;
import org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.NamespaceHandlerResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.EntityResolver;

import ths.spring.ServicesNamespace;

public class ServicesBeanDefinitionReaderProcessor {
    private final XmlBeanDefinitionReader reader;

    public ServicesBeanDefinitionReaderProcessor(XmlBeanDefinitionReader reader) {
    	Assert.notNull(reader, "XmlBeanDefinitionReader must not be null");
        this.reader = reader;
    }

    public void addServicesSupport() {
        ResourceLoader resourceLoader = reader.getResourceLoader();

        if (resourceLoader == null) {
            resourceLoader = new DefaultResourceLoader();
        }

        ClassLoader classLoader = resourceLoader.getClassLoader();

        // schema providers
        ServicesNamespace sns = ServicesNamespace.getInstance(classLoader);
   
        // default resolvers
        EntityResolver defaultEntityResolver = new ResourceEntityResolver(resourceLoader);
        NamespaceHandlerResolver defaultNamespaceHanderResolver = new DefaultNamespaceHandlerResolver(classLoader);

        // new resolvers
        EntityResolver entityResolver = new ServicesSchemaResolver(classLoader, defaultEntityResolver, sns);
        NamespaceHandlerResolver namespaceHandlerResolver = new ServicesNamespaceHandlerResolver(defaultNamespaceHanderResolver, sns);
        
        reader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_XSD);
        reader.setEntityResolver(entityResolver);
        reader.setNamespaceHandlerResolver(namespaceHandlerResolver);
    }
}