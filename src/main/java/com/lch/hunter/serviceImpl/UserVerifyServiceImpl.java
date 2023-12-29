package com.lch.hunter.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lch.hunter.entity.UserVerify;
import com.lch.hunter.mapper.UserVerifyMapper;
import com.lch.hunter.service.UserVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;
import java.util.Random;

@Service
public class UserVerifyServiceImpl extends ServiceImpl<UserVerifyMapper, UserVerify> implements UserVerifyService {
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendSimpleMail(String userMailAddress){
        SimpleMailMessage message = new SimpleMailMessage();
        String verifyCode = verifyCodeGenerator();

        message.setSubject("感谢您注册赏金猎人Hunter！");
        message.setFrom("hunter_signup@163.com");
        message.setTo(userMailAddress);
        message.setSentDate(new Date());
        message.setText("您的验证码是：" + verifyCode);
        javaMailSender.send(message);
        return verifyCode;
    }

    public String verifyCodeGenerator(){
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}
