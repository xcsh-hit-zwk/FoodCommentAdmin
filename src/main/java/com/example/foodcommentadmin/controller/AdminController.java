package com.example.foodcommentadmin.controller;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.AdminAccount;
import com.example.foodcommentadmin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 * @author: zhangweikun
 * @create: 2022-4-28 9:19
 */
@RestController
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/Login")
    public R login(@Validated @RequestBody AdminAccount adminAccount){
        if(adminService.login(adminAccount) == true){
            return R.ok();
        }
        return R.setResult(ResultCode.USER_NOT_EXIST);
    }
}
