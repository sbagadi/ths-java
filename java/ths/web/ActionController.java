package ths.web;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpServletRequest;

public class ActionController {
	
	private final String actionClassNamePrefix = "web.action.";
	private final ConcurrentHashMap<String, AbstractAction> actions = new ConcurrentHashMap<String, AbstractAction>();
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	public ActionUrl getActionUrl(HttpServletRequest request) {
		ActionUrl ac = new ActionUrl(request.getRequestURI(), request.getServerName(), actionClassNamePrefix);
		return ac;
	}
	
	public AbstractAction getInstance(ActionUrl ac) throws Exception, ClassNotFoundException {	
		rwl.readLock().lock();
		String actionKey = ac.getActionKey();
		AbstractAction obj = actions.get(actionKey);
		if (null == obj) {
			rwl.readLock().unlock();
			rwl.writeLock().lock();
			obj = (AbstractAction)Class.forName(ac.getActionClassName()).newInstance();
			actions.put(actionKey, obj);
			rwl.readLock().lock();
			rwl.writeLock().unlock(); 
		}
		rwl.readLock().unlock();

		return obj;
	}
}
