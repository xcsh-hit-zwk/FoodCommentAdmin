package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.AdminMapper;
import com.example.foodcommentadmin.pojo.AdminAccount;
import com.example.foodcommentadmin.pojo.AdminEntity;
import com.example.foodcommentadmin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:
 * @author: zhangweikun
 * @create: 2022-4-28 9:18
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登录方法，通过比对已有账户名称来确认是否可以登录
     * @param adminAccount
     * @return 登陆结果
     */
    @Override
    public Boolean login(AdminAccount adminAccount) {
        String adminUsername = adminAccount.getUsername();
        QueryWrapper<AdminEntity> adminEntityQueryWrapper = new QueryWrapper<>();
        adminEntityQueryWrapper.eq("admin_username", adminUsername)
                .eq("has_delete", false);
        AdminEntity adminEntity = adminMapper.selectOne(adminEntityQueryWrapper);
        if(adminEntity != null){
            log.info("开始验证账号");
            String adminPassword = adminAccount.getPassword();
            if(adminPassword.equals(adminEntity.getAdminPassword())){
                log.info("开始验证密码");
                return true;
            }
        }
        return false;
    }
}
