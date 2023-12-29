package com.lch.hunter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    Page<User> searchUserByUsername(String keyword, int pageNum, int pageSize);

    User getUserByIdWithPasswd(int userid);

    int saveUser(User user);

    List<User> getUserByMailAddress(String userMailAddress);

    User getUserByIdWithOutPasswd(int id);
}
