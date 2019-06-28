package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryImpl implements CategoryDao {

    private JdbcTemplate tem = new JdbcTemplate(JDBCUtils.getDataSource());

    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> list = tem.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return list;
    }
}
