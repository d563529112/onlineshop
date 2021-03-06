package com.mall.servlet;

import com.alibaba.fastjson.JSONArray;
import com.mall.pojo.User;
import com.mall.service.user.UserService;
import com.mall.service.user.UserServiceImpl;
import com.mall.util.Constants;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UserServlet",urlPatterns = "/user.do")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")&&method!=null){
            this.updatePwd(req,resp);
        }
//        else if (method.equals("pwdmodify")){
//            this.pwdModify(req, resp);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //修改密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp){
        //从Session里面拿ID;
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);

        String newpassword = req.getParameter("newpassword");

        System.out.println("UserServlet:"+newpassword);

        boolean flag = false;

        System.out.println(o!=null);
        System.out.println(StringUtils.isNullOrEmpty(newpassword));

        if (o!=null && newpassword!=null){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getId(), newpassword);
            if (flag){
                req.setAttribute("message","修改密码成功，请退出，使用新密码登录");
                //密码修改成功，移除当前Session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                req.setAttribute("message","密码修改失败");
                //密码修改成功，移除当前Session
            }
        }else {
            req.setAttribute("message","新密码有问题");
        }

        try {
            req.getRequestDispatcher("pwdmodifysuccess.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //验证旧密码,session中有用户的密码
//    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
//        //从Session里面拿ID;
//
//        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
//        String oldpassword = req.getParameter("oldpassword");
//        System.out.println(oldpassword);
//        //万能的Map : 结果集
//        Map<String, String> resultMap = new HashMap<String,String>();
//
//        if (o==null){ //Session失效了，session过期了
//            resultMap.put("result","sessionerror");
//        }else if (StringUtils.isNullOrEmpty(oldpassword)){ //输入的密码为空
//            resultMap.put("result","error");
//        }else {
//            String userPassword = ((User) o).getUserPassword(); //Session中用户的密码
//            if (oldpassword.equals(userPassword)){
//                resultMap.put("result","true");
//            }else {
//                resultMap.put("result","false");
//            }
//        }
//
//
//        try {
//            resp.setContentType("application/json");
//            PrintWriter writer = resp.getWriter();
//            //JSONArray 阿里巴巴的JSON工具类, 转换格式
//            /*
//            resultMap = ["result","sessionerror","result","error"]
//            Json格式 = {key：value}
//             */
//            writer.write(JSONArray.toJSONString(resultMap));
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}

