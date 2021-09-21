package com.mall.dao.user;

import com.mall.dao.BaseDao;
import com.mall.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

    public class UserDaoImpl implements UserDao {
        //得到要登录的用户
        public User getLoginUser(Connection connection, String userCode, String userPassword) throws SQLException {
            PreparedStatement pstm = null;
            ResultSet rs = null;
            User user = null;

            if (connection!=null){
                String sql = "select * from user where userCode=?";
                Object[] params = {userCode};
                rs = BaseDao.execute(connection, pstm, rs, sql, params);
                if (rs.next()){
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserCode(rs.getString("userCode"));
                    user.setUserName(rs.getString("userName"));
                    user.setUserPassword(rs.getString("userPassword"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));

                }
                BaseDao.closeResource(null,pstm,rs);
                if (!user.getUserPassword().equals(userPassword))
                    user=null;
            }
            return user;
        }
        public int updatePwd(Connection connection, int id, String password) throws SQLException {
            PreparedStatement pstm = null;
            int execute = 0;
            if (connection!=null){
                String sql = "update user set userPassword = ? where id = ?";
                Object params[] = {password,id};
                execute = BaseDao.execute(connection, sql, params, pstm);
                BaseDao.closeResource(null,pstm,null);
            }

            return execute;
        }


    }


