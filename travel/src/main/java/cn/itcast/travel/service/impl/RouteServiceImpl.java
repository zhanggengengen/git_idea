package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/*private int totalCount;//总条数
private int nowPage;//当前页数
private int pageSize;//每页显示的条数
private int totalPage;//总页数
private List<T> list;//每页显示的数据集合*/

public class RouteServiceImpl implements RouteService {

    private RouteDao dao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();

    public PageBean<Route> pageQuery(int cid, int nowPage, int pageSize,String rname) {

        PageBean<Route> pb = new PageBean<Route>();
        //总条数
        int totalCount = dao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //数据集合
        int start = (nowPage - 1) * pageSize;
        List<Route> list = dao.pageQuery(cid,start,pageSize,rname);
        pb.setList(list);
        //当前页数
        pb.setNowPage(nowPage);
        //每页条数
        pb.setPageSize(pageSize);
        //总页数
        int totalPage = (int) Math.ceil(totalCount / pageSize);
        pb.setTotalPage(totalPage);
        return pb;
    }

    public Route findOne(int rid) {
        Route route = dao.findOne(rid);
        int id = route.getRid();
        List<RouteImg> list = routeImgDao.findById(id);
        route.setRouteImgList(list);
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        return route;
    }
}
