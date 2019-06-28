package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    public void saveUser(User user);

    public User findExistUser(User user);

    public User findExistUserByCode(String code);

    public void updateStatus(User u);

    public User findUserByup(User user);
}
