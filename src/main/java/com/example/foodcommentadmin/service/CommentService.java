package com.example.foodcommentadmin.service;

import com.example.foodcommentadmin.pojo.CommentAddEntity;
import com.example.foodcommentadmin.pojo.RestaurantComment;

/**
 * @author: zhangweikun
 * @create: 2022-05-19 18:19
 */
public interface CommentService {

    /**
     * @param commentAddEntity
     * @return comment_id
     */
    String addComment(CommentAddEntity commentAddEntity);

    RestaurantComment getComment(String commentId);

    Boolean modifyComment(String commentId, String commentInfo);

    Boolean deleteComment(String commentId);
}
