package ths.spring;

import static ths.commons.lang.Assert.*;
import static ths.commons.util.StringUtils.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;

public class SingleNamespace extends NamespaceHandlerSupport implements NamespaceHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ServicesNamespace sns;
	private final String name;
    private final String namespaceURL;
    private final String configLocationPrefix;
    private final TreeMap<BeanElementKey, BeanElement> elements;
    private ClassLoader classLoader;
    private boolean initialized;
    
	public SingleNamespace(ClassLoader loader, ServicesNamespace sns, String name, String URL) {
		this.classLoader = loader;
		this.sns = sns;
		this.name = name;
		this.namespaceURL = URL;
		this.configLocationPrefix = "META-INF/parsers/"+ name.replace('/', '-');
		this.elements = new TreeMap<BeanElementKey, BeanElement>();
		this.initialized = false;
	}
	
    public ServicesNamespace getServicesNamespace() {
        return sns;
    }

    public String getName() {
        return name;
    }

    public String getNamespaceUri() {
        return namespaceURL;
    }
    
    public NamespaceHandler getNamespaceHandler() {
        return this;
    }
    
    public BeanElement getBeanElement(String name, BeanElementType type) {
        return elements.get(new BeanElementKey(name, type));
    }

    public Collection<BeanElement> getBeanElements() {
        return elements.values();
    }    
    
	@Override
	public void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        for (BeanElementType type : BeanElementType.values()) {
        	definitionBeanElement(type);
        }		
	}
	
	private void definitionBeanElement(BeanElementType type) {
        String contribLocation = configLocationPrefix + type.getElementLocationSuffix();
        Properties mappings;
        logger.trace("Trying to load contributions at {}", contribLocation);
        System.out.println("load contributions:"+ contribLocation);
        try {
            mappings = PropertiesLoaderUtils.loadAllProperties(contribLocation, classLoader);
        } catch (IOException e) {
            throw new ServiceNamespaceException("Unable to load Contributions from " + contribLocation, e);
        }
        
        TreeMap<String, String> sortedMappings = new TreeMap<String, String>();
        for (Entry<Object, Object> entry : mappings.entrySet()) {
            String elementName = trimToNull((String) entry.getKey());
            String parserClassName = trimToNull((String) entry.getValue());

            sortedMappings.put(elementName, parserClassName);
            
            BeanElement element = new BeanElement(this, type, elementName, parserClassName);
            BeanElement existElement = elements.get(element.getKey());
            if (existElement != null) {
                throw new ServiceNamespaceException("Duplicated contributions from locations: " + contribLocation
                        + "\n" + "     " + existElement + "\n and " + element);
            }

            register(element);
            elements.put(element.getKey(), element);
        }       
	}
	
    private void register(BeanElement element) {
        Object obj;

        try {
            obj = instantiateContributionImplementation(element);
        } catch (FatalBeanException e) {
        	logger.warn("Skipped registration of {} due to the error: {}", element.getDescription(), e);
            return;
        }

        if (obj instanceof BeanElementAware) {
            ((BeanElementAware) obj).setBeanElement(element);
        }

        switch (element.getType()) {
            case BEAN_DEFINITION_PARSER:
                registerBeanDefinitionParser(element.getName(), (BeanDefinitionParser) obj);
                break;

            case BEAN_DEFINITION_DECORATOR:
                registerBeanDefinitionDecorator(element.getName(), (BeanDefinitionDecorator) obj);
                break;

            case BEAN_DEFINITION_DECORATOR_FOR_ATTRIBUTE:
                registerBeanDefinitionDecoratorForAttribute(element.getName(), (BeanDefinitionDecorator) obj);
                break;

            default:
                unreachableCode("unknown contributionType: %s", element.getType());
        }
    }

    private Object instantiateContributionImplementation(BeanElement element) throws FatalBeanException {
        String implementationClassName = element.getParserClassName();

        if (implementationClassName == null) {
            throw new FatalBeanException("Contribution class not defined: contributionType=" + element.getType()
                    + ", contributionName=" + element.getName() + ", configurationPoint="
                    + element.getSingleNamespace().getName() + ", namespaceUri="
                    + element.getSingleNamespace().getNamespaceUri());
        }

        Class<?> implementationClass;

        try {
            implementationClass = ClassUtils.forName(implementationClassName, classLoader);
        } catch (ClassNotFoundException e) {
            throw new FatalBeanException("Contribution class not found: contributionType=" + element.getType()
                    + ", contribuitionClass=" + implementationClassName + ", contributionName=" + element.getName()
                    + ", configurationPoint=" + element.getSingleNamespace().getName() + ", namespaceUri="
                    + element.getSingleNamespace().getNamespaceUri(), e);
        }

        if (!element.getType().getElementInterface().isAssignableFrom(implementationClass)) {
            throw new FatalBeanException("Contribution class does not implement the "
                    + element.getType().getElementInterface().getSimpleName() + " interface:  contributionType="
                    + element.getType() + ", contribuitionClass=" + implementationClassName + ", contributionName="
                    + element.getName() + ", configurationPoint=" + element.getSingleNamespace().getName()
                    + ", namespaceUri=" + element.getSingleNamespace().getNamespaceUri());
        }

        return BeanUtils.instantiateClass(implementationClass);
    }
    
    public Properties getSchemaMappings() {
    	Properties schemaMappings = new Properties();
    	
    	String[] map = Schema.getServicesSchemaLoction(namespaceURL, name);
    	schemaMappings.put(map[0], map[1]);
    	
    	BeanElement element;
    	Iterator<?> it = elements.keySet().iterator();
    	while (it.hasNext()) {
    		element = (BeanElement)elements.get(it.next());
    		if (element.getType() == BeanElementType.BEAN_DEFINITION_PARSER) {
    			map = Schema.getServicesSchemaLoction(namespaceURL, name, element.getName());
    			schemaMappings.put(map[0], map[1]);
    		}
    	}
    	
    	return schemaMappings;    	
    }
    
    public String getDescription() {
        return String.format("ConfigurationPoint[%s]", name);
    }	    
}
