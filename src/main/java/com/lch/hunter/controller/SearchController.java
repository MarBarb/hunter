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

    @GetMapping("/search/require/{pageNum}") // localhost/search/require/1?keyword=博雅楼
    public Page<Requires> searchRequire(@RequestParam String keyword,
                                      @PathVariable int pageNum) {
        return requireService.searchRequireByDescription(keyword, pageNum, 1);
    }

    @GetMapping("/search/user/{pageNum}") // localhost/search/user/1?keyword=Admin_1
    public Page<User> searchUser(@RequestParam String keyword,
                                 @PathVariable int pageNum) {
        return userService.searchUserByUsername(keyword, pageNum, 1);
    }
}
