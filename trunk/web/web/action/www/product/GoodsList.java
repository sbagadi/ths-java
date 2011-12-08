package web.http.action.www;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ths.core.loaders.AbstractLoader;
import ths.template.Engine;
import ths.web.AbstractHttpAction;
import web.http.dao.Goods;
import web.http.maper.GoodsMapper;

public class GoodsList extends AbstractHttpAction {

	@Override
	protected void service() throws Exception {
		String config = this.servletContext.getRealPath("/WEB-INF/template.properties");
		Engine engine = Engine.getEngine("/"+ config);
		((AbstractLoader)engine.getLoader()).setConfigDirectory(this.servletContext.getRealPath("/"));
		
		GoodsMapper goodsMapper = (GoodsMapper)this.getDatabaseMapper(GoodsMapper.class);
		List<Goods> goodsList = goodsMapper.getGoodsByCategoryId(1);
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("GoodsList", goodsList);
		engine.getTemplate("/themes/en/goods-detail.html").render(context, out);
		
	}
}
