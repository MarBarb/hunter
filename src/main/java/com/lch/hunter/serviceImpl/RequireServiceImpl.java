package com.lch.hunter.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.mapper.RequireMapper;
import com.lch.hunter.service.RequireService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class RequireServiceImpl extends ServiceImpl<RequireMapper, Requires> implements RequireService {
    @Override
    public Page<Requires> getRequiresOrderByCreateTime(int pageNum, int pageSize){
        // 创建分页对象
        Page<Requires> page = new Page<>(pageNum, pageSize);

        // 创建查询条件构造器
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("createtime"); // 按照发帖时间由近到远排序
        queryWrapper.ge("endtime", LocalDateTime.now()); // 超出endtime的帖子会被剔除
        queryWrapper.eq("status", "Available");

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }

    public Page<Requires> getRequiresOrderByEndTime(int pageNum, int pageSize){
        // 创建分页对象
        Page<Requires> page = new Page<>(pageNum, pageSize);

        // 创建查询条件构造器
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("endtime"); // 按照endtime由近到远排序
        queryWrapper.ge("endtime", LocalDateTime.now()); // 超出endtime的帖子会被剔除
        queryWrapper.eq("status", "Available");

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }

    public Page<Requires> getRequiresOrderByRewardDesc(int pageNum, int pageSize){
        // 创建分页对象
        Page<Requires> page = new Page<>(pageNum, pageSize);

        // 创建查询条件构造器
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("reward"); // 按照reward从大到小排序
        queryWrapper.ge("endtime", LocalDateTime.now()); // 超出endtime的帖子会被剔除
        queryWrapper.eq("status", "Available");

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }


    public Page<Requires> getRequiresOrderByRewardAsc(int pageNum, int pageSize){
        // 创建分页对象
        Page<Requires> page = new Page<>(pageNum, pageSize);

        // 创建查询条件构造器
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("reward"); // 按照reward从小到大排序
        queryWrapper.ge("endtime", LocalDateTime.now()); // 超出endtime的帖子会被剔除
        queryWrapper.eq("status", "Available");

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }

    public Page<Requires> getRequiresByUserOrderByCreateTime(int pageNum, int pageSize, int userid) {

        Page<Requires> page = new Page<>(pageNum, pageSize);

        // 创建查询条件构造
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.orderByDesc("createtime"); // 按照发帖时间由近到远排序
        queryWrapper.ge("endtime", LocalDateTime.now()); // 超出endtime的帖子会被剔除
        queryWrapper.eq("status", "Available");

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }


    public Page<Requires> getAllRequiresByUser(int pageNum, int pageSize, int userid) {

        Page<Requires> page = new Page<>(pageNum, pageSize);

        // 创建查询条件构造
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.orderByDesc("createtime"); // 按照发帖时间由近到远排序

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }

    public Page<Requires> searchRequireByDescription(String keyword, int pageNum, int pageSize) {
        // 创建分页对象
        Page<Requires> page = new Page<>(pageNum, pageSize);

        // 创建查询条件构造器
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("createtime"); // 按照发帖时间由近到远排序

        // 添加搜索条件，模糊匹配帖子内容
        queryWrapper.like("description", keyword);

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }
}
