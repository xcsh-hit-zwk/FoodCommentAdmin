package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @description: 餐厅登录实体类
 * @author: zhangweikun
 * @create: 2022-04-23 09:21
 **/
@Data
public class AdminAccount extends Model<AdminAccount> {
    private String username;
    private String password;
}
