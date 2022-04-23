package com.example.foodcommentadmin.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 前端传来的招牌菜实体类
 * @author: zhangweikun
 * @create: 2022-04-23 16:01
 **/
@Data
public class RestaurantFoodEntity implements Serializable {
    private String restaurantName;
    private String foodName;
    private Integer foodLike;
    private String foodPicture;
}
