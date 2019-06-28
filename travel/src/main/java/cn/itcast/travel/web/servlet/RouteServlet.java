package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传过来的参数--当前页数--每页显示多少数据--cid
        String nowPagestr = request.getParameter("nowPage");
        String pageSizestr = request.getParameter("pageSize");
        String cidstr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        rname = URLDecoder.decode(rname,"utf-8");
//        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        //System.out.println(rname);
        //转换为int类型.传递给service层

        //当前页
        int nowPage = 0;
        if(nowPagestr != null && nowPagestr.length() > 0){
            nowPage = Integer.parseInt(nowPagestr);
        }else{
            nowPage = 1;
        }

        //每页显示的条数
        int pageSize = 0;
        if(pageSizestr != null && pageSizestr.length() > 0){
            pageSize = Integer.parseInt(pageSizestr);
        }else{
            pageSize = 8;
        }

        //类别
        int cid = 0;
        if(cidstr != null && cidstr.length() > 0  && !"null".equals(cidstr)){
            cid = Integer.parseInt(cidstr);
        }

        PageBean<Route> pb = service.pageQuery(cid, nowPage, pageSize, rname);

        String s = writeValue(pb, response);
        response.getWriter().write(s);

    }


    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridstr = request.getParameter("rid");
        int rid = Integer.parseInt(ridstr);
        Route route = service.findOne(rid);
        String s = writeValue(route,response);
        //System.out.println(s);
        response.getWriter().write(s);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user == null){
            uid = 0;
        }else{
            uid = user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),flag);


    }


    public void findCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridstr = request.getParameter("rid");
        int rid = Integer.parseInt(ridstr);
        int count = favoriteService.findCount(rid);
        String s = writeValue(count, response);
        response.getWriter().write(s);
    }


    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ridstr = request.getParameter("rid");
        int rid = Integer.parseInt(ridstr);
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user != null){
            uid = user.getUid();
        }else {
            return;
        }
        favoriteService.add(rid,uid);
    }



}
