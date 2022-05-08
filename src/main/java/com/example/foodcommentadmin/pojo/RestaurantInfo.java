package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @description: Mysql中RestaurantInfo表对应的实体类
 * @author: zhangweikun
 * @create: 2022-04-15 10:27
 **/
@Data
@TableName("restaurantinfo")
public class RestaurantInfo extends Model<RestaurantInfo> {

    @TableId(value = "restaurant_id", type = IdType.ASSIGN_ID)
    private String restaurantId;

    private String restaurantName;
    private String restaurantTag;
    private String restaurantPosition;
    private String restaurantImage;
    private String restaurantProvince;
    private String restaurantCity;
    private String restaurantBlock;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Date modTime;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;

}
