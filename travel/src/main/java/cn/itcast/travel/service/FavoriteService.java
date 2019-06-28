package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;

import java.util.List;

public interface FavoriteService {
    public boolean isFavorite(String rid,int uid);

    public int findCount(int rid);

    public void add(int rid,int uid);

    public List myLove(int uid);
}
