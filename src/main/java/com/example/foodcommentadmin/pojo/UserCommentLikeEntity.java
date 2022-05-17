package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @author: zhangweikun
 * @create: 2022-05-17 17:10
 */
@Data
@TableName("usercommentlike")
public class UserCommentLikeEntity extends Model<UserCommentLikeEntity> {

    @TableId(value = "usercommentlike_id", type = IdType.ASSIGN_ID)
    private String usercommentlikeId;

    private String commentId;
    private String userId;
    private String restaurantId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Date modTime;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;

}
