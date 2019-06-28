package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao dao = new FavoriteDaoImpl();

    public boolean isFavorite(String ridstr, int uid) {
        int rid = Integer.parseInt(ridstr);

        Favorite favorite = dao.isFavorite(rid, uid);

        if(favorite != null){
            return true;
        }

        return false;
    }

    public int findCount(int rid){
        int count = dao.findCount(rid);
        return count;
    }

    public void add(int rid,int uid){
        dao.add(rid,uid);
    }

    public List myLove(int uid) {
        List list = dao.myLove(uid);
        return list;
    }
}
