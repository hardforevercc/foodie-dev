package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value="注册登陆",tags = {"用于注册登陆的相关接口"})
@RestController
@RequestMapping("/passport")
public class PasswordController {
    @Autowired
    private UserService userService;
    @ApiOperation(value="用户名是否存在",notes="用户名是否存在",httpMethod="GET")
    @GetMapping("usernameIsExist")
    public IMOOCJSONResult usernameIsExits(@RequestParam String username){
        //判断入参是否为空
        if(StringUtils.isBlank(username)){
            return IMOOCJSONResult.errorMsg("查询用户名不能为空");
        }
        //判断用户名是否存在
        boolean result = userService.queryUsernameIsExits(username);
        if(result) {
            return IMOOCJSONResult.errorMsg("查询用户名已存在");
        }
        return IMOOCJSONResult.ok();
    }
    @ApiOperation(value="用户注册",notes="用户注册",httpMethod="POST")
    @PostMapping("/regist")
    public IMOOCJSONResult usernameRegist(@RequestBody UserBO userBO,HttpServletRequest request,
                                          HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        //1.判断用户名和密码是否为空
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)
            || StringUtils.isBlank(confirmPassword)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }
        //2.判断用户名是否已经存在
        boolean isExits = userService.queryUsernameIsExits(username);
        if(isExits) {
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        //3.判断密码长度是否小于6位
        if(password.length() < 6){
            return IMOOCJSONResult.errorMsg("输入密码长度不能小于6位");
        }
        //4.判断密码和确认密码是否一致
        if(!password.equals(confirmPassword)){
            return IMOOCJSONResult.errorMsg("两次输入密码不一致");
        }
        //5.实现注册
        Users user = userService.createUser(userBO);
        CookieUtils.setCookie(request,response,"user",JsonUtils.objectToJson(user),true);
        return IMOOCJSONResult.ok(user);
    }

    @ApiOperation(value="用户登陆",notes="用户登陆",httpMethod="POST")
    @PostMapping("/login")
    public IMOOCJSONResult usernameLogin(@RequestBody UserBO userBO, HttpServletRequest request,
                                         HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        //1.判断用户名和密码是否为空
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        //5.实现登陆
        Users user = userService.queryUsersForLogin(username,password);
        if(null == user){
            return IMOOCJSONResult.errorMsg("用户名或密码不匹配");
        }
        user = setNullProperty(user);
        //true 对cookie加密
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(user),true);
        return IMOOCJSONResult.ok(user);
    }

    @ApiOperation(value="用户退出登陆",notes="用户退出登陆",httpMethod="POST")
    @PostMapping("/logout")
    public IMOOCJSONResult usernameLoginout(@RequestParam String userId, HttpServletRequest request,
                                         HttpServletResponse response){

        CookieUtils.deleteCookie(request,response,"user");
        return IMOOCJSONResult.ok();
    }

    private  Users setNullProperty(Users user){
        user.setPassword(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setMobile(null);
        user.setBirthday(null);
        return user;
    }

}
