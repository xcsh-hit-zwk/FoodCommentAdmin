package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.Account;
import org.springframework.stereotype.Service;

public interface UserService {

    public boolean login(Account account);

}
