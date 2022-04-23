package com.example.foodcommentadmin.controller;

import com.alibaba.fastjson.JSON;
import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.RestaurantAccount;
import com.example.foodcommentadmin.pojo.RestaurantFoodEntity;
import com.example.foodcommentadmin.pojo.RestaurantInfo;
import com.example.foodcommentadmin.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 餐厅相关信息的Controller
 * @author: zhangweikun
 * @create: 2022-04-23 10:12
 **/
@RestController
@RequestMapping("/Restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/SignUp")
    public R signUp(@Validated @RequestBody RestaurantInfo restaurantInfo){
        if(restaurantService.signUp(restaurantInfo) == true){
            return R.ok();
        }
        return R.setResult(ResultCode.USERNAME_EXISTS);
    }

    @PostMapping("/Login")
    public R login(@Validated @RequestBody RestaurantAccount restaurantAccount){
        if(restaurantService.login(restaurantAccount) == true){
            return R.ok();
        }
        return R.setResult(ResultCode.USER_NOT_EXIST);
    }

    @PostMapping("/AddFood")
    public R addFood(@Validated @RequestBody List<RestaurantFoodEntity> restaurantFoods){
        String restaurantName = restaurantFoods.get(0).getRestaurantName();
        List<RestaurantFoodEntity> failedAddList = restaurantService
                .addFood(restaurantName, restaurantFoods);

        if(failedAddList.size() == 0){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_INSERT_FAILED).data(failedAddList);
    }

}

