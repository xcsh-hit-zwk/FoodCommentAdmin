package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.FoodMapper;
import com.example.foodcommentadmin.mapper.RestaurantInfoMapper;
import com.example.foodcommentadmin.mapper.RestaurantLabelMapper;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.RestaurantOverViewService;
import com.mysql.cj.log.Log;
import com.mysql.cj.x.protobuf.MysqlxResultset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
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


    @Override
    public List<RestaurantOverView> cityRestaurantOverView(String city) {

        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();
        // 查饭店信息
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.like("restaurant_city", city)
                .eq("has_delete", false);
        List<RestaurantInfo> restaurantInfoList = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);
        restaurantOverViewList = fillRestaurantOverView(restaurantInfoList);

        Collections.sort(restaurantOverViewList, (o1, o2) -> {
            if (o1.getLikes() < o2.getLikes())
                return 1;
            else
                return -1;
        });

        return restaurantOverViewList;
    }

    @Override
    public List<RestaurantOverView> tagRestaurantOverView(String tag) {
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        // 查饭店信息
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.like("restaurant_tag", tag)
                .eq("has_delete", false);
        List<RestaurantInfo> restaurantInfoList = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);

        restaurantOverViewList = fillRestaurantOverView(restaurantInfoList);
        Collections.sort(restaurantOverViewList, (o1, o2) -> {
            if (o1.getLikes() < o2.getLikes())
                return 1;
            else
                return -1;
        });

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
            return new ArrayList<>();
        }

        // 根据restaurant_id查restaurantinfo表
        List<RestaurantInfo> restaurantInfoList = new ArrayList<>();
        Iterator<Food> foodIterator = foodList.iterator();
        while (foodIterator.hasNext()){
            Food next = foodIterator.next();
            QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
            restaurantInfoQueryWrapper.eq("has_delete", false)
                    .eq("restaurant_id", next.getRestaurantId());
            RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
            if(restaurantInfo != null){
                if (!restaurantInfoList.contains(restaurantInfo)){
                    restaurantInfoList.add(restaurantInfo);
                }
            }
            // 纠错
            else {
                QueryWrapper<Food> correct = new QueryWrapper<>();
                correct.eq("has_delete", false)
                        .eq("food_id", next.getFoodId());
                next.setHasDelete(true);
                next.setModTime(new Date());
                int result = foodMapper.update(next, correct);
                log.info("招牌菜查询餐厅纠错，结果为: " + String.valueOf(result));
            }
        }
        restaurantOverViewList = fillRestaurantOverView(restaurantInfoList);
        Collections.sort(restaurantOverViewList, ((o1, o2) -> {
            if (o1.getLikes() < o2.getLikes())
                return 1;
            else
                return -1;
        }));

        return restaurantOverViewList;
    }

    @Override
    public List<RestaurantOverView> blockRestaurantOverView(String block) {
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        // 查饭店信息
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.like("restaurant_block", block)
                .eq("has_delete", false);
        List<RestaurantInfo> restaurantInfoList = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);
        restaurantOverViewList = fillRestaurantOverView(restaurantInfoList);
        Collections.sort(restaurantOverViewList, ((o1, o2) -> {
            if (o1.getLikes() < o2.getLikes())
                return 1;
            else
                return -1;
        }));

        return restaurantOverViewList;
    }

    @Override
    public List<RestaurantOverView> nameRestaurantOverView(String name) {
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .like("restaurant_name", name);
        List<RestaurantInfo> restaurantInfoList = restaurantInfoMapper.selectList(restaurantInfoQueryWrapper);
        restaurantOverViewList = fillRestaurantOverView(restaurantInfoList);
        Collections.sort(restaurantOverViewList, ((o1, o2) -> {
            if (o1.getLikes() < o2.getLikes())
                return 1;
            else
                return -1;
        }));
        return restaurantOverViewList;
    }

    private List<RestaurantOverView> fillRestaurantOverView(List<RestaurantInfo> restaurantInfoList){
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        Iterator<RestaurantInfo> restaurantInfoIterator = restaurantInfoList.iterator();
        while (restaurantInfoIterator.hasNext()){

            RestaurantInfo restaurantInfo = restaurantInfoIterator.next();
            RestaurantOverView restaurantOverView = new RestaurantOverView();

            restaurantOverView.setRestaurantName(restaurantInfo.getRestaurantName());
            restaurantOverView.setRestaurantTag(restaurantInfo.getRestaurantTag());
            restaurantOverView.setRestaurantImage(restaurantInfo.getRestaurantImage());
            restaurantOverView.setRestaurantPosition(restaurantInfo.getRestaurantPosition());
            restaurantOverView.setRestaurantProvince(restaurantInfo.getRestaurantProvince());
            restaurantOverView.setRestaurantCity(restaurantInfo.getRestaurantCity());
            restaurantOverView.setRestaurantBlock(restaurantInfo.getRestaurantBlock());

            QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
            foodQueryWrapper.eq("has_delete", false)
                    .eq("restaurant_id", restaurantInfo.getRestaurantId());

            List<Food> foodList = foodMapper.selectList(foodQueryWrapper);
            if (!foodList.isEmpty()){
                int likes = 0;
                Iterator<Food> foodIterator = foodList.iterator();
                while (foodIterator.hasNext()){
                    Food food = foodIterator.next();
                    likes += food.getFoodLike();
                }
                restaurantOverView.setLikes(likes);
            }
            else {
                restaurantOverView.setLikes(0);
            }

            restaurantOverViewList.add(restaurantOverView);
        }

        return restaurantOverViewList;
    }
}
