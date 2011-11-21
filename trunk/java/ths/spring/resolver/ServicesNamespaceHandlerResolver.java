package ths.spring.resolver;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerResolver;

import ths.spring.ServicesNamespace;
import ths.spring.SingleNamespace;

public class ServicesNamespaceHandlerResolver implements NamespaceHandlerResolver {
	
	private final NamespaceHandlerResolver defaultResolver;
	private final ServicesNamespace sns;
	
	public ServicesNamespaceHandlerResolver(NamespaceHandlerResolver defaultResolver, ServicesNamespace sns) {
		this.defaultResolver = defaultResolver;
		this.sns = sns;
	}
	
	@Override
	public NamespaceHandler resolve(String namespaceUri) {
		SingleNamespace sn = sns.getSingleNamespaceByURL(namespaceUri);
        if (sn != null) {
            return sn.getNamespaceHandler();
        } else if (defaultResolver != null) {
            return defaultResolver.resolve(namespaceUri);
        } else {
            return null;
        }
	}
}
