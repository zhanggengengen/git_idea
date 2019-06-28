package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate tem = new JdbcTemplate(JDBCUtils.getDataSource());

    public int findTotalCount(int cid,String rname) {
        /*String sql = "select count(*) from tab_route where cid = ?";
        Integer total = tem.queryForObject(sql, Integer.class, cid);
        return total;*/
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0 && !"null".equals(cid)){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            sb.append(" and rname like ?  ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        Integer total = tem.queryForObject(sql, Integer.class, params.toArray());
        return total;
    }

    public List<Route> pageQuery(int cid,int start,int pageSize,String rname) {
        /*String sql = "select * from tab_route where cid = ? limit ? , ?";
        List<Route> list = tem.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, start, pageSize);*/

        String sql = "select * from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0 &&  !"null".equals(cid)){
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        params.add(start);
        params.add(pageSize);
        sb.append(" limit ? ,? ");
        sql = sb.toString();
        List<Route> list = tem.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());

        return list;
    }

    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        Route route = tem.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }
}
