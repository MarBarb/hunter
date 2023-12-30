package com.lch.hunter.serviceImpl;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lch.hunter.controller.ImgController;
import com.lch.hunter.entity.Img;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.mapper.RequireMapper;
import com.lch.hunter.service.RequireService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;

@Service
public class RequireServiceImpl extends ServiceImpl<RequireMapper, Requires> implements RequireService {

    RequireMapper requireMapper;
    ImgController imgController;

    public RequireServiceImpl(RequireMapper requireMapper, ImgController imgController) {
        this.requireMapper = requireMapper;
        this.imgController = imgController;
    }

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
        queryWrapper.ge("endtime", LocalDateTime.now()); // 超出endtime的帖子会被剔除
        queryWrapper.eq("status", "Available");

        // 添加搜索条件，模糊匹配帖子内容
        queryWrapper.like("description", keyword);

        // 调用 MyBatis-Plus 分页查询方法
        return baseMapper.selectPage(page, queryWrapper);
    }

    public List<Requires> getRequireByUser(@PathVariable int id){
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", id);
        return requireMapper.selectList(queryWrapper);
    }

    public boolean deleteRequireById(@PathVariable int id) throws IOException {
        List<Img> list = imgController.getImgByRequire(id);
        if(!list.isEmpty()) {
            String realPath = list.get(0).getImgrealpath();
            File file = new File(realPath);
            deleteDirectory(file);
        }
        requireMapper.deleteById(id);
        return true;
    }
}
