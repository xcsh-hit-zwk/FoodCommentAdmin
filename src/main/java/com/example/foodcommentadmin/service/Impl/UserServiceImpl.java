package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.AccountMapper;
import com.example.foodcommentadmin.mapper.UserMapper;
import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.User;
import com.example.foodcommentadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Wrapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean login(Account account){

        String userId =  account.getUserId();

        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        Account sqlAccount = accountMapper.selectOne(queryWrapper);
        if(sqlAccount != null && sqlAccount.getHasDelete().booleanValue() == false){
            if(account.getPassword().equals(sqlAccount.getPassword())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean signUp(User user) {
        String userId = user.getUserId();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        User sqlUser = userMapper.selectOne(queryWrapper);
        if(sqlUser == null){
            int result = userMapper.insert(user);
            if(result == 1){
                return true;
            }
        }

        return false;
    }
}
