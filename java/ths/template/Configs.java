package ths.template;

import java.util.HashMap;
import java.util.Map;

import ths.core.config.AbstractPropertiesConfiguration;
import ths.core.exception.ConfigurationException;
import ths.core.loaders.LoaderConfiguration;

public class Configs extends AbstractPropertiesConfiguration {
	
	private LoaderConfiguration loaderConfig = new LoaderConfiguration();;
	
	@Override
	public void loadDefaultConfig() {		
		// extensions
		this.setParameter("cache", 			"ths.template.support.caches.StrongCache");
		this.setParameter("loader", 		"ths.core.loaders.ClasspathLoader");
		this.setParameter("parser", 		"ths.template.support.parsers.CommentParser");
		this.setParameter("translator", 	"ths.template.support.translators.DfaTranslator");
		this.setParameter("compiler", 		"ths.template.support.compilers.JdkCompiler");
		this.setParameter("formatter", 		"ths.template.support.formatters.MultiFormatter");
		this.setParameter("formatters", 	"ths.template.support.formatters.DateFormatter");
		this.setParameter("filter", 		"ths.template.support.filters.MultiFilter");
		this.setParameter("filters", 		"");
		this.setParameter("text.filter", 	"ths.template.support.filters.MultiTextFilter");
		this.setParameter("text.filters", 	"");
		this.setParameter("functions", 		"ths.template.support.functions.DefaultFunction");
		
		// properties	
		this.setParameter("import.packages", 		"java.util");
		this.setParameter("template.directory", 	"");
		this.setParameter("template.suffix", 		".html");
		this.setParameter("attribute.namespace", 	"");
		this.setParameter("cache.capacity", 		"0");
		this.setParameter("reloadable", 			"false");
		this.setParameter("precompiled", 			"false");
		this.setParameter("debug", 					"false");
		this.setParameter("compile.directory", 		"");
		this.setParameter("java.version", 			"1.6");
		this.setParameter("foreach.status", 		"foreach");
		this.setParameter("input.encoding", 		"UTF-8");
		this.setParameter("output.encoding", 		"UTF-8");
		this.setParameter("output.stream", 			"false");
		this.setParameter("locale", 				"en_US");
		this.setParameter("time.zone", 				"+0");
		this.setParameter("date.format", 			"yyyy-MM-dd HH:mm:ss");
		this.setParameter("number.format", 			"###,##0.###");
		this.setParameter("null.value", 			"");	
		this.setParameter("true.value", 			"true");
		this.setParameter("false.value", 			"false");
		this.setParameter("sequences", 	"Mon Tue Wed Thu Fri Sat Sun Mon," +
										"Monday Tuesday Wednesday Thursday Friday Saturday Sunday Monday," +
										"Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec Jan," +
										"January February March May June July August September October November December January");
		
	}
	
	public void load(String filename) {
		this.loadProperties(filename);
	}
	
	public LoaderConfiguration getLoaderConfiguration() {
		Map<String, String> config = new HashMap<String, String>();
		config.put("loader.input.encoding", this.getTemplateParameter("input.encoding", false));
		config.put("loader.directory", this.getTemplateParameter("template.directory", true));
		config.put("loader.suffix", this.getTemplateParameter("template.suffix", false));
		config.put("loader.loaders", "");
		
		loaderConfig.loadUserConfig(config);
		return loaderConfig;
	}
	
	
	public String getOutputStream() {
		return this.getTemplateParameter("output.stream", false);
	}
	
	public String getImportPackages() {
		return this.getTemplateParameter("import.packages", false);
	}
	
	public String getNullValue() {
		return this.getTemplateParameter("null.value", true);
	}

	public String getTrueValue() {
		return this.getTemplateParameter("true.value", false);
	}
	
	public String getFalseValue() {
		return this.getTemplateParameter("false.value", false);
	}		
	public String getOutputEncoding() {
		return this.getTemplateParameter("output.encoding", false);
	}
	
	public String getAttributeNamespace() {
		return this.getTemplateParameter("attribute.namespace", true);
	}
	
	public String getForeachStatus() {
		return this.getTemplateParameter("foreach.status", false);
	}
	
	public String getJavaVersion() {
		return this.getTemplateParameter("java.version", false);
	}
	
	public String getParsers() {
		return this.getTemplateParameter("parsers", true);
	}
	
	public String getDateFormat() {
		return this.getTemplateParameter("date.format", false);
	}
	
	public String getNumberFormat() {
		return this.getTemplateParameter("number.format", false);
	}
	
	public String getFormatters() {
		return this.getTemplateParameter("formatters", true);
	}
	
	public String getFilters() {
		return this.getTemplateParameter("filters", true);
	}
	
	public String getTextFilters() {
		return this.getTemplateParameter("text.filters", true);
	}
	
	public String getCompileDirectory() {
		return this.getTemplateParameter("compile.directory", true);
	}
	
	public String getCacheCapacity() {
		return this.getTemplateParameter("cache.capacity", false);
	}
	
	//----------------------------------
	public String getCache() {
		return this.getTemplateParameter("cache", false);
	}
	
	public String getLoader() {
		return this.getTemplateParameter("loader", false);
	}
	
	public String getParser() {
		return this.getTemplateParameter("parser", false);
	}
	
	public String getTranslator() {
		return this.getTemplateParameter("translator", false);
	}
	
	public String getCompiler() {
		return this.getTemplateParameter("compiler", false);
	}
	
	public String getTextFilter() {
		return this.getTemplateParameter("text.filter", false);
	}
	
	public String getFormatter() {
		return this.getTemplateParameter("formatter", false);
	}
	
	public String getFilter() {
		return this.getTemplateParameter("filter", false);
	}
	
	public String getFunctions() {
		return this.getTemplateParameter("functions", false);
	}
	
	public String getSequences() {
		return this.getTemplateParameter("sequences", false);
	}
	
	public String getReloadable() {
		return this.getTemplateParameter("reloadable", false);
	}
	
	public String getPrecompiled() {
		return this.getTemplateParameter("precompiled", false);
	}
	
	private String getTemplateParameter(String key, boolean hasEmpty) {
		String val = null;
		try {
			val = this.getParameter(key, hasEmpty);
		} catch (ConfigurationException e) {
			throw new IllegalArgumentException("Configuration["+ key +"] is empty.");
		} 
		return val;
	}
}
