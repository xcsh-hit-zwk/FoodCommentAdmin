package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.RegisterAccount;
import com.example.foodcommentadmin.pojo.User;
import com.example.foodcommentadmin.pojo.UserInfo;

public interface UserService {

    Boolean login(Account account);

    Boolean signUp(RegisterAccount registerAccount);

    User getUser(Account account);

    Boolean updateUserInfo(UserInfo userInfo);
}
