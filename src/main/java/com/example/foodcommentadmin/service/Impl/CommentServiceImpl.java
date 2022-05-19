package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.CommentMapper;
import com.example.foodcommentadmin.mapper.RestaurantInfoMapper;
import com.example.foodcommentadmin.mapper.UserMapper;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhangweikun
 * @create: 2022-05-19 18:22
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

    /**
     * @param commentAddEntity
     * @return comment_id
     */
    @Override
    public String addComment(CommentAddEntity commentAddEntity) {
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", commentAddEntity.getRestaurantName());
        RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if (restaurantInfo == null){
            return null;
        }
        String restaurantId = restaurantInfo.getRestaurantId();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("user_id", commentAddEntity.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            return null;
        }
        String userId = user.getId();

        Comment comment = new Comment();
        comment.setCommentLike(0);
        comment.setCommentInfo(commentAddEntity.getCommentInfo());
        comment.setRestaurantId(restaurantId);
        comment.setUserId(userId);
        int result = commentMapper.insert(comment);
        if (result == 1){
            return comment.getCommentId();
        }
        return null;
    }

    @Override
    public RestaurantComment getComment(String commentId) {
        RestaurantComment restaurantComment = new RestaurantComment();

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("has_delete", false)
                .eq("comment_id", commentId);
        Comment comment = commentMapper.selectOne(commentQueryWrapper);
        if (comment == null){
            return null;
        }

        restaurantComment.setCommentId(comment.getCommentId());
        restaurantComment.setCommentLike(comment.getCommentLike());
        restaurantComment.setCommentInfo(comment.getCommentInfo());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("id", comment.getUserId());
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            return null;
        }

        restaurantComment.setUsername(user.getUserId());
        restaurantComment.setUserImage(user.getUserImage());
        restaurantComment.setNickname(user.getNickname());

        return restaurantComment;
    }
}
