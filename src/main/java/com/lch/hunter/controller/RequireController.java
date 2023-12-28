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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    @GetMapping("/require")
    public List<Requires> query(){
        return requireMapper.selectList(null); // 自动转换为json
    }

    // 分页按序号顺序查询require(**注意：此接口不筛选且仅按数据库顺序排序，不要使用**)
    @GetMapping("/require/findByPage")
    public IPage findByPage(
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        Page<Requires> page = new Page<>(pageNum, pageSize);
        return requireMapper.selectPage(page, null);
    }

    // 分页按创建时间由晚到早查询require(去除过期require和status为‘Finished’的require)
    @GetMapping("/require/findByCreateTime")
    public IPage findByCreateTimeByPage(
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return requireService.getRequiresOrderByCreateTime(pageNum, pageSize);
    }

    // 分页按截止时间由早到晚查询require(去除过期require和status为‘Finished’的require)
    @GetMapping("/require/findByEndTime")
    public IPage findByEndTimeByPage(
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return requireService.getRequiresOrderByEndTime(pageNum, pageSize);
    }

    // 分页按reward由大到小查询require(去除过期require和status为‘Finished’的require)
    @GetMapping("/require/findByRewardDesc")
    public IPage findByRewardByDescByPage(
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return requireService.getRequiresOrderByRewardDesc(pageNum, pageSize);
    }

    // 分页按reward由小到大查询require(去除过期require和status为‘Finished’的require)
    @GetMapping("/require/findByRewardAsc")
    public IPage findByRewardByAscByPage(
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return requireService.getRequiresOrderByRewardAsc(pageNum, pageSize);
    }

    // 依据requireid查询require
    @GetMapping("/require/{id}")
    public String getRequireById(@PathVariable int id){
        Requires thisRequire = requireMapper.selectById(id);
        return thisRequire.toString(); // 自动转换为json
    }

    // 依据用户id查询其发布的所有requires(分页，按时间，去除过期，去除Finished)(用于其他用户查看某个用户发布过的require)
    @GetMapping("/require/findByUser/{userid}")
    public IPage getRequireByUserByPage(@PathVariable int userid,
                                        @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return requireService.getRequiresByUserOrderByCreateTime(pageNum, pageSize, userid);
    }

    // 依据用户id查询其发布的所有requires(分页，按时间，不去除过期，不去除Finished)(用于用户查看自己发布过的require)
    @GetMapping("/require/findByUserSelf/{userid}")
    public IPage getAllRequireByUserByPage(@PathVariable int userid,
                                           @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        return requireService.getAllRequiresByUser(pageNum, pageSize, userid);
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
    public int save(Requires requires){
        requireMapper.insert(requires);
        return requires.getRequireid();
    }

    // 添加require(发布悬赏)(endtime不传)
    @PostMapping("/requireByLater/{later}")
    public int saveByLater(Requires requires, @PathVariable int later){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        requires.setCreatetime(now.format(formatter));
        requires.setEndtime(now.plusMinutes(later).format(formatter));
        requireMapper.insert(requires);
        return requires.getRequireid();
    }

    // 此处不可缺少参数
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
