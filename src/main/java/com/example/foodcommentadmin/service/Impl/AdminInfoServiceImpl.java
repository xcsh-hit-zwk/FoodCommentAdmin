package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.FoodMapper;
import com.example.foodcommentadmin.mapper.RestaurantInfoMapper;
import com.example.foodcommentadmin.mapper.RestaurantLabelMapper;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.AdminInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author: zhangweikun
 * @create: 2022-05-07 18:23
 */
@Service
@Slf4j
public class AdminInfoServiceImpl implements AdminInfoService {

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
        restaurantLabelQueryWrapper.eq("has_delete", false);
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
    public Boolean addRestaurant(RestaurantOverView restaurantOverView) {
        RestaurantInfo restaurantInfo = new RestaurantInfo();

        restaurantInfo.setRestaurantName(restaurantOverView.getRestaurantName());
        restaurantInfo.setRestaurantTag(restaurantOverView.getRestaurantTag());
        restaurantInfo.setRestaurantPosition(restaurantOverView.getRestaurantPosition());
        restaurantInfo.setRestaurantImage(restaurantOverView.getRestaurantImage());
        restaurantInfo.setRestaurantProvince(restaurantOverView.getRestaurantProvince());
        restaurantInfo.setRestaurantCity(restaurantOverView.getRestaurantCity());
        restaurantInfo.setRestaurantBlock(restaurantOverView.getRestaurantBlock());

        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("restaurant_name", restaurantInfo.getRestaurantName())
                .eq("has_delete", false);
        RestaurantInfo check = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if(check != null){
            return false;
        }

        int result = restaurantInfoMapper.insert(restaurantInfo);
        if(result == 1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean deleteRestaurant(RestaurantOverView restaurantOverView) {


        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", restaurantOverView.getRestaurantName())
                .eq("restaurant_tag", restaurantOverView.getRestaurantTag())
                .eq("restaurant_position", restaurantOverView.getRestaurantPosition())
                .eq("restaurant_image", restaurantOverView.getRestaurantImage())
                .eq("restaurant_province", restaurantOverView.getRestaurantProvince())
                .eq("restaurant_city", restaurantOverView.getRestaurantCity())
                .eq("restaurant_block", restaurantOverView.getRestaurantBlock());

        RestaurantInfo delete = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if(delete != null){

            delete.setHasDelete(true);
            delete.setModTime(new Date());
            restaurantInfoMapper.update(delete, restaurantInfoQueryWrapper);
            String restaurantId = delete.getRestaurantId();

            QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
            foodQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);
            List<Food> foodList = foodMapper.selectList(foodQueryWrapper);
            Iterator<Food> foodIterator = foodList.iterator();
            while (foodIterator.hasNext()){
                Food food = foodIterator.next();
                food.setHasDelete(true);
                food.setModTime(new Date());
                QueryWrapper<Food> deleteQueryWrapper = new QueryWrapper<>();
                deleteQueryWrapper.eq("food_id", food.getFoodId())
                                .eq("has_delete", false);
                foodMapper.update(food, deleteQueryWrapper);
            }

            QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper = new QueryWrapper<>();
            restaurantLabelQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);
            List<RestaurantLabel> restaurantLabelList = restaurantLabelMapper
                    .selectList(restaurantLabelQueryWrapper);
            Iterator<RestaurantLabel> restaurantLabelIterator = restaurantLabelList.iterator();
            while (restaurantLabelIterator.hasNext()){
                RestaurantLabel restaurantLabel = restaurantLabelIterator.next();
                restaurantLabel.setHasDelete(true);
                restaurantLabel.setModTime(new Date());
                QueryWrapper<RestaurantLabel> deleteQueryWrapper = new QueryWrapper<>();
                deleteQueryWrapper.eq("label_id", restaurantLabel.getLabelId())
                        .eq("has_delete", false);
                restaurantLabelMapper.update(restaurantLabel, deleteQueryWrapper);
            }
            return true;
        }
        else {
            return false;
        }
    }
}
