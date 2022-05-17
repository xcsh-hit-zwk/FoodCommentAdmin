package com.example.foodcommentadmin.controller;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.LikeComment;
import com.example.foodcommentadmin.pojo.LikeFood;
import com.example.foodcommentadmin.pojo.RestaurantDetail;
import com.example.foodcommentadmin.pojo.SearchInfo;
import com.example.foodcommentadmin.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/AddFoodLike")
    public R addFoodLike(@Validated @RequestBody LikeFood likeFood){
        String username = likeFood.getUsername();
        String restaurantName = likeFood.getRestaurantName();
        String foodName = likeFood.getFoodName();
        if (username == null || restaurantName == null || foodName == null){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        Boolean answer = restaurantService
                .addFoodLike(username, restaurantName + "-" + foodName, restaurantName);
        if(answer == true){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_INSERT_FAILED);
    }

    @PostMapping("/CancelFoodLike")
    public R cancelFoodLike(@Validated @RequestBody LikeFood likeFood){
        String username = likeFood.getUsername();
        String restaurantName = likeFood.getRestaurantName();
        String foodName = likeFood.getFoodName();
        if (username == null || restaurantName == null || foodName == null){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        Boolean answer = restaurantService
                .cancelFoodLike(username, restaurantName + "-" + foodName, restaurantName);
        if(answer == true){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_INSERT_FAILED);
    }

    @PostMapping("/AddCommentLike")
    public R addCommentLike(@Validated @RequestBody LikeComment likeComment){
        String commentId = likeComment.getCommentId();
        String username = likeComment.getUsername();
        String restaurantName = likeComment.getRestaurantName();
        if (commentId == null || username == null || restaurantName == null){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        Boolean success = restaurantService.addCommentLike(commentId, username, restaurantName);
        if (success == true){
            return R.ok();
        }
        return R.setResult(ResultCode.SOME_INSERT_FAILED);
    }

    @PostMapping("/CancelCommentLike")
    public R cancelCommentLike(@Validated @RequestBody LikeComment likeComment){
        String commentId = likeComment.getCommentId();
        String username = likeComment.getUsername();
        String restaurantName = likeComment.getRestaurantName();
        if (commentId == null || username == null || restaurantName == null){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }

        Boolean success = restaurantService.cancelCommentLike(commentId, username, restaurantName);
        if (success == true){
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

