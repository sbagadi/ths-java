package ths.log;

import java.net.URL;

public abstract class LogConfigurator {
	private String logSystem;
	
    protected abstract void doConfigure(URL configFile) throws Exception;
    public abstract void shutdown();
    
    public final String getLogSystem() {
        return logSystem;
    }
    
    public static LogConfigurator getConfigurator(String logSystem) {
    	String providerClassName = "";
    	if (logSystem.equals("logback")) {
    		providerClassName = "ths.log.logback.LogbackConfigurator";
    	}
    	
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		
		Class<?> providerClass;
		try {
			providerClass = cl.loadClass(providerClassName);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Could not find LogConfigurator for " + logSystem, e);
		}
		if (!LogConfigurator.class.isAssignableFrom(providerClass)) {
			throw new IllegalArgumentException(logSystem + " class " + providerClassName + " is not a sub-class of " + LogConfigurator.class.getName());
		}
		
		LogConfigurator configurator;
		try {
			configurator = (LogConfigurator) providerClass.newInstance();
		} catch (Throwable e) {
			throw new IllegalArgumentException("Could not create instance of class " + providerClassName + " for " + logSystem, e);
		}
		
		configurator.logSystem = logSystem;
		return configurator;
    }
    
    public final void useDefaultConfigure() {
    	URL configFile = getDefaultConfigFile();
    	useAppointedConfigure(configFile);
    }
    
    public final void useAppointedConfigure(URL configFile) {
        StringBuilder buf = new StringBuilder();
        buf.append("INFO: configuring \"").append(logSystem).append("\" using ").append(configFile).append("\n");
        log(buf.toString());

        try {
            doConfigure(configFile);
        } catch (Exception e) {
            log("WARN: Failed to configure " + logSystem + " using " + configFile, e);
        }
    }
    
    public final URL getDefaultConfigFile() {
        return getClass().getClassLoader().getResource(getClass().getPackage().getName().replace('.', '/') + "/" + getDefaultConfigFileName());
    }
    
    protected String getDefaultConfigFileName() {
        return logSystem + "-default.xml";
    }
    
    protected static void log(String msg) {
        log(msg, null);
    }

    protected static void log(String msg, Throwable e) {
        System.out.flush();
        System.err.println(msg);

        if (e != null) {
            e.printStackTrace();
        }

        System.err.flush();
    }    
}
