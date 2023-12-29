package com.lch.hunter.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;
import com.lch.hunter.entity.UserVerify;
import com.lch.hunter.mapper.UserMapper;
import com.lch.hunter.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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


    public List<User> getUserByMailAddress(String userMailAddress){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("usermail", userMailAddress);
        return userMapper.selectList(queryWrapper);
    }


    public int saveUser(User user){
        userMapper.insert(user);
        return user.getUserid();
    }

    // 依据id查询user(返回密码)
    public User getUserByIdWithOutPasswd(int id) {
        User user = userMapper.selectById(id);
        user.setPassword("fake_password");
        return user; // 自动转换为json
    }

    // 依据id查询user(返回密码)
    public User getUserByIdWithPasswd(int id) {
        return userMapper.selectById(id); // 自动转换为json
    }

    // 查询所有用户(**此函数仅供后端&测试使用**)
    public List<User> findAll(){
        return userMapper.selectList(null); // 自动转换为json
    }
}
