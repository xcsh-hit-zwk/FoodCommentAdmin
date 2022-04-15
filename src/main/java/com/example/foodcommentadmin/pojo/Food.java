package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

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
    private Boolean hasDelete;
    private String createTime;
    private String modTime;

}
