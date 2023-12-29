package com.lch.hunter.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.UserVerify;
import com.lch.hunter.mapper.UserVerifyMapper;
import com.lch.hunter.service.UserVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserVerifyServiceImpl extends ServiceImpl<UserVerifyMapper, UserVerify> implements UserVerifyService {
    @Autowired
    private JavaMailSender javaMailSender;

    private UserVerifyMapper userVerifyMapper;

    public UserVerifyServiceImpl(UserVerifyMapper userVerifyMapper) {
        this.userVerifyMapper = userVerifyMapper;
    }

    public String sendSignupVerifyMail(String userMailAddress){
        SimpleMailMessage message = new SimpleMailMessage();
        String verifyCode = verifyCodeGenerator();

        message.setSubject("要帮助，秒应答。感谢您注册帮应社区！");
        message.setFrom("hunter_signup@163.com");
        message.setTo(userMailAddress);
        message.setSentDate(new Date());
        message.setText(
                "您的验证码是：" + verifyCode + "\n\n" +
                        "如果你没有请求此代码，可放心忽略这封电子邮件。别人可能错误地键入了你的电子邮件地址。\n\n" +
                        "谢谢！\n帮应社区账户团队\n"
        );
        javaMailSender.send(message);
        return verifyCode;
    }

    public String sendForgetPasswordVerifyMail(String userMailAddress){
        SimpleMailMessage message = new SimpleMailMessage();
        String verifyCode = verifyCodeGenerator();

        message.setSubject("帮应社区：您正在通过邮箱验证修改密码。");
        message.setFrom("hunter_signup@163.com");
        message.setTo(userMailAddress);
        message.setSentDate(new Date());
        message.setText(
                "您的验证码是：" + verifyCode + "\n\n" +
                        "如果你没有请求此代码，可放心忽略这封电子邮件。别人可能错误地键入了你的电子邮件地址。\n\n" +
                        "谢谢！\n帮应社区账户团队\n"
        );
        javaMailSender.send(message);
        return verifyCode;
    }

    public String verifyCodeGenerator(){
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

}
