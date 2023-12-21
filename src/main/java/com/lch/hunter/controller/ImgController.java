package com.lch.hunter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lch.hunter.entity.Img;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.mapper.ImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.font.ImageGraphicAttribute;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class ImgController {

    @Autowired
    private ImgMapper imgMapper;

    // 直接依据图片id查询图片
    @GetMapping("/img/{id}")
    public String getImgById(@PathVariable int id){
        Img thisImg = imgMapper.selectById(id);
        System.out.println(id);
        return thisImg.getImgpath(); // 返回图片路径
    }

    // 依据requireid查询图片
    @GetMapping("/require/{id}/img")
    public List<Img> getImgByRequire(@PathVariable int id){
        QueryWrapper<Img> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("requireid", id);
        return imgMapper.selectList(queryWrapper);
    }

    // 上传图片
    // 前端需要传的参数：requireid，imgid，photo（from-data形式）。参数imgpath为空即可，由后端分自动填写
    // 图片需要随机重命名
    // 图片上传完后，浏览器输入这个路径可以访问到图片源文件："localhost/requires/requireid/filename",如"localhost/requires/3/test1.jpg"
    @PostMapping("/img")
    public String save(Img img, MultipartFile photo, HttpServletRequest request) throws IOException{
        // 图片存到 "/img_file/requireid/"
        String path = request.getServletContext().getRealPath("/img_file/requires/" + img.getRequireid() + "/");
        img.setImgpath(path + photo.getOriginalFilename());
        saveFile(photo, path);
        int indicator = imgMapper.insert(img);
        if(indicator>0){
            return img.getImgpath(); // 返回路径.
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

    private void saveFile(MultipartFile photo, String path) throws IOException {
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(path + photo.getOriginalFilename());
        photo.transferTo(file);
    }
}
