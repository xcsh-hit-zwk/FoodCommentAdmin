package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.FoodOverView;
import com.example.foodcommentadmin.pojo.LabelOverView;
import com.example.foodcommentadmin.pojo.RestaurantOverView;

import java.util.List;

/**
 * @description: 餐厅简略信息的Service接口
 * @author: zhangweikun
 * @create: 2022-04-17 14:31
 **/
public interface RestaurantOverViewService {

    // 全部餐厅，用于返回给管理员
    List<RestaurantOverView> totalRestaurantOverView();

    // 全部招牌菜，用于返回给管理员
    List<FoodOverView> totalFoodOverView();

    // 全部标签，用于返回给管理员
    List<LabelOverView> totalLabelOverView();

    // 市为范围，点赞总数降序
    List<RestaurantOverView> cityRestaurantOverView(String city);

    // 饭店名搜索
    // 需要用户手动输入进行搜索
    List<RestaurantOverView> nameRestaurantOverView(String name);

    // 饭店类型搜索
    // 需要用户手动输入进行搜索
    // 格式为：# + 饭店tag
    List<RestaurantOverView> tagRestaurantOverView(String tag);

    // 菜品名搜索
    // 需要用户手动输入进行搜索
    // 格式为：## + 菜品名
    List<RestaurantOverView> foodRestaurantOverView(String food);

    // 区为范围，点赞总数降序
    // 需要用户手动输入进行搜索
    // 格式为：### + 区名
    List<RestaurantOverView> blockRestaurantOverView(String block);

}
