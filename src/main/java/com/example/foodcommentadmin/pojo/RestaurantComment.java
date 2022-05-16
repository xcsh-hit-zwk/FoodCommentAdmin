package com.example.foodcommentadmin.pojo;

import lombok.Data;

/**
 * @author: zhangweikun
 * @create: 2022-05-16 19:00
 */
@Data
public class RestaurantComment {

    private String username;
    private String userImage;
    private String nickname;
    private String commentInfo;
    private Integer commentLike;

}
