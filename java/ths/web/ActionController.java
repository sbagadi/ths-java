package ths.web;

import javax.servlet.http.HttpServletRequest;

public class ActionController {
	
	public static String getActionClassName(HttpServletRequest request) {
		String url = request.getRequestURI();
		String domain = request.getServerName();
		String host = "www";
		if(domain != null) {
			host = domain.substring(0, domain.indexOf("."));
		}
		
		int index = url.lastIndexOf('/')+1;
		StringBuffer name = new StringBuffer("web.http.action.");
		name.append(host);
		name.append(url.substring(0, index).replaceAll("/", "."));
		
		String action = url.substring(index, url.lastIndexOf('.'));
		if (action.indexOf('_') > 0) {
			String[] words = action.split("_");
			for(int i=0; i<words.length; i++) {
				//name.append(words[i].replaceFirst(words[i].substring(0, 1), words[i].substring(0, 1).toUpperCase()));
				name.append(Character.toUpperCase(words[i].charAt(0)));
				name.append(words[i].substring(1));
			}
		} else {
			//name.append(action.replaceFirst(action.substring(0, 1), action.substring(0, 1).toUpperCase()));
			name.append(Character.toUpperCase(action.charAt(0)));
			name.append(action.substring(1));			
		}

		return name.toString();
	}
	
	public static AbstractHttpAction createActionObject(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (AbstractHttpAction)Class.forName(className).newInstance();
	}	
}
