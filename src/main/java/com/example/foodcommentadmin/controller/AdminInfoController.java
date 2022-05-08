package com.example.foodcommentadmin.controller;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.FoodOverView;
import com.example.foodcommentadmin.pojo.LabelOverView;
import com.example.foodcommentadmin.pojo.RestaurantOverView;
import com.example.foodcommentadmin.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhangweikun
 * @create: 2022-05-07 18:26
 */
@RestController
@CrossOrigin
@RequestMapping("/AdminInfo")
public class AdminInfoController {

    @Autowired
    private AdminInfoService adminInfoService;

    // 给管理员用的接口
    @GetMapping("/GetTotalRestaurantOverView")
    public R getTotalRestaurantOverView(){
        List<RestaurantOverView> restaurantOverViewList = adminInfoService
                .totalRestaurantOverView();
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

    @GetMapping("/GetTotalFoodOverView")
    public R getTotalFoodOverView(){
        List<FoodOverView> foodOverViewList = adminInfoService.totalFoodOverView();
        if(foodOverViewList != null){
            return R.ok().data(foodOverViewList);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

    @GetMapping("/GetTotalLabelOverView")
    public R getTotalLabelOverView(){
        List<LabelOverView> labelOverViewList = adminInfoService.totalLabelOverView();
        if(labelOverViewList != null){
            return R.ok().data(labelOverViewList);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

    @PostMapping("/AddRestaurant")
    public R addRestaurant(@Validated @RequestBody RestaurantOverView restaurantOverView){
        Boolean success = adminInfoService.addRestaurant(restaurantOverView);
        if(success == true){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_INSERT_FAILED);
    }

    @PostMapping("/DeleteRestaurant")
    public R deleteRestaurant(@Validated @RequestBody RestaurantOverView restaurantOverView){
        Boolean success = adminInfoService.deleteRestaurant(restaurantOverView);
        if(success == true){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_DELETE_FAILED);
    }

}