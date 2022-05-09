package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.FoodMapper;
import com.example.foodcommentadmin.mapper.RestaurantInfoMapper;
import com.example.foodcommentadmin.mapper.RestaurantLabelMapper;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.RestaurantOverViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 餐厅简略信息的Service类实现类
 * @author: zhangweikun
 * @create: 2022-04-17 14:51
 **/
@Slf4j
@Service
public class RestaurantOverViewServiceImpl implements RestaurantOverViewService {

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    private RestaurantLabelMapper restaurantLabelMapper;

    @Autowired
    private FoodMapper foodMapper;


    // todo 跟用户端有关的返回接口全都要改，包括FoodName字段的修正问题
    @Override
    public List<RestaurantOverView> cityRestaurantOverView(String city) {

        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        // 查饭店信息
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.like("restaurant_city", city)
                .eq("has_delete", false);
        List<RestaurantInfo> restaurantInfos = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);

        if(restaurantInfos.isEmpty()){
            return null;
        }

        return restaurantOverViewList;
    }

    @Override
    public List<RestaurantOverView> nameRestaurantOverView(String name) {
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        // 查饭店信息
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.like("restaurant_name", name)
                .eq("has_delete", false);
        List<RestaurantInfo> restaurantInfos = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);

        if(restaurantInfos.isEmpty()){
            return null;
        }


        return restaurantOverViewList;
    }

    @Override
    public List<RestaurantOverView> tagRestaurantOverView(String tag) {
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        // 查饭店信息
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.like("restaurant_tag", tag)
                .eq("has_delete", false);
        List<RestaurantInfo> restaurantInfos = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);

        if(!restaurantInfos.isEmpty()){
            return null;
        }


        return restaurantOverViewList;
    }

    @Override
    public List<RestaurantOverView> foodRestaurantOverView(String food) {
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        // 根据food表查restaurant_id
        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.like("food_name", food)
                .eq("has_delete", false);
        List<Food> foodList = foodMapper.selectList(foodQueryWrapper);
        if (foodList.isEmpty()){
            return null;
        }

        // 根据restaurant_id查restaurantinfo表和restaurantlabel表
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper;
        Iterator<Food> foodIterator = foodList.iterator();
        while (foodIterator.hasNext()){
            RestaurantOverView restaurantOverView = new RestaurantOverView();

            Food tempFood = foodIterator.next();
            String restaurantID = tempFood.getRestaurantId();
            restaurantInfoQueryWrapper.eq("restaurant_id", restaurantID)
                    .eq("has_delete", false);
            RestaurantInfo restaurantInfo = restaurantInfoMapper
                    .selectOne(restaurantInfoQueryWrapper);
            // 通过得到的restaurantInfo设置restaurantOverView的相关属性
            if(restaurantInfo == null){
                return null;
            }

            restaurantOverView.setRestaurantName(restaurantInfo.getRestaurantName());
            restaurantOverView.setRestaurantTag(restaurantInfo.getRestaurantTag());
            restaurantOverView.setRestaurantPosition(restaurantInfo.getRestaurantPosition());
            restaurantOverView.setRestaurantImage(restaurantInfo.getRestaurantImage());
            restaurantOverView.setRestaurantProvince(restaurantInfo.getRestaurantProvince());
            restaurantOverView.setRestaurantCity(restaurantInfo.getRestaurantCity());
            restaurantOverView.setRestaurantBlock(restaurantInfo.getRestaurantBlock());

            restaurantLabelQueryWrapper = new QueryWrapper<>();
            restaurantLabelQueryWrapper.eq("restaurant_id", restaurantID)
                    .eq("has_delete", false);
            List<RestaurantLabel> restaurantLabels = restaurantLabelMapper
                    .selectList(restaurantLabelQueryWrapper);
            if(restaurantLabels.isEmpty()){
                return null;
            }

            restaurantOverView.setLikes(0);

            restaurantOverViewList.add(restaurantOverView);

        }


        return restaurantOverViewList;
    }

    @Override
    public List<RestaurantOverView> blockRestaurantOverView(String block) {
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        // 查饭店信息
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.like("restaurant_block", block)
                .eq("has_delete", false);
        List<RestaurantInfo> restaurantInfos = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);

        if(restaurantInfos.isEmpty()){
            return null;
        }


        return restaurantOverViewList;
    }
}
