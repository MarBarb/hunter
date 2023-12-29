package com.lch.hunter.controller;

import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;
import com.lch.hunter.mapper.UserMapper;
import com.lch.hunter.service.RequireService;
import com.lch.hunter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;
    private UserService userService;
    private RequireService requireService;

    public UserController(UserService userService, RequireService requireService, UserMapper userMapper) {
        this.userService = userService;
        this.requireService = requireService;
        this.userMapper = userMapper;
    }

    // 依据id查询user(不能返回密码)
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserByIdWithOutPasswd(id); // 返回的密码为假密码”fake_password“
    }

    // 依据id修改信息
    @PutMapping("/user/modify")
    public boolean update(int id, String username, String userdepartment, String usersemester){
        User newUserInfo = userMapper.selectById(id);
        if(newUserInfo != null){
            if(!Objects.equals(username, "")){
                newUserInfo.setUsername(username);
            }
            if(!Objects.equals(userdepartment, "")){
                newUserInfo.setUserdepartment(userdepartment);
            }
            if(!Objects.equals(usersemester, "")){
                newUserInfo.setUsersemester(usersemester);
            }
            userMapper.updateById(newUserInfo);
            return true;
        } else {
            return false; // 找不到这个用户
        }
    }

    @PutMapping("/user/changePassword")
    public boolean updatePassword(int userid, String userInputOldPassword, String userInputNewPassword){
        User user = userService.getUserByIdWithPasswd(userid);
        if(user != null && Objects.equals(user.getPassword(), userInputOldPassword)){
            user.setPassword(userInputNewPassword);
            userMapper.updateById(user);
            return true;
        } else {
            return false; // 输入的原密码不匹配or找不到这个用户
        }
    }

    // 依据id删除user(注销)
    @DeleteMapping("/user/delete/{id}")
    public boolean deleteUserById(@PathVariable int id) {
        List<Requires> list = requireService.getRequireByUser(id);
        if(!list.isEmpty()){
            list.forEach(item->
            {
                try {
                    requireService.deleteRequireById(item.getRequireid());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        userMapper.deleteById(id);
        return true;
    }
}