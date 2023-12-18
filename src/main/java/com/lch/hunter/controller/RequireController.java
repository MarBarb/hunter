package com.lch.hunter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;
import com.lch.hunter.mapper.RequireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequireController {
    @Autowired
    private RequireMapper requireMapper;

    // 依据requireid查询require
    @GetMapping("/require/{id}")
    public String getRequireById(@PathVariable int id){
        Requires thisRequire = requireMapper.selectById(id);
        System.out.println(id);
        return thisRequire.toString(); // 自动转换为json
    }

    // 依据用户id查询其发布的所有requires
    @GetMapping("/user/{id}/require")
    public List<Requires> getRequireByUser(@PathVariable int id){
        QueryWrapper<Requires> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", id);
        return requireMapper.selectList(queryWrapper);
    }

    // 添加require(发布悬赏)
    @PostMapping("/require")
    public String save(Requires requires){
        int indicator = requireMapper.insert(requires);
        if(indicator>0){
            return "success\n";
        }else{
            return "fail!\n";
        }
    }

    // 依据id删除
    @DeleteMapping("/require/{id}")
    public String deleteRequireById(@PathVariable int id) {
        requireMapper.deleteById(id);
        return "Require " + id + " 已删除";
    }
}
