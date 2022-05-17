package com.example.foodcommentadmin.controller;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.RestaurantDetail;
import com.example.foodcommentadmin.pojo.RestaurantFoodEntity;
import com.example.foodcommentadmin.pojo.SearchInfo;
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
@CrossOrigin
@RequestMapping("/RestaurantDetail")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // 借用一下SearchInfo
    // searchWay: "餐厅详情", info = "餐厅名"
    @PostMapping("/GetRestaurantDetail")
    public R getRestaurantDetail(@Validated @RequestBody SearchInfo searchInfo){
        String restaurantName = searchInfo.getInfo();
        if (searchInfo.getSearchWay() == null || !searchInfo.getSearchWay().equals("餐厅详情")){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }
        if (restaurantName == null || restaurantName.equals("")){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        RestaurantDetail restaurantDetail = restaurantService.getRestaurantDetail(restaurantName);
        if (restaurantDetail != null){
            return R.ok().data(restaurantDetail);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

    // 借用一下SearchInfo
    // searchWay: "餐厅名", searchInfo: "招牌菜名"
    @PostMapping("/AddFoodLike")
    public R addFoodLike(@Validated @RequestBody SearchInfo searchInfo){
        String restaurantName = searchInfo.getSearchWay();
        String foodName = searchInfo.getInfo();
        if (restaurantName == null){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }
        if (foodName == null){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        Boolean answer = restaurantService.addFoodLike(restaurantName + "-" + foodName);
        if(answer == true){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_INSERT_FAILED);
    }

    // 借用一下SearchInfo
    // searchWay: "餐厅名", searchInfo: "招牌菜名"
    @PostMapping("/CancelFoodLike")
    public R cancelFoodLike(@Validated @RequestBody SearchInfo searchInfo){
        String restaurantName = searchInfo.getSearchWay();
        String foodName = searchInfo.getInfo();
        if (restaurantName == null){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }
        if (foodName == null){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        Boolean answer = restaurantService.cancelFoodLike(restaurantName + "-" + foodName);
        if(answer == true){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_INSERT_FAILED);
    }

//    @PostMapping("/AddFood")
//    public R addFood(@Validated @RequestBody List<RestaurantFoodEntity> restaurantFoods){
//        String restaurantName = restaurantFoods.get(0).getRestaurantName();
//        List<RestaurantFoodEntity> failedAddList = restaurantService
//                .addFood(restaurantName, restaurantFoods);
//
//        if(failedAddList.size() == 0){
//            return R.ok();
//        }
//        return R.setResult(ResultCode.SOME_INSERT_FAILED).data(failedAddList);
//    }

}

