package cn.itcast.travel.web.servlet.no;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
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
import java.util.Map;

//@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(checkcode == null || !checkcode.equalsIgnoreCase(check)){
            ResultInfo rs = new ResultInfo();
            rs.setFlag(false);
            rs.setErrorMsg("验证码输入错误");
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(rs);
            response.setContentType("application/json;charset=utf-8");
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

        UserService service = new UserServiceImpl();
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
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(rs);
        response.getWriter().write(s);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
