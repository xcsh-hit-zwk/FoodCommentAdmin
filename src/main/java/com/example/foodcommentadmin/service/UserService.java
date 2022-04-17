package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {

    Boolean login(Account account);
    Boolean signUp(User user);
}
