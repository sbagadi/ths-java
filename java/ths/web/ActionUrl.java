package ths.web;

import ths.commons.util.StringUtils;

public class ActionUrl {
	
	private static final char UNDERSCORE = '_';
	private static final char URL_SPLIT = '/';
	private static final char POINT = '.';
	
	private String requestUrl;
	private String serverName;
	private String host;
	private String actionKey;	
	private String actionPrefix;
	
	public ActionUrl(String requestUrl, String serverName, String prefix) {
		this.host = "www";
		this.actionPrefix = prefix;
		this.requestUrl = StringUtils.trimToEmpty(requestUrl);
		this.serverName = StringUtils.trimToEmpty(serverName);
	}
	
	public void parse() {
		this.parseHost();
		this.parseActionKey();
	}
	
	private void parseHost() {
		if (!StringUtils.isEmpty(serverName)) {
			char ch = serverName.charAt(0);
			if (!Character.isDigit(ch)) {
				StringBuilder buffer = new StringBuilder();
				for (int i = 0; i < serverName.length(); i++) {
					ch = serverName.charAt(i);
					if (ch == POINT) break;
					buffer.append(ch);
				}
				host = buffer.toString();
			}
		}
	}
	
	private void parseActionKey() {
		boolean hasUpper = false;
	    int length = host.length() + 1 + requestUrl.length();
	    
	    StringBuilder buffer = new StringBuilder(length);
	    buffer.append(host).append(POINT);
	    for (int index = 0; index < length; index++) {
			char ch = requestUrl.charAt(index);
			if (ch == POINT) {
				break;
			} else if (Character.isWhitespace(ch)) {
				continue;
			} else if (ch == UNDERSCORE) {
				hasUpper = true;
			} else if (ch == URL_SPLIT) {
				buffer.append(POINT);
				hasUpper = true;
			} else {
				if (hasUpper) {
					buffer.append(Character.toUpperCase(ch));
					hasUpper = false;
				} else {
					buffer.append(ch);
				}
			}
	    }
	    
	    actionKey = buffer.toString();		
	}
	
	public String getActionKey() {
		return actionKey;
	}
	
	public String getActionClassName() {
		return actionPrefix + actionKey;
	}
}
