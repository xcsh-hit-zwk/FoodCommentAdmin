package com.example.foodcommentadmin.pojo;

import lombok.Data;

/**
 * @description: 餐厅登录实体类
 * @author: zhangweikun
 * @create: 2022-04-23 09:21
 **/
@Data
public class RestaurantAccount {
    private String restaurantName;
    private String restaurantPassword;
}
