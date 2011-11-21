package ths.web.context;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;

import ths.spring.resolver.ServicesBeanDefinitionReaderProcessor;
import ths.web.Constants;

public class ServicesWebApplicationContext extends AbstractRefreshableWebApplicationContext {
	
	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

		beanDefinitionReader.setResourceLoader(this);
		new ServicesBeanDefinitionReaderProcessor(beanDefinitionReader).addServicesSupport();
		
		loadBeanDefinitions(beanDefinitionReader);		
	}
	
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws IOException {
		String[] configLocations = getConfigLocations();
		if (configLocations != null) {
			for (String configLocation : configLocations) {
				reader.loadBeanDefinitions(configLocation);
			}
		}
	}
	
	@Override
	protected String[] getDefaultConfigLocations() {
		return new String[] {Constants.THS_CONFIGURATION_LOCATION};
	}	
}
