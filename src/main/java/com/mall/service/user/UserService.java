package com.mall.service.user;

import com.mall.pojo.User;

public interface UserService {
        //用户登录
        public User login(String userCode, String password);
        //根据用户ID修改密码
        public boolean updatePwd(int id, String pwd);

}
