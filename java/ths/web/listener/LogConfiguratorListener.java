package ths.web.listener;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ths.log.LogConfigurator;

public class LogConfiguratorListener implements ServletContextListener {
	private static final String LOG_SYSTEM = "logSystem";
    private static final String LOG_Config_Location = "logConfigLocation";
    private LogConfigurator logConfigurator;
    
    @Override 
    public void contextInitialized(ServletContextEvent event) {
    	ServletContext servletContext = event.getServletContext();
    	String logSystem = servletContext.getInitParameter(LOG_SYSTEM);
    	String logConfigLocation = servletContext.getInitParameter(LOG_Config_Location);
    	
    	if (logSystem == null) logSystem = getDefaultLogSystem();
    	if (logConfigLocation == null) logConfigLocation = getDefaultConfigLocation(logSystem);
    	
    	logConfigurator = LogConfigurator.getConfigurator(logSystem);
    	servletContext.log(String.format("Initializing %s system", logSystem));
    	
        URL logConfigurationResource;
        try {
            logConfigurationResource = servletContext.getResource(logConfigLocation);
        } catch (MalformedURLException e) {
            logConfigurationResource = null;
        }
        
        if (logConfigurationResource == null) {
            servletContext.log(String.format("Could not find %s configuration file \"%s\" in webapp context.  Using default configurations.", logSystem, logConfigLocation));
            logConfigurator.useDefaultConfigure();
        } else {
            logConfigurator.useAppointedConfigure(logConfigurationResource);
        }  
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		servletContext.log("Shutting down log system");
		logConfigurator.shutdown();
    }
    
    private String getDefaultLogSystem() {
    	return "logback";
    }
    
    private String getDefaultConfigLocation(String logSystem) {
    	return "/WEB-INF/" + logSystem + ".xml";
    }
}
