package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            //2.调用service完成激活
            //UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='"+request.getContextPath()+"/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }


    public void exit (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public void findOne (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        /*ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(user);
        response.setContentType("application/json;charset=utf-8");*/
        String s = writeValue(user, response);
        response.getWriter().write(s);
    }


    public void login (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode == null || !checkcode.equalsIgnoreCase(check)){
            ResultInfo rs = new ResultInfo();
            rs.setFlag(false);
            rs.setErrorMsg("验证码输入错误");
            /*ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(rs);
            response.setContentType("application/json;charset=utf-8");*/
            String s = writeValue(rs,response);
            response.getWriter().write(s);
            //System.out.println(rs);
            return;
        }


        User user = new User();
        Map<String, String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //UserService service = new UserServiceImpl();
        User u = service.findUserByup(user);
        ResultInfo rs = new ResultInfo();
        //System.out.println(rs);
        if (u == null) {
            rs.setFlag(false);
            rs.setErrorMsg("用户名或者密码输入错误");
        } else if ("n".equalsIgnoreCase(u.getStatus())) {
            rs.setFlag(false);
            rs.setErrorMsg("该用户尚未激活,请先激活再来操作");
        } else {
            request.getSession().setAttribute("user",u);
            rs.setFlag(true);
        }
       /* response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(rs);*/
        String s = writeValue(rs, response);
        response.getWriter().write(s);
    }


    public void regist (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        String checkcode = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if(checkcode == null || !checkcode.equalsIgnoreCase(check)){
            ResultInfo rs = new ResultInfo();
            rs.setFlag(false);
            rs.setErrorMsg("验证码输入错误");
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(rs);
            response.setContentType("application/json;charset=utf-8");*/
            String json = writeValue(rs, response);
            response.getWriter().write(json);
            return;
        }

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);

        ResultInfo rs = new ResultInfo();
        if(flag){
            rs.setFlag(true);
        }else{
            rs.setFlag(false);
            rs.setErrorMsg("该用户名已被占用");
        }
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(rs);
        response.setContentType("application/json;charset=utf-8");*/
        String json = writeValue(rs, response);
        response.getWriter().write(json);
    }


    public void myLove (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);
        if(user != null){
            int uid = user.getUid();
            List list = favoriteService.myLove(uid);
            System.out.println(list);
        }else {
            return;
        }
    }



}
