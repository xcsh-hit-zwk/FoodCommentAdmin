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

    @Override
    public List<RestaurantOverView> totalRestaurantOverView(){

        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false);
        List<RestaurantInfo> restaurantInfoList = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);

        if(restaurantInfoList.isEmpty()){
            return null;
        }

        // 填充RestaurantOverView信息
        Iterator<RestaurantInfo> restaurantInfoIterator = restaurantInfoList.iterator();
        while (restaurantInfoIterator.hasNext()){

            RestaurantInfo restaurantInfo = restaurantInfoIterator.next();
            RestaurantOverView restaurantOverView = new RestaurantOverView();

            // 填充属于RestaurantInfo的信息
            restaurantOverView.setRestaurantName(restaurantInfo.getRestaurantName());
            restaurantOverView.setRestaurantTag(restaurantInfo.getRestaurantTag());
            restaurantOverView.setRestaurantPosition(restaurantInfo.getRestaurantPosition());
            restaurantOverView.setRestaurantImage(restaurantInfo.getRestaurantImage());
            restaurantOverView.setRestaurantProvince(restaurantInfo.getRestaurantProvince());
            restaurantOverView.setRestaurantCity(restaurantInfo.getRestaurantCity());
            restaurantOverView.setRestaurantBlock(restaurantInfo.getRestaurantBlock());

            // 填充点赞数
            QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
            QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper = new QueryWrapper<>();

            String restaurantId = restaurantInfo.getRestaurantId();
            foodQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);
            restaurantLabelQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);

            List<Food> foodList = new ArrayList<>();
            List<RestaurantLabel> restaurantLabelList = new ArrayList<>();

            foodList = foodMapper.selectList(foodQueryWrapper);
            restaurantLabelList = restaurantLabelMapper.selectList(restaurantLabelQueryWrapper);

            // 添加点赞数
            int likes = 0;
            if(!foodList.isEmpty() && !restaurantLabelList.isEmpty()){

                Iterator<Food> foodIterator = foodList.iterator();
                Iterator<RestaurantLabel> restaurantLabelIterator = restaurantLabelList.iterator();

                while (foodIterator.hasNext()){
                    Food food = foodIterator.next();
                    likes += food.getFoodLike();
                }

                while (restaurantLabelIterator.hasNext()){
                    RestaurantLabel restaurantLabel = restaurantLabelIterator.next();
                    likes += restaurantLabel.getLabelLike();
                }
            }
            restaurantOverView.setLikes(likes);

            restaurantOverViewList.add(restaurantOverView);
        }

        return restaurantOverViewList;
    }

    // 全部招牌菜，用于返回给管理员
    public List<FoodOverView> totalFoodOverView(){

        List<FoodOverView> foodOverViewList = new ArrayList<>();

        List<Food> foodList = new ArrayList<>();
        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false);

        foodList = foodMapper.selectList(foodQueryWrapper);
        if(foodList.isEmpty()){
            return null;
        }

        Iterator<Food> foodIterator = foodList.iterator();
        while (foodIterator.hasNext()){
            Food food = foodIterator.next();
            FoodOverView foodOverView = new FoodOverView();

            foodOverView.setFoodName(food.getFoodName());
            foodOverView.setFoodLikes(food.getFoodLike());
            foodOverView.setFoodImage(food.getFoodPicture());

            QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
            restaurantInfoQueryWrapper.eq("restaurant_id", food.getRestaurantId());
            RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);

            foodOverView.setRestaurantName(restaurantInfo.getRestaurantName());

            foodOverViewList.add(foodOverView);

        }

        return foodOverViewList;
    }

    // 全部标签，用于返回给管理员
    public List<LabelOverView> totalLabelOverView(){

        List<LabelOverView> labelOverViewList = new ArrayList<>();

        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper = new QueryWrapper<>();
        List<RestaurantLabel> restaurantLabelList = restaurantLabelMapper
                .selectList(restaurantLabelQueryWrapper);
        if(restaurantLabelList.isEmpty()){
            return null;
        }

        Iterator<RestaurantLabel> restaurantLabelIterator = restaurantLabelList.iterator();
        while (restaurantLabelIterator.hasNext()){
            RestaurantLabel restaurantLabel = restaurantLabelIterator.next();
            LabelOverView labelOverView = new LabelOverView();
            labelOverView.setLabelName(restaurantLabel.getLabelInfo());

            QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
            restaurantInfoQueryWrapper.eq("restaurant_id", restaurantLabel.getRestaurantId());
            RestaurantInfo restaurantInfo = restaurantInfoMapper
                    .selectOne(restaurantInfoQueryWrapper);

            labelOverView.setRestaurantName(restaurantInfo.getRestaurantName());

            labelOverViewList.add(labelOverView);
        }

        return labelOverViewList;
    }

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

        // 查饭店标签
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper;
        Iterator<RestaurantInfo> infoIterator = restaurantInfos.iterator();
        List<RestaurantLabel> restaurantLabels;
        while (infoIterator.hasNext()){
            restaurantLabelQueryWrapper = new QueryWrapper<>();
            RestaurantInfo restaurantInfo = infoIterator.next();
            String restaurantId = restaurantInfo.getRestaurantId();
            restaurantLabelQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);
            restaurantLabels = restaurantLabelMapper.selectList(restaurantLabelQueryWrapper);
            // 如果标签List是空的，说明饭店没标签，是有问题的查询，需要返回null
            if(!restaurantLabels.isEmpty()){
                // 这个sum的计算方法以后还要修改
                int sum = 0;
                RestaurantOverView restaurantOverView = new RestaurantOverView();

                restaurantOverView.setRestaurantName(restaurantInfo.getRestaurantName());
                restaurantOverView.setRestaurantTag(restaurantInfo.getRestaurantTag());
                restaurantOverView.setRestaurantPosition(restaurantInfo.getRestaurantPosition());
                restaurantOverView.setRestaurantImage(restaurantInfo.getRestaurantImage());
                restaurantOverView.setRestaurantProvince(restaurantInfo.getRestaurantProvince());
                restaurantOverView.setRestaurantCity(restaurantInfo.getRestaurantCity());
                restaurantOverView.setRestaurantBlock(restaurantInfo.getRestaurantBlock());

                Iterator<RestaurantLabel> labelIterator = restaurantLabels.iterator();
                while (labelIterator.hasNext()){
                    RestaurantLabel restaurantLabel = labelIterator.next();
                    sum += restaurantLabel.getLabelLike();
                }

                restaurantOverView.setLikes(sum);

                restaurantOverViewList.add(restaurantOverView);
            }
            else {
                return null;
            }
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

        // 查饭店标签
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper;
        Iterator<RestaurantInfo> infoIterator = restaurantInfos.iterator();
        List<RestaurantLabel> restaurantLabels;
        while (infoIterator.hasNext()){
            restaurantLabelQueryWrapper = new QueryWrapper<>();
            RestaurantInfo restaurantInfo = infoIterator.next();
            String restaurantId = restaurantInfo.getRestaurantId();
            restaurantLabelQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);
            restaurantLabels = restaurantLabelMapper.selectList(restaurantLabelQueryWrapper);
            // 如果标签List是空的，说明饭店没标签，是有问题的查询，需要返回null
            if(!restaurantLabels.isEmpty()){
                // 这个sum的计算方法以后还要修改
                int sum = 0;
                RestaurantOverView restaurantOverView = new RestaurantOverView();

                restaurantOverView.setRestaurantName(restaurantInfo.getRestaurantName());
                restaurantOverView.setRestaurantTag(restaurantInfo.getRestaurantTag());
                restaurantOverView.setRestaurantPosition(restaurantInfo.getRestaurantPosition());
                restaurantOverView.setRestaurantImage(restaurantInfo.getRestaurantImage());
                restaurantOverView.setRestaurantProvince(restaurantInfo.getRestaurantProvince());
                restaurantOverView.setRestaurantCity(restaurantInfo.getRestaurantCity());
                restaurantOverView.setRestaurantBlock(restaurantInfo.getRestaurantBlock());

                Iterator<RestaurantLabel> labelIterator = restaurantLabels.iterator();
                while (labelIterator.hasNext()){
                    RestaurantLabel restaurantLabel = labelIterator.next();
                    sum += restaurantLabel.getLabelLike();
                }

                restaurantOverView.setLikes(sum);

                restaurantOverViewList.add(restaurantOverView);
            }
            else {
                return null;
            }
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

        // 查饭店标签
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper;
        Iterator<RestaurantInfo> infoIterator = restaurantInfos.iterator();
        List<RestaurantLabel> restaurantLabels;
        while (infoIterator.hasNext()){
            restaurantLabelQueryWrapper = new QueryWrapper<>();
            RestaurantInfo restaurantInfo = infoIterator.next();
            String restaurantId = restaurantInfo.getRestaurantId();
            restaurantLabelQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);
            restaurantLabels = restaurantLabelMapper.selectList(restaurantLabelQueryWrapper);
            // 如果标签List是空的，说明饭店没标签，是有问题的查询，需要返回null
            if(!restaurantLabels.isEmpty()){
                // 这个sum的计算方法以后还要修改
                int sum = 0;
                RestaurantOverView restaurantOverView = new RestaurantOverView();

                restaurantOverView.setRestaurantName(restaurantInfo.getRestaurantName());
                restaurantOverView.setRestaurantTag(restaurantInfo.getRestaurantTag());
                restaurantOverView.setRestaurantPosition(restaurantInfo.getRestaurantPosition());
                restaurantOverView.setRestaurantImage(restaurantInfo.getRestaurantImage());
                restaurantOverView.setRestaurantProvince(restaurantInfo.getRestaurantProvince());
                restaurantOverView.setRestaurantCity(restaurantInfo.getRestaurantCity());
                restaurantOverView.setRestaurantBlock(restaurantInfo.getRestaurantBlock());

                Iterator<RestaurantLabel> labelIterator = restaurantLabels.iterator();
                while (labelIterator.hasNext()){
                    RestaurantLabel restaurantLabel = labelIterator.next();
                    sum += restaurantLabel.getLabelLike();
                }

                restaurantOverView.setLikes(sum);

                restaurantOverViewList.add(restaurantOverView);
            }
            else {
                return null;
            }
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
            int sum = 0;
            Iterator<RestaurantLabel> labelIterator = restaurantLabels.iterator();
            while (labelIterator.hasNext()){
                RestaurantLabel restaurantLabel = labelIterator.next();
                sum += restaurantLabel.getLabelLike();
            }

            restaurantOverView.setLikes(sum);

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

        // 查饭店标签
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper;
        Iterator<RestaurantInfo> infoIterator = restaurantInfos.iterator();
        List<RestaurantLabel> restaurantLabels;
        while (infoIterator.hasNext()){
            restaurantLabelQueryWrapper = new QueryWrapper<>();
            RestaurantInfo restaurantInfo = infoIterator.next();
            String restaurantId = restaurantInfo.getRestaurantId();
            restaurantLabelQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);
            restaurantLabels = restaurantLabelMapper.selectList(restaurantLabelQueryWrapper);
            // 如果标签List是空的，说明饭店没标签，是有问题的查询，需要返回null
            if(!restaurantLabels.isEmpty()){
                // 这个sum的计算方法以后还要修改
                int sum = 0;
                RestaurantOverView restaurantOverView = new RestaurantOverView();

                restaurantOverView.setRestaurantName(restaurantInfo.getRestaurantName());
                restaurantOverView.setRestaurantTag(restaurantInfo.getRestaurantTag());
                restaurantOverView.setRestaurantPosition(restaurantInfo.getRestaurantPosition());
                restaurantOverView.setRestaurantImage(restaurantInfo.getRestaurantImage());
                restaurantOverView.setRestaurantProvince(restaurantInfo.getRestaurantProvince());
                restaurantOverView.setRestaurantCity(restaurantInfo.getRestaurantCity());
                restaurantOverView.setRestaurantBlock(restaurantInfo.getRestaurantBlock());

                Iterator<RestaurantLabel> labelIterator = restaurantLabels.iterator();
                while (labelIterator.hasNext()){
                    RestaurantLabel restaurantLabel = labelIterator.next();
                    sum += restaurantLabel.getLabelLike();
                }

                restaurantOverView.setLikes(sum);

                restaurantOverViewList.add(restaurantOverView);
            }
            else {
                return null;
            }
        }
        return restaurantOverViewList;
    }
}
