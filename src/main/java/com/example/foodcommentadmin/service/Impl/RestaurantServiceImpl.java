package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.FoodMapper;
import com.example.foodcommentadmin.mapper.RestaurantInfoMapper;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 餐厅设置修改各种信息接口的实现类
 * @author: zhangweikun
 * @create: 2022-04-23 09:26
 **/
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    private FoodMapper foodMapper;

    /**
     * 餐厅注册方法，通过检查已有账户名来判断是否已经注册
     * @param restaurantInfo
     * @return 注册结果
     */
    @Override
    public Boolean signUp(RestaurantInfo restaurantInfo) {
        String name = restaurantInfo.getRestaurantName();
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("restaurant_name", name);

        RestaurantInfo sqlRestaurantInfo = restaurantInfoMapper
                .selectOne(restaurantInfoQueryWrapper);

        if(sqlRestaurantInfo != null){
            return false;
        }

        restaurantInfoMapper.insert(restaurantInfo);

        return true;
    }

    /**
     * 餐厅登录方法，通过比对已有账户名称来确认是否可以登录
     * @param restaurantAccount
     * @return 登陆结果
     */
    @Override
    public Boolean login(RestaurantAccount restaurantAccount) {
        String restaurantName = restaurantAccount.getRestaurantName();
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("restaurant_name", restaurantName);

        RestaurantInfo restaurantInfo = restaurantInfoMapper
                .selectOne(restaurantInfoQueryWrapper);
        if(restaurantInfo != null){
            if(restaurantInfo.getRestaurantPassword().equals(restaurantAccount.getRestaurantPassword()))
            {
                return true;
            }
        }
        return false;
    }

     /** 向Food表中添加餐厅招牌菜
     * @param restaurantName
     * @param restaurantFoods
     * @return 插入失败的招牌菜
     */
    @Override
    public List<RestaurantFoodEntity> addFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods) {
        List<RestaurantFoodEntity> failedInsertList = new ArrayList<>();
        // 查询restaurant_name对应的restaurant_id
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("restaurant_name", restaurantName);
        RestaurantInfo restaurantInfo = restaurantInfoMapper
                .selectOne(restaurantInfoQueryWrapper);
        String restaurantId = restaurantInfo.getRestaurantId();

        // 通过前端传来的RestaurantFoodEntity对象和上面查到的restaurant_id
        // 来拼出Food对象并插入进表中
        Iterator<RestaurantFoodEntity> restaurantFoodEntityIterator = restaurantFoods.iterator();
        while (restaurantFoodEntityIterator.hasNext()){
            Food food = new Food();
            RestaurantFoodEntity restaurantFoodEntity = restaurantFoodEntityIterator.next();

            food.setFoodName(restaurantFoodEntity.getFoodName());
            food.setRestaurantId(restaurantId);
            food.setFoodLike(restaurantFoodEntity.getFoodLike());
            food.setFoodPicture(restaurantFoodEntity.getFoodPicture());

            // 通过food_name和restaurant_id来合并查询是否重复添加招牌菜
            QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
            foodQueryWrapper.eq("food_name", food.getFoodName())
                    .eq("restaurant_id", food.getRestaurantId());
            Food temp = foodMapper.selectOne(foodQueryWrapper);
            if(temp == null){
                foodMapper.insert(food);
            }
            else {
                failedInsertList.add(restaurantFoodEntity);
            }

        }
        return failedInsertList;
    }

    /**
     * 删除对应招牌菜
     * @param restaurantName
     * @param restaurantFoods
     * @return 返回删除失败的招牌菜
     */
    @Override
    public List<RestaurantFoodEntity> deleteFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods) {
        return null;
    }

    @Override
    public List<RestaurantFoodEntity> updateFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods) {
        return null;
    }

    @Override
    public Boolean addLabel(String restaurantName, List<RestaurantLabel> restaurantLabels) {
        return null;
    }

    @Override
    public Boolean deleteLabel(String restaurantName, List<RestaurantLabel> restaurantLabels) {
        return null;
    }

    @Override
    public Boolean updateLabel(String restaurantName, List<RestaurantLabel> restaurantLabels) {
        return null;
    }

}
