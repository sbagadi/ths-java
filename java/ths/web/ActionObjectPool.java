package ths.web;

import java.util.concurrent.ConcurrentHashMap;

public class ActionObjectPool {
	
	private final ConcurrentHashMap<String, AbstractHttpAction> actions = new ConcurrentHashMap<String, AbstractHttpAction>();
	
    protected AbstractHttpAction get(String action) {
        return actions.get(action);
    }
    
    protected void put(String action, AbstractHttpAction value) {
    	actions.put(action, value);
    }

    protected void remove(String action) {
    	actions.remove(action);
    }
    
    public AbstractHttpAction getInstance(String className) throws Exception, ClassNotFoundException {
    	String ac = ActionController.getActionKey(className);
    	
    	AbstractHttpAction obj = this.get(ac);
    	if (null == obj) {
    		obj = ActionController.createActionObject(className);
    		this.put(ac, obj);
    		System.out.println("put obj:"+ ac);
    	}
    	
		return obj;
    }
}
