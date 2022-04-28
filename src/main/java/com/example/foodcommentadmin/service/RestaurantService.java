package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.*;

import java.util.List;

/**
 * @description: 餐厅注册的Service接口
 * @author: zhangweikun
 * @create: 2022-04-23 09:15
 **/
public interface RestaurantService {



    /**
     * 餐厅招牌菜相关接口
     */
    // 餐厅添加招牌菜接口
    List<RestaurantFoodEntity> addFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods);
    // 餐厅下架招牌菜接口
    List<RestaurantFoodEntity> deleteFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods);
    // 餐厅修改招牌菜接口
    List<RestaurantFoodEntity> updateFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods);

    /**
     * 餐厅标签相关接口
     */
    // 餐厅添加标签接口
    Boolean addLabel(String restaurantName, List<RestaurantLabel> restaurantLabels);
    // 餐厅删除标签接口
    Boolean deleteLabel(String restaurantName, List<RestaurantLabel> restaurantLabels);
    // 餐厅修改标签接口
    Boolean updateLabel(String restaurantName, List<RestaurantLabel> restaurantLabels);
}
