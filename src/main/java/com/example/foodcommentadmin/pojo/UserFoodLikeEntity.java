package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @author: zhangweikun
 * @create: 2022-05-17 17:15
 */
@Data
@TableName("userfoodlike")
public class UserFoodLikeEntity extends Model<UserFoodLikeEntity> {

    @TableId(value = "userfoodlike_id", type = IdType.ASSIGN_ID)
    private String userfoodlikeId;

    private String userId;
    private String foodId;
    private String restaurantId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Date modTime;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;
}
