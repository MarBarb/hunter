package com.lch.hunter.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;
import com.lch.hunter.mapper.UserMapper;
import com.lch.hunter.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Page<User> searchUserByUsername(String keyword, int pageNum, int pageSize) {
        // 创建分页对象
        Page<User> page = new Page<>(pageNum, pageSize);

        // 创建查询条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("userid"); // 按照id由大到小排序

        // 添加搜索条件，模糊匹配
        queryWrapper.like("username", keyword);

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }
}
