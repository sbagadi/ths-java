package ths.log.logback;

import java.net.URL;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.LogbackException;

import ths.log.LogConfigurator;

public class LogbackConfigurator extends LogConfigurator {
	@Override
	protected void doConfigure(URL configFile) throws Exception {
        JoranConfigurator configurator = new JoranConfigurator();

        configurator.setContext(getLoggerContext());
        configurator.doConfigure(configFile);
	}

	@Override
	public void shutdown() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.stop();
	}
	
    private LoggerContext getLoggerContext() {
        ILoggerFactory lcObject = LoggerFactory.getILoggerFactory();

        if (!(lcObject instanceof LoggerContext)) {
            throw new LogbackException("Expected LOGBACK binding with SLF4J, but another log system has taken the place: "+ lcObject.getClass().getSimpleName());
        }

        LoggerContext lc = (LoggerContext) lcObject;
        lc.reset();

        return lc;
    }
}
