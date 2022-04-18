package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.User;

public interface UserService {

    Boolean login(Account account);
    Boolean signUp(User user);
}
