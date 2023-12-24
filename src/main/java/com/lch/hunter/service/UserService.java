package com.lch.hunter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;

public interface UserService extends IService<User> {
    Page<User> searchUserByUsername(String keyword, int pageNum, int pageSize);
}
