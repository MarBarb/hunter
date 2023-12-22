package com.lch.hunter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lch.hunter.entity.Img;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;
import com.lch.hunter.mapper.RequireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;



@RestController
public class RequireController {
    @Autowired
    private RequireMapper requireMapper;
    private ImgController imgController;

    public RequireController(ImgController imgController) {
        this.imgController = imgController;
    }

    // 查询所有require
    @GetMapping("/require")
    public List<Requires> query(){
        return requireMapper.selectList(null); // 自动转换为json
    }

    // 查询指定个数require
    @GetMapping("/require/number/{num}")
    public List<Requires> query_fornum(@PathVariable int num) {
        List<Requires> list = requireMapper.selectList(null);
        return list.subList(0, num);
    }

    // 依据requireid查询require
    @GetMapping("/require/{id}")
    public String getRequireById(@PathVariable int id){
        Requires thisRequire = requireMapper.selectById(id);
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
    @PutMapping("/require/modify")
    public String modify(Requires require){
        int indicator = requireMapper.updateById(require);
        if(indicator>0){
            return "success\n";
        }else{
            return "fail!\n";
        }
    }

    // 依据id删除
    // 删除requires时，其包含的图片会在数据库内级联删除，同时存储此require的图片的整个目录也会被删除
    @DeleteMapping("/require/{id}")
    public String deleteRequireById(@PathVariable int id) throws IOException {
        List<Img> list = imgController.getImgByRequire(id);
        if(!list.isEmpty()){
            String path = list.get(0).getImgpath();
            File file = new File(path);
            File dir = new File(file.getParent());
            deleteDirectory(dir);
        }
        requireMapper.deleteById(id);
        return "Require " + id + " 已删除";
    }
}
