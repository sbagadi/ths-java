package ths.web.i18n;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import ths.commons.util.StringUtils;

public class MultiLanguage {
	
	public static final Locale defaultLocale = Locale.ENGLISH;
	private final HashMap<String,Locale> locales = new HashMap<String,Locale>();
	private final HashMap<String,ResourceBundle> resources = new HashMap<String,ResourceBundle>();
	
	public void init(String[][] supportLanguage) {
		Locale local = null;
		String key = null;
		
		for (int i = 0; i < supportLanguage.length; i++) {
			if (supportLanguage[i].length > 1) {
				local = new Locale(supportLanguage[i][0], supportLanguage[i][1]);
				key = supportLanguage[i][0] + "_" + supportLanguage[i][1];
			} else {
				local = new Locale(supportLanguage[i][0]);
				key = supportLanguage[i][0];
			}
			
			this.locales.put(key, local);
			this.resources.put(key, ResourceBundle.getBundle("META-INF/languages/language", local));
		}
	}
	
	public String getLanguage(String language, String key) {
		ResourceBundle rb = this.getResource(language);
		return StringUtils.trimToEmpty(rb.getString(key));
	}
	
	public String getFormatLanguage(String language, String key, Object... arguments) {
		String msg = this.getLanguage(language, key);
		if (!StringUtils.isEmpty(msg)) {
			msg = MessageFormat.format(msg, arguments);
		}
		return msg;
	}
	
	private ResourceBundle getResource(String language) {
		ResourceBundle def = this.resources.get(defaultLocale.getLanguage());
		if (StringUtils.isEmpty(language)) {
			return def;
		}
		
		ResourceBundle cur = this.resources.get(language);
		if (cur == null) cur = def;
		
		return cur;
	}
}
