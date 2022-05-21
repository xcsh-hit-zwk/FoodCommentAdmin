package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.*;

import java.util.List;

public interface UserService {

    Boolean login(Account account);

    Boolean signUp(RegisterAccount registerAccount);

    User getUser(Account account);

    Boolean updateUserInfo(UserInfo userInfo);

    List<UserInfoComment> getUserComment(String username);

    String getUserImage(String username);
}
