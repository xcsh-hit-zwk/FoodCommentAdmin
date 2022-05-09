package com.example.foodcommentadmin.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhangweikun
 * @create: 2022-05-09 10:02
 */
@Data
@Slf4j
public class UpdateFoodOverView {

    private String foodId;
    private FoodOverView foodOverView;

}
