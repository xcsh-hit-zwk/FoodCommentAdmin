package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @description: MySql中RestaurantLabel表对应的实体类
 * @author: zhangweikun
 * @create: 2022-04-15 10:31
 **/
@Data
@TableName("restaurantlabel")
public class RestaurantLabel {

    @TableId(value = "label_id", type = IdType.ASSIGN_ID)
    private String labelId;

    private String restaurantId;
    private String labelInfo;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Date modTime;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;
}
