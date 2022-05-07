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

}
