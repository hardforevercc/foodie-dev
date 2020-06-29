package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

public interface UserService {
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExits(String username);

    /**
     * 创建用户
     * 前端传过来的对象统一以BO为结尾
     * @param userBo
     * @return
     */
    public Users createUser(UserBO userBo);

    /**
     * 检索用户名和密码是否匹配并登陆
     * @param username
     * @param password
     * @return
     */
    public Users queryUsersForLogin(String username,String password);

}
