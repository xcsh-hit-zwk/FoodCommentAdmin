package com.example.foodcommentadmin.controller;

import com.alibaba.fastjson.JSON;
import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.RestaurantOverView;
import com.example.foodcommentadmin.service.RestaurantOverViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 餐厅简略信息Controller
 * @author: zhangweikun
 * @create: 2022-04-17 17:47
 **/
@RestController
@CrossOrigin
@RequestMapping("/RestaurantOverView")
public class RestaurantOverViewController {

    @Autowired
    private RestaurantOverViewService restaurantOverViewService;

    @PostMapping("/getTotal")
    public R getTotalRestaurantOverView(@Validated @RequestBody String JsonCity){
        String city = JSON.parseObject(JsonCity).get("city").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .totalRestaurantOverView(city);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }

    @PostMapping("/getByName")
    public R getNameRestaurantOverView(@Validated @RequestBody String JsonName){
        String name = JSON.parseObject(JsonName).get("name").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .nameRestaurantOverView(name);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }

    @PostMapping("/getByTag")
    public R getTagRestaurantOverView(@Validated @RequestBody String JsonTag){
        String tag = JSON.parseObject(JsonTag).get("tag").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .tagRestaurantOverView(tag);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }

    @PostMapping("/getByFood")
    public R getFoodRestaurantOverView(@Validated @RequestBody String JsonFood){
        String food = JSON.parseObject(JsonFood).get("food").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .foodRestaurantOverView(food);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }

    @PostMapping("/getByBlock")
    public R getBlockRestaurantOverView(@Validated @RequestBody String JsonBlock){
        String block = JSON.parseObject(JsonBlock).get("block").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .blockRestaurantOverView(block);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }
}
