package com.lch.hunter.controller;

import com.lch.hunter.service.UserService;
import com.lch.hunter.service.UserVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public Boolean login(String IdOrMailAddress, String password, HttpServletRequest request) {
        int userid = 0;
        if(isNumeric(IdOrMailAddress)){
            userid = Integer.parseInt(IdOrMailAddress);
        } else {
            userid = userService.getUserByMailAddress(IdOrMailAddress).get(0).getUserid();
        }

        if(userid == 0){
            return false;
        }

        if(Objects.equals(userService.getUserByIdForPasswd(userid).getPassword(), password)) {
            // 登录成功，将用户信息存储到Session中
            HttpSession session = request.getSession();
            session.setAttribute("user", userid);
            return true;
        } else {
            return false; // 用户不存在or用户名与密码不匹配
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    @PostMapping("/logout")
    public Boolean logout(int userid, HttpServletRequest request) {
        if(userService.getUserByIdForPasswd(userid) != null) {
            HttpSession session = request.getSession();
            if((int)session.getAttribute("user") == userid){
                session.removeAttribute("user"); // 从session中清除
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}