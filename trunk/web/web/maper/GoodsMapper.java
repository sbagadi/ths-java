package web.http.maper;

import java.util.List;

import web.http.dao.Goods;

public interface GoodsMapper {
	List<Goods> getGoodsByCategoryId(int categoryId);
}
