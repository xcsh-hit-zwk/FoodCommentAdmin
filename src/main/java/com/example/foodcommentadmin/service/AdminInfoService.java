package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.FoodOverView;
import com.example.foodcommentadmin.pojo.LabelOverView;
import com.example.foodcommentadmin.pojo.RestaurantOverView;

import java.util.List;

/**
 * @author: zhangweikun
 * @create: 2022-05-07 18:23
 */
public interface AdminInfoService {

    // 全部餐厅，用于返回给管理员
    List<RestaurantOverView> totalRestaurantOverView();

    // 全部招牌菜，用于返回给管理员
    List<FoodOverView> totalFoodOverView();

    // 全部标签，用于返回给管理员
    List<LabelOverView> totalLabelOverView();

    // 管理员添加餐厅
    Boolean addRestaurant(RestaurantOverView restaurantOverView);

    // 管理员删除餐厅
    Boolean deleteRestaurant(RestaurantOverView restaurantOverView);

    // 获取需要更新的餐厅的Id
    String getUpdateRestaurantId(RestaurantOverView restaurantOverView);
    // 管理员更新餐厅
    Boolean updateRestaurant(String restaurantId, RestaurantOverView restaurantOverView);

    // 管理员添加招牌菜
    Boolean addFood(FoodOverView foodOverView);

    // 管理员删除招牌菜
    Boolean deleteFood(FoodOverView foodOverView);

    // 管理员获取需要更新招牌菜的id
    String getUpdateFoodId(FoodOverView foodOverView);
    // 管理员更新招牌菜
    Boolean updateFood(String foodId, FoodOverView foodOverView);

    // 管理员添加标签
    Boolean addLabel(LabelOverView labelOverView);

    // 管理员删除标签
    Boolean deleteLabel(LabelOverView labelOverView);

    // 管理员获取需要更新的标签的Id
    String getUpdateLabelId(LabelOverView labelOverView);
    // 管理员更新标签
    Boolean updateLabel(String labelId, LabelOverView labelOverView);

}
