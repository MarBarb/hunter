package com.lch.hunter.controller;

import com.lch.hunter.entity.User;
import com.lch.hunter.entity.UserVerify;
import com.lch.hunter.mapper.UserMapper;
import com.lch.hunter.mapper.UserVerifyMapper;
import com.lch.hunter.service.RequireService;
import com.lch.hunter.service.UserService;
import com.lch.hunter.service.UserVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserVerifyController {

    @Autowired
    UserVerifyMapper userVerifyMapper;

    UserVerifyService userVerifyService;
    UserService userService;

    UserMapper userMapper;

    public UserVerifyController(UserService userService, UserVerifyService userVerifyService, UserMapper userMapper) {
        this.userService = userService;
        this.userVerifyService = userVerifyService;
        this.userMapper = userMapper;
    }

    @PostMapping("/user")
    public int sendCode(String userMailAddress){
        UserVerify userVerify = new UserVerify();
        userVerify.setVerifyid(0);
        userVerify.setMail(userMailAddress);
        String verifyCode = userVerifyService.sendSignupVerifyMail(userMailAddress);
        userVerify.setCode(verifyCode);
        userVerifyMapper.insert(userVerify);
        return userVerify.getVerifyid();
    }

//    @PostMapping("/user/verify")
//    public int verifyAndSave(User user, int verifyId, String userInputCode){
//        UserVerify userVerify = userVerifyMapper.selectById(verifyId);
//        if(userInputCode.equals(userVerify.getCode())){
//            user.setUsermail(userVerify.getMail());
//            userVerifyMapper.deleteById(verifyId);
//            return userService.saveUser(user); // 返回值为userid
//        } else {
//            return 0;
//        }
//    }

    @PostMapping("/user/verify")
    public int verifyAndSave(String username, String password, String userdepartment, String usersemester, int verifyId, String userInputCode){
        User user = new User(username, 0, password, userdepartment, usersemester, "");

        UserVerify userVerify = userVerifyMapper.selectById(verifyId);
        if(userInputCode.equals(userVerify.getCode())){
            user.setUsermail(userVerify.getMail());
            userVerifyMapper.deleteById(verifyId);
            return userService.saveUser(user); // 返回值为userid
        } else {
            return 0;
        }
    }

    // 通过邮件找回密码
    @PostMapping("/user/forgetPassword")
    public int changePasswdByMail(int userid) {
        UserVerify userVerify = new UserVerify();
        User user = userService.getUserByIdWithOutPasswd(userid);
        userVerify.setVerifyid(0);
        String verifyCode = userVerifyService.sendForgetPasswordVerifyMail(user.getUsermail());
        userVerify.setCode(verifyCode);
        userVerifyMapper.insert(userVerify);
        return userVerify.getVerifyid(); // 此接口insert的userVerify类没有usermail
    }

    @PutMapping("/user/forgetPassword/verify")
    public boolean changePasswdByMailVerifyAndSave(int userid, int verifyId, String userInputCode, String userInputNewPassword){
        UserVerify userVerify = userVerifyMapper.selectById(verifyId);
        if(userInputCode.equals(userVerify.getCode())){
            User user = userService.getUserByIdWithOutPasswd(userid);
            user.setPassword(userInputNewPassword);
            userVerifyMapper.deleteById(verifyId); // 删除验证码，验证码作废
            userMapper.updateById(user);
            return true;
        } else {
            return false;
        }
    }
}
