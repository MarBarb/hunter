package com.lch.hunter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lch.hunter.entity.Requires;
import com.lch.hunter.entity.User;
import com.lch.hunter.service.RequireService;
import com.lch.hunter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    private RequireService requireService;
    private UserService userService;

    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search/require") // localhost/search/require/1?keyword=博雅楼
    public Page<Requires> searchRequire(@RequestParam String keyword,
                                        @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return requireService.searchRequireByDescription(keyword, pageNum, pageSize);
    }

    @GetMapping("/search/user") // localhost/search/user/1?keyword=Admin_1
    public Page<User> searchUser(@RequestParam String keyword,
                                 @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return userService.searchUserByUsername(keyword, pageNum, pageSize);
    }
}
