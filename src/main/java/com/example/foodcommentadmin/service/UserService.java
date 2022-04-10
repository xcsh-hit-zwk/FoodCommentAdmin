package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {

    boolean login(Account account);
    boolean signUp(User user);
}
