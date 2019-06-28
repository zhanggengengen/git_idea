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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String check = request.getParameter("check");
        String checkcode = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if(checkcode == null || !checkcode.equalsIgnoreCase(check)){
            ResultInfo rs = new ResultInfo();
            rs.setFlag(false);
            rs.setErrorMsg("验证码输入错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(rs);
            response.setContentType("application/json;charset=utf-8");
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

        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);

        ResultInfo rs = new ResultInfo();
        if(flag){
            rs.setFlag(true);
        }else{
            rs.setFlag(false);
            rs.setErrorMsg("该用户名已被占用");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(rs);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
