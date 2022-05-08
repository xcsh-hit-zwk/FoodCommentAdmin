package com.example.foodcommentadmin.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhangweikun
 * @create: 2022-05-08 16:22
 */
@Data
@Slf4j
public class UpdateRestaurantOverView {

    private String restaurantId;
    private RestaurantOverView restaurantOverView;

}
