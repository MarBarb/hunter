package com.lch.hunter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lch.hunter.entity.Requires;

public interface RequireService extends IService<Requires> {
    Page<Requires> getRequiresOrderByCreateTime(int pageNum, int pageSize);
    Page<Requires> getRequiresByUserOrderByCreateTime(int pageNum, int pageSize, int userid);

    Page<Requires> searchRequireByDescription(String keyword, int pageNum, int pageSize);

    IPage getRequiresOrderByEndTime(int pageNum, int i);

    IPage getRequiresOrderByRewardDesc(int pageNum, int i);

    IPage getRequiresOrderByRewardAsc(int pageNum, int i);

    IPage getAllRequiresByUser(int pageNum, int i, int userid);
}
