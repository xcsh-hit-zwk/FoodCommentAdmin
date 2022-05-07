package com.example.foodcommentadmin.pojo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

@Data
public class Account extends Model<Account> {

    private String username;
    private String password;

}
