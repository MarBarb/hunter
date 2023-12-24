package com.lch.hunter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lch.hunter.entity.Img;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.mapper.RequireMapper;
import com.lch.hunter.service.RequireService;
import com.lch.hunter.serviceImpl.RequireServiceImpl;
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
    private RequireService requireService;

    public RequireController(ImgController imgController, RequireService requireService) {
        this.imgController = imgController;
        this.requireService = requireService;
    }

    // 查询所有require
    public List<Requires> query(){
        return requireMapper.selectList(null); // 自动转换为json
    }

    // 分页按序号顺序查询require
    public IPage findByPage(@PathVariable int pageNum) {
        Page<Requires> page = new Page<>(pageNum, 10); // 每页10条
        return requireMapper.selectPage(page, null);
    }

    // 分页按创建时间由晚到早查询require(去除过期require)
    public IPage findByCreateTimeByPage(@PathVariable int pageNum){
        return requireService.getRequiresOrderByCreateTime(pageNum, 10);
    }

    // 分页按截止时间由早到晚查询require(去除过期require)
    public IPage findByEndTimeByPage(@PathVariable int pageNum){
        return requireService.getRequiresOrderByEndTime(pageNum, 10);
    }

    // 分页按reward由大到小查询require(去除过期require)
    public IPage findByRewardByDescByPage(@PathVariable int pageNum){
        return requireService.getRequiresOrderByRewardDesc(pageNum, 10);
    }

    // 分页按reward由小到大查询require(去除过期require)
    public IPage findByRewardByAscByPage(@PathVariable int pageNum){
        return requireService.getRequiresOrderByRewardAsc(pageNum, 10);
    }

    // 依据requireid查询require
    public String getRequireById(@PathVariable int id){
        Requires thisRequire = requireMapper.selectById(id);
        return thisRequire.toString(); // 自动转换为json
    }

    // 依据用户id查询其发布的所有requires(分页，按时间，去除过期)(用于其他用户查看某个用户发布过的require)
    public IPage getRequireByUserByPage(@PathVariable int userid, @PathVariable int pageNum){
        return requireService.getRequiresByUserOrderByCreateTime(pageNum, 10, userid);
    }

    // 依据用户id查询其发布的所有requires(分页，按时间，不去除过期)(用于用户查看自己发布过的require)
    public IPage getRequireByUserByPageWithOutDate(@PathVariable int userid, @PathVariable int pageNum){
        return requireService.getRequiresByUserOrderByCreateTimeWithOutDate(pageNum, 10, userid);
    }

    // 依据用户id查询其发布的所有requires(不分页，不按时间)(**此接口仅留给后端用**)
    // @GetMapping("/require/findByUser/{id}")
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
