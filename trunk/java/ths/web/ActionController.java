package ths.web;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

public class ActionController {
	
	private final ConcurrentHashMap<String, AbstractHttpAction> actions = new ConcurrentHashMap<String, AbstractHttpAction>();
	
	public static String getActionClassName(HttpServletRequest request) {
		String url = request.getRequestURI();
		String domain = request.getServerName();
	}
	
	public static String getActionKey(String className) {
		
	}
	
	public static AbstractHttpAction createActionObject(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (AbstractHttpAction)Class.forName(className).newInstance();
	}	
}
