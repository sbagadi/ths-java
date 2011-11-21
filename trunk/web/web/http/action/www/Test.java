package web.http.action.www;

import ths.service.checker.CheckerService;
import ths.web.AbstractHttpAction;

public class Test extends AbstractHttpAction {

	@Override
	protected void service() throws Exception {
		StringBuffer sb = new StringBuffer();
		CheckerService cs = (CheckerService) this.getServiceInstance(CheckerService.DEFAULT_BEAN_NAME);
		
		sb.append("中文测试！");
		sb.append("name:"+ cs.getName());
		sb.append("age:"+ cs.getAge());
		
		out.print(sb.toString());
	}
}
