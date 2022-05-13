package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.AccountMapper;
import com.example.foodcommentadmin.mapper.UserMapper;
import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.RegisterAccount;
import com.example.foodcommentadmin.pojo.User;
import com.example.foodcommentadmin.pojo.UserInfo;
import com.example.foodcommentadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean login(Account account){

        String userId =  account.getUsername();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_id",userId)
                    .eq("has_delete", false);

        User user = userMapper.selectOne(userQueryWrapper);
        if(user != null){
            if(account.getPassword().equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean signUp(RegisterAccount registerAccount) {
        String username = registerAccount.getUsername();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_id", username)
                .eq("has_delete", false);

        User sqlUser = userMapper.selectOne(userQueryWrapper);
        if(sqlUser == null){
            User user = new User();

            user.setUserId(registerAccount.getUsername());
            user.setPassword(registerAccount.getPassword());
            user.setNickname(registerAccount.getNickname());

            userMapper.insert(user);
            return true;
        }
        return false;
    }

    @Override
    public User getUser(Account account) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("user_id", account.getUsername())
                .eq("password", account.getPassword());
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            return null;
        }

        return user;
    }

    @Override
    public Boolean updateUserInfo(UserInfo userInfo) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("user_id", userInfo.getUsername())
                .eq("password", userInfo.getPassword());
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            return false;
        }
        user.setNickname(userInfo.getNickname());
        user.setUserImage(userInfo.getUserImage());

        int result = userMapper.update(user, userQueryWrapper);
        if(result == 1){
            return true;
        }
        else {
            return false;
        }
    }
}
