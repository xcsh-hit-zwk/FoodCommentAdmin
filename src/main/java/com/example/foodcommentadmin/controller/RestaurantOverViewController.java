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
    public R getTotalRestaurantOverView(@Validated @RequestBody String JSONcity){
        String city = JSON.parseObject(JSONcity).get("city").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .totalRestaurantOverView(city);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }
}
