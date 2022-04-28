package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * description: 管理员表实体类
 * @author: zhangweikun
 * @create: 2022-4-28 8:59
 */
@Data
@TableName("restaurantAdmin")
public class AdminEntity extends Model<AdminEntity> {

    @TableId(value = "admin_id", type = IdType.ASSIGN_ID)
    private String adminId;

    private String adminUsername;
    private String adminPassword;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modTime;

    @TableField(fill = FieldFill.INSERT)
    private Boolean hasDelete;
}
