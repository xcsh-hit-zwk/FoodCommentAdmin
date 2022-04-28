package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.AdminAccount;

/**
 * description:
 * @author: zhangweikun
 * @create: 2022-4-28 9:18
 */
public interface AdminService {

    /**
     * 餐厅登录相关接口
     */
    // 餐厅登录接口
    Boolean login(AdminAccount adminAccount);
}
