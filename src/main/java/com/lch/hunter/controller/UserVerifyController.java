package com.lch.hunter.controller;

import com.lch.hunter.entity.User;
import com.lch.hunter.entity.UserVerify;
import com.lch.hunter.mapper.UserVerifyMapper;
import com.lch.hunter.service.RequireService;
import com.lch.hunter.service.UserService;
import com.lch.hunter.service.UserVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserVerifyController {

    @Autowired
    UserVerifyMapper userVerifyMapper;

    UserVerifyService userVerifyService;
    UserService userService;

    public UserVerifyController(UserService userService, UserVerifyService userVerifyService) {
        this.userService = userService;
        this.userVerifyService = userVerifyService;
    }

    @PostMapping("/user")
    public int sendCode(String userMailAddress){
        UserVerify userVerify = new UserVerify();
        userVerify.setVerifyid(0);
        userVerify.setMail(userMailAddress);
        String verifyCode = userVerifyService.sendSimpleMail(userMailAddress);
        userVerify.setCode(verifyCode);
        userVerifyMapper.insert(userVerify);
        return userVerify.getVerifyid();
    }

    @PostMapping("/user/verify")
    public boolean verifyAndSave(User user, int verifyId, String userInputCode){
        UserVerify userVerify = userVerifyMapper.selectById(verifyId);
        if(userInputCode.equals(userVerify.getCode())){
            user.setUsermail(userVerify.getMail());
            return userService.saveUser(user); // 返回值为boolean
        } else {
            return false;
        }
    }


    // ***仅供测试开发用，记得删除***
    @GetMapping("/user/getv/{id}")
    public UserVerify getUV(@PathVariable int id){
        return userVerifyMapper.selectById(id);
    }
}
