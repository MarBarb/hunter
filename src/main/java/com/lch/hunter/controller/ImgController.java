package com.lch.hunter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lch.hunter.entity.Img;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.mapper.ImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.font.ImageGraphicAttribute;
import java.util.List;

@RestController
public class ImgController {

    @Autowired
    private ImgMapper imgMapper;

    // 直接依据图片id查询图片
    @GetMapping("/Img/{id}")
    public String getImgById(@PathVariable int id){
        Img thisImg = imgMapper.selectById(id);
        System.out.println(id);
        return thisImg.toString(); // 其中包含图片路径
    }

    // 依据requireid查询图片
    @GetMapping("/require/{id}/img")
    public List<Img> getImgByRequire(@PathVariable int id){
        QueryWrapper<Img> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("requireid", id);
        return imgMapper.selectList(queryWrapper);
    }

    // 上传图片(requireid和图片对应的逻辑由前端实现)
    @PostMapping("/img")
    public String save(Img img){
        int indicator = imgMapper.insert(img);
        if(indicator>0){
            return "success\n";
        }else{
            return "fail!\n";
        }
    }

    // 依据id删除图片(目前仅仅是在数据库内删除，后续补充实际删除图片文件)
    // 删除requires时，其包含的图片会在**数据库**内级联删除
    @DeleteMapping("/img/{id}")
    public String deleteImgById(@PathVariable int id) {
        imgMapper.deleteById(id);
        return "Img " + id + " 已删除";
    }
}
