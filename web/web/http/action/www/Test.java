package web.http.action.www;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

//import ths.service.checker.CheckerService;
import ths.template.Engine;
import ths.core.loaders.AbstractLoader;
import ths.web.AbstractHttpAction;
import web.http.model.Book;
import web.http.model.User;

public class Test extends AbstractHttpAction {

	@Override
	protected void service() throws Exception {
		//StringBuffer sb = new StringBuffer();
		//CheckerService cs = (CheckerService) this.getServiceInstance(CheckerService.DEFAULT_BEAN_NAME);
		
		//sb.append("中文测试！");
		//sb.append("name:"+ cs.getName());
		//sb.append("age:"+ cs.getAge());
		//out.print(sb.toString());
		
		String config = this.servletContext.getRealPath("/WEB-INF/template.properties");
		Engine engine = Engine.getEngine("/"+ config);
		((AbstractLoader)engine.getLoader()).setConfigDirectory(this.servletContext.getRealPath("/"));
		
		int size = 2;
		Random random = new Random();
		Book[] books = new Book[size];
		for (int i = 0; i < size; i ++) {
		books[i] = new Book(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), new Date(), random.nextInt(100) + 10, random.nextInt(60) + 30);
		}
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", new User("田海深", "admin"));
		context.put("books", books);		

		engine.getTemplate("/themes/en/goods-detail.html").render(context, out);
		
	}
}
