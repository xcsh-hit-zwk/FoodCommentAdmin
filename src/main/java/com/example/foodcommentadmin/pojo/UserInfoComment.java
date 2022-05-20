package com.example.foodcommentadmin.pojo;

import lombok.Data;

/**
 * @author: zhangweikun
 * @create: 2022-05-20 9:05
 */
@Data
public class UserInfoComment {

    private String username;
    private String userImage;
    private String nickname;
    private String commentInfo;
    private String restaurantImage;
    private String restaurantName;
    private String restaurantPosition;
    private String restaurantTag;

}
