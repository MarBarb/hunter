package com.lch.hunter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.mapper.RequireMapper;
import com.lch.hunter.service.RequireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    private RequireService requireService;

    @GetMapping("/search/require/{pageNum}") // localhost/search/require/1?keyword=博雅楼
    public Page<Requires> searchPosts(@RequestParam String keyword,
                                      @PathVariable int pageNum) {
        return requireService.searchRequireByDescription(keyword, pageNum, 10);
    }
}
