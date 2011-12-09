package ths.web;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

public class ActionController {
	
	private final String actionClassNamePrefix = "web.action.";
	private final ConcurrentHashMap<String, AbstractAction> actions = new ConcurrentHashMap<String, AbstractAction>();
	private final ReentrantLock lock = new ReentrantLock();

	public ActionUrl getActionUrl(HttpServletRequest request) {
		ActionUrl ac = new ActionUrl(request.getRequestURI(), request.getServerName(), actionClassNamePrefix);
		return ac;
	}
	
	public AbstractAction getInstance(ActionUrl ac) throws Exception, ClassNotFoundException {	
		String actionKey = ac.getActionKey();
		AbstractAction obj = actions.get(actionKey);
		if (obj == null) {
			lock.lock();
			try {
				obj = actions.get(actionKey);
				if (obj == null) {
					obj = (AbstractAction)Class.forName(ac.getActionClassName()).newInstance();
					actions.put(actionKey, obj);
				}
			} finally {
				lock.unlock();
			}
		}
		return obj;
	}
}
