package ths.template.base;

import ths.core.Configurable;
import ths.core.Configuration;
import ths.core.exception.ConfigurationException;

public class TemplateConfigurable implements Configurable {
	
	public static final String CONF_PARA_TEMLATE_ENCODING = "template.encoding";
	public static final String CONF_PARA_TEMLATE_DIRECTORY = "template.directory";
	public static final String CONF_PARA_TEMLATE_SUFFIX = "template.suffix";
	
	private String inputEncoding;
	
	public String getInputEncoding() {
		return this.inputEncoding;
	}
	
	@Override
	public void configure(Configuration config) throws ConfigurationException  {
		this.inputEncoding = config.getParameter(CONF_PARA_TEMLATE_ENCODING, false);
		
		
	}

}
