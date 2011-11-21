package ths.spring;

import static ths.commons.util.StringUtils.*;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ServicesNamespace {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String DEFAULT_CONFIG_LOCATION = "META-INF/services.namespaces";
	private static ServicesNamespace servicesNamespace = null;
	
    private final HashMap<String, SingleNamespace> uriToSingleNamespace;
    private final TreeMap<String, SingleNamespace> nameToSingleNamespace;
    private final Collection<SingleNamespace> namespaces;	
	private ClassLoader classLoader;
	
	private ServicesNamespace(ClassLoader loader) {
		classLoader = loader;
		uriToSingleNamespace = new HashMap<String, SingleNamespace>();
		nameToSingleNamespace = new TreeMap<String, SingleNamespace>();;
		namespaces = Collections.unmodifiableCollection(nameToSingleNamespace.values());;
		
		init();
	}
	
	public static ServicesNamespace getInstance(ClassLoader classLoader) {
		if (null==servicesNamespace) {
			servicesNamespace = new ServicesNamespace(classLoader);
		}
		return servicesNamespace;
	}
	
	private void init() {
        Properties mappings;
        logger.trace("Trying to load configuration points at {}", DEFAULT_CONFIG_LOCATION);

        try {
        	mappings = PropertiesLoaderUtils.loadAllProperties(DEFAULT_CONFIG_LOCATION, classLoader);
        } catch (IOException e) {
            throw new ServiceNamespaceException("Unable to load Configuration Points from "+ DEFAULT_CONFIG_LOCATION, e);
        }
        
        for (Entry<Object, Object> entry : mappings.entrySet()) {
            String name = (String) entry.getKey();
            String namespaceURL = (String) entry.getValue();
            
            if (!namespaceURL.endsWith(name)) {
                throw new ServiceNamespaceException("Naming Convention Violation: namespace URI [" + namespaceURL
                        + "] of configuration point should end with its name [" + name
                        + "].  This configuration point is located at " + DEFAULT_CONFIG_LOCATION + ".");
            }
            
            SingleNamespace sn = new SingleNamespace(classLoader, this, name, namespaceURL);

            uriToSingleNamespace.put(namespaceURL, sn);
            nameToSingleNamespace.put(name, sn);
        } 
	}
	
    public Collection<SingleNamespace> getNamespaces() {
        return namespaces;
    }

    public SingleNamespace getSingleNamespaceByName(String name) {
        return ensureInitServiceNamespace(nameToSingleNamespace.get(name));
    }

    public SingleNamespace getSingleNamespaceByURL(String namespaceUri) {
        return ensureInitServiceNamespace(uriToSingleNamespace.get(normalizeNamespaceURL(namespaceUri)));
    }
    
    public Properties getSchemaMappings() {
    	Properties props = new Properties();
    	SingleNamespace sn;
    	Iterator<?> it = nameToSingleNamespace.keySet().iterator();
		while (it.hasNext()) {
			sn = (SingleNamespace) nameToSingleNamespace.get(it.next());
			ensureInitServiceNamespace(sn);
			props.putAll(sn.getSchemaMappings());
		}
		logger.debug("Loaded schema mappings: " + props);
    	return props;
    }
    
    private SingleNamespace ensureInitServiceNamespace(SingleNamespace sn) {
        if (sn != null) {
        	sn.init();
        }

        return sn;
    }
    
    private String normalizeNamespaceURL(String uri) {
        uri = trimToNull(uri);

        if (uri != null) {
            uri = URI.create(uri).normalize().toString().replaceAll("/$", "");
        }

        return uri;
    }    
}
