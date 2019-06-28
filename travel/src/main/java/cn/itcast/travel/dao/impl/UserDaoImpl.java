package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl implements UserDao {

    private JdbcTemplate tem = new JdbcTemplate(JDBCUtils.getDataSource());

    public void saveUser(User user) {
        //System.out.println(user);
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        tem.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),
                user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());

    }

    public User findExistUser(User user) {
        User user2 = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user2 = tem.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername());
        } catch (DataAccessException e) {

        }

        return user2;
    }

    public User findExistUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = tem.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {

        }

        return user;
    }

    public void updateStatus(User u) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        tem.update(sql,u.getUid());
    }

    public User findUserByup(User user) {
        User u = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            u = tem.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {

        }
        return u;
    }


}
