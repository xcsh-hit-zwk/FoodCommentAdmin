package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
@TableName("user")
public class User extends Model<User> {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String userId;
    private String password;
    private String nickname;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modTime;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasLogin;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;

}
