package com.lch.hunter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lch.hunter.controller.UserVerifyController;
import com.lch.hunter.entity.UserVerify;

public interface UserVerifyService extends IService<UserVerify> {
    String sendSimpleMail(String userMailAddress);
}
