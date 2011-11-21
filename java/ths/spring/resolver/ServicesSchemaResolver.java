package ths.spring.resolver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ths.spring.ServicesNamespace;

public class ServicesSchemaResolver implements EntityResolver {
	
	public static final String DEFAULT_SPRING_SCHEMA_MAPPINGS_LOCATION = "META-INF/spring.schemas";
	private static final Logger logger = LoggerFactory.getLogger(ServicesSchemaResolver.class);
    private final EntityResolver defaultEntityResolver;
    private final ServicesNamespace sns;
    private final ClassLoader classLoader;
    private volatile Map<String, String> schemaMappings;
    
    public ServicesSchemaResolver(ClassLoader classLoader, EntityResolver defaultEntityResolver, ServicesNamespace sns) {
    	this.classLoader = classLoader;
        this.defaultEntityResolver = defaultEntityResolver;
        this.sns = sns;
    }
    
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		logger.trace("Trying to resolve XML entity with public id [" + publicId +"] and system id [" + systemId + "]");
	
		if (systemId != null) {
			InputSource source = resolveServicesEntity(publicId, systemId);
			if (source != null) {
				logger.debug("Found XML schema for systemId {}: {}", systemId, publicId);
				return source;
			} else if(defaultEntityResolver != null) {
				return defaultEntityResolver.resolveEntity(publicId, systemId);
			}
		}
		
		return null;
	}    
    
	public InputSource resolveServicesEntity(String publicId, String systemId) throws IOException {
		String resourceLocation = getSchemaMappings().get(systemId);
		if (resourceLocation != null) {
			Resource resource = new ClassPathResource(resourceLocation, this.classLoader);
			try {
				InputSource source = new InputSource(resource.getInputStream());
				source.setPublicId(publicId);
				source.setSystemId(systemId);
	
				logger.debug("Found XML schema [" + systemId + "] in classpath: " + resourceLocation);
				return source;
			} catch (FileNotFoundException ex) {
				logger.debug("Couldn't find XML schema [" + systemId + "]: " + resource, ex);
			}
		}

		return null;
	}
	
	private Map<String, String> getSchemaMappings() {
		if (this.schemaMappings == null) {
			synchronized (this) {
				if (this.schemaMappings == null) {
					Properties springSchemaMappings = getSpringSchemaMappings();
					Properties servicesSchemaMappings = getServicesSchemaMappings();
					Map<String, String> schemaMappings = new ConcurrentHashMap<String, String>();
					CollectionUtils.mergePropertiesIntoMap(springSchemaMappings, schemaMappings);					
					CollectionUtils.mergePropertiesIntoMap(servicesSchemaMappings, schemaMappings);
					this.schemaMappings = schemaMappings;					
				}
			}
		}

		return this.schemaMappings;
	}
	
	private Properties getSpringSchemaMappings() {
		logger.debug("Loading schema mappings from [" + DEFAULT_SPRING_SCHEMA_MAPPINGS_LOCATION + "]");
		Properties mappings = new Properties();
		try {
			mappings = PropertiesLoaderUtils.loadAllProperties(DEFAULT_SPRING_SCHEMA_MAPPINGS_LOCATION, this.classLoader);
			logger.debug("Loaded schema mappings: " + mappings);
			
		} catch (IOException ex) {
			throw new IllegalStateException("Unable to load schema mappings from location [" + DEFAULT_SPRING_SCHEMA_MAPPINGS_LOCATION + "]", ex);
		}

		return mappings;
	}

	private Properties getServicesSchemaMappings() {
		logger.debug("Loading schema mappings from [ function getSchemaMappings in class ServicesNamespace ]");
		Properties mappings = sns.getSchemaMappings();
		logger.debug("Loaded schema mappings: " + mappings);

		return mappings;
	}

	@Override
	public String toString() {
		return "EntityResolver using mappings " + getSchemaMappings();
	}	
    

	
}
