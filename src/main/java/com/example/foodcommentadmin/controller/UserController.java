package com.example.foodcommentadmin.controller;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/GetUser")
    public R getUser(@Validated @RequestBody Account account){
        User user = userService.getUser(account);
        if (user != null){
            return R.ok().data(user);
        }
        return R.setResult(ResultCode.USER_NOT_EXIST);
    }

    @PostMapping("/UpdateUserInfo")
    public R updateUserInfo(@Validated @RequestBody UserInfo userInfo){
        Boolean success = userService.updateUserInfo(userInfo);
        if (success != false){
            return R.ok();
        }
        return R.setResult(ResultCode.USER_NOT_EXIST);
    }

    // searchWay = "ModifyUserComment", info = "%username"
    @PostMapping("/ModifyUserComment")
    public R ModifyUserComment(@Validated @RequestBody SearchInfo searchInfo){
        String searchWay = searchInfo.getSearchWay();
        String info = searchInfo.getInfo();
        if (!searchWay.equals("ModifyUserComment")){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        List<UserInfoComment> userInfoCommentList = userService.getUserComment(info);
        if (userInfoCommentList != null){
            return R.ok().data(userInfoCommentList);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

    // searchWay = "GetUserImage", info = "%username"
    @PostMapping("/GetUserImage")
    public R getUserImage(@Validated @RequestBody SearchInfo searchInfo){
        if (!searchInfo.getSearchWay().equals("GetUserImage")){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        String userImage = userService.getUserImage(searchInfo.getInfo());
        if (userImage != null){
            return R.ok().data(userImage);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }
}
