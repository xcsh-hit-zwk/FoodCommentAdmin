package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@TableName("user")
public class Account {

    private String userId;
    private String password;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasLogin;
}
