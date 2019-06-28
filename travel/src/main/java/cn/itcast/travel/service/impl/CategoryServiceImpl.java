package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryImpl();

    public List<Category> findAll() {


        Jedis jedis = JedisUtil.getJedis();
        //用sortedset查询
        Set<Tuple> cs = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        if(cs == null || cs.size() == 0){
            //System.out.println("查询数据库");
            list = dao.findAll();
            for (Category category : list) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
        }else{
            //System.out.println("查询缓存");
            list = new ArrayList<Category>();
            for (Tuple c : cs) {
                Category cate = new Category();
                cate.setCname(c.getElement());
                cate.setCid((int)c.getScore());
                list.add(cate);
            }
        }

        return list;
    }
}
