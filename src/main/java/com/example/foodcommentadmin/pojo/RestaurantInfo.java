package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantInfo that = (RestaurantInfo) o;
        return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(restaurantName, that.restaurantName) && Objects.equals(restaurantTag, that.restaurantTag) && Objects.equals(restaurantPosition, that.restaurantPosition) && Objects.equals(restaurantImage, that.restaurantImage) && Objects.equals(restaurantProvince, that.restaurantProvince) && Objects.equals(restaurantCity, that.restaurantCity) && Objects.equals(restaurantBlock, that.restaurantBlock) && Objects.equals(createTime, that.createTime) && Objects.equals(modTime, that.modTime) && Objects.equals(hasDelete, that.hasDelete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, restaurantName, restaurantTag, restaurantPosition, restaurantImage, restaurantProvince, restaurantCity, restaurantBlock, createTime, modTime, hasDelete);
    }
}
