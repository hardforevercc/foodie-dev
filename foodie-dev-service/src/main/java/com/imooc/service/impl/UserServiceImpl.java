package com.imooc.service.impl;

import com.immoc.mapper.UsersMapper;
import com.imooc.enums.Sex;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    private static final String FACE_ID = "123";

    @Transactional(propagation=Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExits(String username) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("username",username);
        Users result = usersMapper.selectOneByExample(userExample);

        return null == result?false:true;
    }
    @Transactional(propagation=Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBo) {
        String userId = sid.nextShort();
        Users user = new Users();
        user.setUsername(userBo.getUsername());
        try{
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        }catch (Exception e){
            e.printStackTrace();
        }
        //默认用户昵称同用户名
        user.setNickname(userBo.getUsername());
        user.setCreatedTime(new Date());
        user.setId(userId);
        user.setFace(FACE_ID);
        user.setBirthday(DateUtil.stringToDate("1991-01-01"));
        user.setSex(Sex.secret.type);
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);
        return user;
    }
    @Transactional(propagation=Propagation.SUPPORTS)
    @Override
    public Users queryUsersForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        try{
            userCriteria.andEqualTo("username",username).andEqualTo("password",MD5Utils.getMD5Str(password));
        }catch (Exception e){
            e.printStackTrace();
        }
        return usersMapper.selectOneByExample(userExample);
    }
}
