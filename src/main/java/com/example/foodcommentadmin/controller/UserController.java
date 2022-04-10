package com.example.foodcommentadmin.controller;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.User;
import com.example.foodcommentadmin.service.UserService;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/Login")
    public R login(@Validated @RequestBody Account account){
        if(userService.login(account)){
            return R.ok();
        }
        return R.setResult(ResultCode.USER_NOT_EXIST);
    }

    @PostMapping("/SignUp")
    public R signUp(@Validated @RequestBody User user){
        if(userService.signUp(user)){
            return R.ok();
        }
        return R.setResult(ResultCode.USERNAME_EXISTS);
    }
}
