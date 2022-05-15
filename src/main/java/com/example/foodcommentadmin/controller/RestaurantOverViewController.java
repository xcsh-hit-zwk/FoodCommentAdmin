package com.example.foodcommentadmin.controller;

import com.alibaba.fastjson.JSON;
import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.FoodOverView;
import com.example.foodcommentadmin.pojo.LabelOverView;
import com.example.foodcommentadmin.pojo.RestaurantOverView;
import com.example.foodcommentadmin.pojo.SearchInfo;
import com.example.foodcommentadmin.service.RestaurantOverViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 餐厅简略信息Controller
 * @author: zhangweikun
 * @create: 2022-04-17 17:47
 **/
@RestController
@CrossOrigin
@RequestMapping("/RestaurantOverView")
@Slf4j
public class RestaurantOverViewController {

    @Autowired
    private RestaurantOverViewService restaurantOverViewService;

    @PostMapping("/GetRestaurantOverView")
    public R getRestaurantOverView(@Validated @RequestBody SearchInfo searchInfo){
        String searchWay = searchInfo.getSearchWay();
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();
        switch (searchWay){
            case "城市":
                restaurantOverViewList = restaurantOverViewService.cityRestaurantOverView(searchInfo.getInfo());
                break;
            case "街区":
                restaurantOverViewList = restaurantOverViewService.blockRestaurantOverView(searchInfo.getInfo());
                break;
            case "招牌菜":
                restaurantOverViewList = restaurantOverViewService.foodRestaurantOverView(searchInfo.getInfo());
                break;
            case "餐厅类型":
                restaurantOverViewList = restaurantOverViewService.tagRestaurantOverView(searchInfo.getInfo());
                break;
            case "名称":
                restaurantOverViewList = restaurantOverViewService.nameRestaurantOverView(searchInfo.getInfo());
                break;
            default:
                return R.setResult(ResultCode.WRONG_SEARCH);
        }
        if (restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.WRONG_SEARCH);
    }

    // todo 这些暂时都不需要，打算改成从Controller开始分流，客户端负责传Tag+name
    @PostMapping("/GetCity")
    public R getCityRestaurantOverView(@Validated @RequestBody String jsonCity){
        String city = JSON.parseObject(jsonCity).get("city").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .cityRestaurantOverView(city);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }

    @PostMapping("/GetByTag")
    public R getTagRestaurantOverView(@Validated @RequestBody String JsonTag){
        String tag = JSON.parseObject(JsonTag).get("tag").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .tagRestaurantOverView(tag);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }

    @PostMapping("/GetByFood")
    public R getFoodRestaurantOverView(@Validated @RequestBody String JsonFood){
        String food = JSON.parseObject(JsonFood).get("food").toString();
        List<RestaurantOverView> restaurantOverViewList = restaurantOverViewService
                .foodRestaurantOverView(food);
        if(restaurantOverViewList != null){
            return R.ok().data(restaurantOverViewList);
        }
        return R.setResult(ResultCode.PARAM_ERROR);
    }

    @PostMapping("/GetByBlock")
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
