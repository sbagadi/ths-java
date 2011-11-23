package ths.template;

import ths.core.config.AbstractPropertiesConfiguration;

public class Configuration extends AbstractPropertiesConfiguration {

	@Override
	public void loadDefaultConfig() {
		//this.setParameter(TemplateConfigurable.CONF_PARA_TEMLATE_ENCODING, "utf-8");
		//this.setParameter(TemplateConfigurable.CONF_PARA_TEMLATE_DIRECTORY, "");
		//this.setParameter(TemplateConfigurable.CONF_PARA_TEMLATE_SUFFIX, ".html");
	}
	
	public void load(String filename) {
		this.loadProperties(filename);
	}
}
