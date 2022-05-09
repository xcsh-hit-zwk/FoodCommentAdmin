package com.example.foodcommentadmin.controller;

import com.alibaba.fastjson.JSON;
import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.AdminInfoService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @PostMapping("/GetUpdateRestaurantId")
    public R getUpdateRestaurantId(@Validated @RequestBody RestaurantOverView restaurantOverView){
        String restaurantId = adminInfoService.getUpdateRestaurantId(restaurantOverView);
        if(restaurantId != null){
            return R.ok().data(restaurantId);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

    @PostMapping("/UpdateRestaurant")
    public R updateRestaurant(@Validated @RequestBody UpdateRestaurantOverView updateRestaurantOverView){
        Boolean success = adminInfoService
                .updateRestaurant(updateRestaurantOverView.getRestaurantId(), updateRestaurantOverView.getRestaurantOverView());
        if(success == true){
            return R.ok();
        }
        else {
            return R.setResult(ResultCode.SOME_UPDATE_FAILED);
        }
    }

    @PostMapping("/GetUpdateFoodId")
    public R getUpdateFoodId(@Validated @RequestBody FoodOverView foodOverView){
        String foodId = adminInfoService.getUpdateFoodId(foodOverView);
        if(foodId != null){
            return R.ok().data(foodId);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

    @PostMapping("/UpdateFood")
    public R updateFood(@Validated @RequestBody UpdateFoodOverView updateFoodOverView){
        log.info("接收报文:", updateFoodOverView);
        Boolean success = adminInfoService.updateFood(updateFoodOverView.getFoodId(),
                updateFoodOverView.getFoodOverView());
        if(success == true){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_UPDATE_FAILED);
    }


}
