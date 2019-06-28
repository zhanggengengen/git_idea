package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    public boolean regist(User user) {

        User u = dao.findExistUser(user);
        if (u == null) {
            //设置没激活前的状态和激活码
            //System.out.println(user);
            //System.out.println("---");
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());

            //System.out.println(user);
            //不存在-->flag = true
            dao.saveUser(user);

            //跳转到激活页面,验证激活码
            String content = "<a href='http://localhost:8080/user/active?code="+user.getCode()+"'>点击激活</a>";
            MailUtils.sendMail(user.getEmail(),content,"激活邮件");


            return true;
        }
        return false;
    }

    public boolean active(String code) {

        User u = dao.findExistUserByCode(code);

        if(u != null){
            dao.updateStatus(u);
            return true;
        }
        return false;
    }

    public User findUserByup(User user) {
        User u = dao.findUserByup(user);
        return u;
    }
}
