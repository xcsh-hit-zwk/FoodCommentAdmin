package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @description: Mysql中Food表对应的实体类
 * @author: zhangweikun
 * @create: 2022-04-15 10:19
 **/
@Data
@TableName("food")
public class Food extends Model<Food> {

    @TableId(value = "food_id", type = IdType.ASSIGN_ID)
    private String foodId;

    private String foodName;
    private String restaurantId;
    private Integer foodLike;
    private String foodPicture;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Date modTime;

}
