package ths.template;

import ths.core.config.AbstractPropertiesConfiguration;

public class TemplateConfiguration extends AbstractPropertiesConfiguration {

	@Override
	public void loadDefaultConfig() {
		//this.setParameter(TemplateConfigurable.CONF_PARA_TEMLATE_ENCODING, "utf-8");
		//this.setParameter(TemplateConfigurable.CONF_PARA_TEMLATE_DIRECTORY, "");
		//this.setParameter(TemplateConfigurable.CONF_PARA_TEMLATE_SUFFIX, ".html");
	}
	
	public void load(String filename) {
		this.loadProperties(filename);
	}
	
	public String getOutputStream() {
		return "output.stream";
	}
	
	public String getImportPackages() {
		return "import.packages";
	}
	
	public String getNullValue() {
		return "null.value";
	}

	public String getTrueValue() {
		return "true.value";
	}
	
	public String getFalseValue() {
		return "false.value";
	}		
	public String getOutputEncoding() {
		return "output.encoding";
	}
	
	public String getAttributeNamespace() {
		return "attribute.namespace";
	}
	
	public String getForeachStatus() {
		return "foreach.status";
	}
	
	public String getJavaVersion() {
		return "java.version";
	}
	
	public String getParsers() {
		return "parsers";
	}
	
	public String getDateFormat() {
		return "date.format";
	}
	
	public String getNumberFormat() {
		return "number.format";
	}
	
	public String getFormatters() {
		return "formatters";
	}
	
	public String getFilters() {
		return "filters";
	}
	
	public String getTextFilters() {
		return "text.filters";
	}
	
	public String getCompileDirectory() {
		return "compile.directory";
	}
	
	public String getCacheCapacity() {
		return "cache.capacity";
	}
	
	//----------------------------------
	public String getCache() {
		return "cache";
	}
	
	public String getLoader() {
		return "loader";
	}
	
	public String getParser() {
		return "parser";
	}
	
	public String getTranslator() {
		return "translator";
	}
	
	public String getCompiler() {
		return "compiler";
	}
	
	public String getTextFilter() {
		return "text.filter";
	}
	
	public String getFormatter() {
		return "formatter";
	}
	
	public String getFilter() {
		return "filter";
	}
	
	public String getFunctions() {
		return "functions";
	}
	
	public String getSequences() {
		return "sequences";
	}
	
	public String getReloadable() {
		return "reloadable";
	}
	
	public String getPrecompiled() {
		return "precompiled";
	}
	
	/*
	public String get() {
		return "";
	}
	public String get() {
		return "";
	}
	public String get() {
		return "";
	}*/
}
