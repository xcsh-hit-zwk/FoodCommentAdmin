package com.example.foodcommentadmin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.RegisterAccount;
import com.example.foodcommentadmin.pojo.User;
import com.example.foodcommentadmin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/User")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/Login")
    public R login(@Validated @RequestBody Account account){
        log.info(account.getUsername());
        log.info(account.getPassword());
        if(userService.login(account)){
            return R.ok();
        }
        return R.setResult(ResultCode.USER_NOT_EXIST);
    }

    @PostMapping("/SignUp")
    public R signUp(@Validated @RequestBody RegisterAccount registerAccount){

        if(userService.signUp(registerAccount)){
            return R.ok();
        }
        return R.setResult(ResultCode.USERNAME_EXISTS);
    }
}
