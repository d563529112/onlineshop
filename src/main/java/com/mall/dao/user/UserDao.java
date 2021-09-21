package com.mall.dao.user;

import com.mall.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    public User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException;
    //修改当前用户密码
    public int updatePwd(Connection connection, int id, String password) throws SQLException;



}
