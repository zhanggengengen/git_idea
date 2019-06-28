package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

public interface FavoriteDao {
    public Favorite isFavorite(int rid, int uid);

    public int findCount(int rid);

    public void add(int rid,int uid);

    public List myLove(int uid);
}
