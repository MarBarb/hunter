package com.lch.hunter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lch.hunter.entity.Requires;

public interface RequireService extends IService<Requires> {
    Page<Requires> getRequiresOrderByCreateTime(int pageNum, int pageSize);
}
