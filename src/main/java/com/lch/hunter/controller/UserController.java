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


    @PutMapping("/user/modify")
    public String modify(User user){
        int indicator = userMapper.updateById(user);
        if(indicator>0){
            return "success\n";
        }else{
            return "fail!\n";
        }
    }

    // 依据id查询user(不能返回密码)
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable int id) {
        User user = userMapper.selectById(id);
        user.setPassword("password"); // 密码不能返回给前端
        return user; // 自动转换为json
    }

    // 依据id修改信息(暂时无法修改密码)
    @PutMapping("/user/{id}")
    public User update(@PathVariable int id, String username, String userdepartment, String usersemester){
        User newUserInfo = userMapper.selectById(id);
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
        return newUserInfo;
    }

    // 依据id删除user(注销)
    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable int id) {
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
        return "User " + id + " 已删除";
    }
}