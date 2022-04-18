package com.example.foodcommentadmin.pojo;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @description: 用于展示在列表中的饭店概览信息
 * @author: zhangweikun
 * @create: 2022-04-17 14:23
 **/
@Data
public class RestaurantOverView {

    private String restaurantId;
    private String restaurantName;
    private Integer likes;
    private String restaurantTag;
    private String restaurantPosition;
    private String restaurantImage;
    private String restaurantProvince;
    private String restaurantCity;
    private String restaurantBlock;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getRestaurantTag() {
        return restaurantTag;
    }

    public void setRestaurantTag(String restaurantTag) {
        this.restaurantTag = restaurantTag;
    }

    public String getRestaurantPosition() {
        return restaurantPosition;
    }

    public void setRestaurantPosition(String restaurantPosition) {
        this.restaurantPosition = restaurantPosition;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantProvince() {
        return restaurantProvince;
    }

    public void setRestaurantProvince(String restaurantProvince) {
        this.restaurantProvince = restaurantProvince;
    }

    public String getRestaurantCity() {
        return restaurantCity;
    }

    public void setRestaurantCity(String restaurantCity) {
        this.restaurantCity = restaurantCity;
    }

    public String getRestaurantBlock() {
        return restaurantBlock;
    }

    public void setRestaurantBlock(String restaurantBlock) {
        this.restaurantBlock = restaurantBlock;
    }

    public void printAll(){
        System.out.print("restaurantId: " + restaurantId + ", ");
        System.out.print("restaurantName: " + restaurantName + ", ");
        System.out.print("likes: " + String.valueOf(likes) + ", ");
        System.out.print("restaurantTag: " + restaurantTag + ", ");
        System.out.print("restaurantPosition: " + restaurantPosition + ", ");
        System.out.print("restaurantImage: " + restaurantImage + ", ");
        System.out.print("restaurantProvince: " + restaurantProvince + ", ");
        System.out.print("restaurantCity: " + restaurantCity + ", ");
        System.out.print("restaurantBlock: " + restaurantBlock + "\n");
    }
    /*
    private String restaurantId;
    private String restaurantName;
    private Integer likes;
    private String restaurantTag;
    private String restaurantPosition;
    private String restaurantImage;
    private String restaurantProvince;
    private String restaurantCity;
    private String restaurantBlock;
     */
}
