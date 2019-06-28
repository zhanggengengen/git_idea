package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate tem = new JdbcTemplate(JDBCUtils.getDataSource());

    public Favorite isFavorite(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = tem.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    public int findCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        Integer count = tem.queryForObject(sql, Integer.class, rid);
        return count;
    }

    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        tem.update(sql,rid,new Date(),uid);
    }

    public List myLove(int uid) {
        String sql = "select rid from tab_favorite where uid = ?";
        List<Integer> list = tem.query(sql, new BeanPropertyRowMapper<Integer>(Integer.class), uid);
        System.out.println(list.toArray());
        return list;
    }
}
