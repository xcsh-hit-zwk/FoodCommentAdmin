package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String createTime;
    private String modTime;
    private Boolean hasLogin;
    private Boolean hasDelete;

}
