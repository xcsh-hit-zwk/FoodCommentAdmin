package com.example.foodcommentadmin.controller;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.Account;
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

    @GetMapping("/Login")
    public R login(@Validated @RequestBody Account account){
        if(userService.login(account)){
            return R.ok();
        }
        return R.setResult(ResultCode.USER_NOT_EXIST);
    }

//    @GetMapping("/SignUp")
//    public R signUp(){
//
//    }
}
