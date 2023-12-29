package com.lch.hunter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    Page<User> searchUserByUsername(String keyword, int pageNum, int pageSize);

    User getUserByIdForPasswd(int userid);

    boolean saveUser(User user);

    List<User> getUserByMailAddress(String userMailAddress);
}
