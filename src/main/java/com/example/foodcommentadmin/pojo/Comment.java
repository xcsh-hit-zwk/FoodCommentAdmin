package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.Data;

import java.util.Date;

/**
 * @description: MySql中Comment表对应的实体类
 * @author: zhangweikun
 * @create: 2022-04-15 10:14
 **/
@Data
@TableName("comment")
public class Comment extends Model<Comment> {

    @TableId(value = "comment_id", type = IdType.ASSIGN_ID)
    private String commentId;

    private String userId;
    private String restaurantId;
    private String commentInfo;

    private Integer commentLike;

    @TableField(fill = FieldFill.INSERT)
    private Date commentTime;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Date modTime;

}
