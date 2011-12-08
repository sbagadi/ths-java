package web.maper;

import java.util.List;

import web.dao.Goods;

public interface GoodsMapper {
	List<Goods> getGoodsByCategoryId(int categoryId);
}
