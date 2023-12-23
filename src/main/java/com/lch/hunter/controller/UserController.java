package com.lch.hunter.controller;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;
import com.lch.hunter.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    private RequireController requireController;

    public UserController(RequireController requireController) {
        this.requireController = requireController;
    }

    // 查询所有用户
    @GetMapping("/user")
    public List<User> query(){
        return userMapper.selectList(null); // 自动转换为json
    }

    // 添加用户(注册)
    @PostMapping("/user")
    public User save(User user) {
        userMapper.insert(user);
        return user;
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

    // 依据id查询user
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable int id){
        return userMapper.selectById(id); // 自动转换为json
    }

    // 依据id修改信息(暂时无法修改密码)
    @PutMapping("/user/{id}")
    public String update(@PathVariable int id, String username, String userdepartment, String usersemester){
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
        return newUserInfo.toString();
    }

    // 依据id删除user(注销)
    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable int id) {
        List<Requires> list = requireController.getRequireByUser(id);
        if(!list.isEmpty()){
            list.forEach(item->
            {
                try {
                    requireController.deleteRequireById(item.getRequireid());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        userMapper.deleteById(id);
        return "User " + id + " 已删除";
    }
}