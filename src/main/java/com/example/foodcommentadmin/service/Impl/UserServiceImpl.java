package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.CommentMapper;
import com.example.foodcommentadmin.mapper.RestaurantInfoMapper;
import com.example.foodcommentadmin.mapper.UserMapper;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

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
            user.setUserImage(registerAccount.getImageUrl());

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

    @Override
    public List<UserInfoComment> getUserComment(String username) {
        List<UserInfoComment> userInfoCommentList = new ArrayList<>();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("user_id", username);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            return null;
        }

        String userId = user.getId();

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("has_delete", false)
                .eq("user_id", userId);
        List<Comment> commentList = commentMapper.selectList(commentQueryWrapper);
        Iterator<Comment> commentIterator = commentList.iterator();
        while (commentIterator.hasNext()){
            Comment comment = commentIterator.next();
            UserInfoComment userInfoComment = new UserInfoComment();
            userInfoComment.setCommentId(comment.getCommentId());
            userInfoComment.setUsername(user.getUserId());
            userInfoComment.setNickname(user.getNickname());
            userInfoComment.setUserImage(user.getUserImage());
            userInfoComment.setCommentInfo(comment.getCommentInfo());
            QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
            restaurantInfoQueryWrapper.eq("has_delete", false)
                    .eq("restaurant_id", comment.getRestaurantId());
            RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
            if (restaurantInfo != null){
                userInfoComment.setRestaurantImage(restaurantInfo.getRestaurantImage());
                userInfoComment.setRestaurantName(restaurantInfo.getRestaurantName());

                String position = restaurantInfo.getRestaurantProvince() + " " +
                        restaurantInfo.getRestaurantCity() + " " +
                        restaurantInfo.getRestaurantBlock();
                userInfoComment.setRestaurantPosition(position);

                userInfoComment.setRestaurantTag(restaurantInfo.getRestaurantTag());
            }

            userInfoCommentList.add(userInfoComment);
        }

        return userInfoCommentList;
    }

    @Override
    public String getUserImage(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("user_id", username);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            return null;
        }

        return user.getUserImage();
    }
}
