package com.example.foodcommentadmin.pojo;

import lombok.Data;

/**
 * @author: zhangweikun
 * @create: 2022-05-17 17:44
 */
@Data
public class FoodLiked {

    private String userfoodlikeId;
    private String userId;
    private String foodId;
    private String restaurantId;
    private String username;
    private String foodName;
    private String restaurantName;

}
