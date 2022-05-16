package com.example.foodcommentadmin.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author: zhangweikun
 * @create: 2022-05-16 19:00
 */
@Data
public class RestaurantDetail {

    private String restaurantId;
    private String restaurantName;
    private Integer restaurantLikes;
    private String restaurantTag;
    private String restaurantBlock;
    private String restaurantPosition;
    private List<FoodOverView> foodList;
    private List<String> labelList;
    private List<RestaurantComment> commentList;

}
