package com.example.foodcommentadmin.pojo;

import lombok.Data;

/**
 * @author: zhangweikun
 * @create: 2022-05-17 17:43
 */
@Data
public class CommentLiked {

    private String usercommentlikeId;
    private String commentId;
    private String userId;
    private String restaurantId;

}
