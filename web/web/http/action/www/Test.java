package web.http.action.www;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

//import ths.service.checker.CheckerService;
import ths.template.Engine;
import ths.core.loaders.AbstractLoader;
import ths.web.AbstractHttpAction;
import web.http.model.Book;
import web.http.model.User;
import web.http.model.Goods;

import com.alibaba.druid.pool.DruidDataSource;

public class Test extends AbstractHttpAction {

	@Override
	protected void service() throws Exception {
		//StringBuffer sb = new StringBuffer();
		//CheckerService cs = (CheckerService) this.getServiceInstance(CheckerService.DEFAULT_BEAN_NAME);
		
		//sb.append("中文测试！");
		//sb.append("name:"+ cs.getName());
		//sb.append("age:"+ cs.getAge());
		//out.print(sb.toString());
		
		DruidDataSource ds = (DruidDataSource)this.getServiceInstance("dataSource-mysql");
		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();
        
		String config = this.servletContext.getRealPath("/WEB-INF/template.properties");
		Engine engine = Engine.getEngine("/"+ config);
		((AbstractLoader)engine.getLoader()).setConfigDirectory(this.servletContext.getRealPath("/"));
		
		int size = 2;
		Random random = new Random();
		Book[] books = new Book[size];
		for (int i = 0; i < size; i ++) {
			books[i] = new Book(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), new Date(), random.nextInt(100) + 10, random.nextInt(60) + 30);
		}
		
		int index = 0;
		Goods[] goodsList = new Goods[2];
        ResultSet rs = stmt.executeQuery("SELECT * FROM web_goods");
        while (rs.next()) {
        	goodsList[index] = new Goods(rs.getInt(1), rs.getString(2), rs.getFloat(3));
        	index++;
        }
        rs.close();
        
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("user", new User("田海深", "admin"));
		context.put("books", books);
		context.put("GoodsList", goodsList);
		
        stmt.close();
        conn.close();
        
		engine.getTemplate("/themes/en/goods-detail.html").render(context, out);
		
	}
}
