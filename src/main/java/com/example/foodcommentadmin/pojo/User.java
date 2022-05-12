package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User extends Model<User> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String userId;
    private String password;
    private String nickname;
    private String userImage;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Date modTime;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;

}
