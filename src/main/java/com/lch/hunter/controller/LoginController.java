package com.lch.hunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class LoginController {
    @Autowired
    private UserController userController;

    public LoginController(UserController userController) {
        this.userController = userController;
    }

    @PostMapping("/login")
    public Boolean login(int userid, String password, HttpServletRequest request) {
        if(Objects.equals(userController.getUserById(userid).getPassword(), password)) {
            // 登录成功，将用户信息存储到Session中
            HttpSession session = request.getSession();
            session.setAttribute("user", userid);
            return true;
        } else {
            return false;
        }
    }
}