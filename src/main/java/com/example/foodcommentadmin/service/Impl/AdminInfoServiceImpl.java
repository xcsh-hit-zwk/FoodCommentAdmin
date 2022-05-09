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

            String restaurantId = restaurantInfo.getRestaurantId();
            foodQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);

            List<Food> foodList = new ArrayList<>();

            foodList = foodMapper.selectList(foodQueryWrapper);

            // 添加点赞数
            int likes = 0;
            if(!foodList.isEmpty()){

                Iterator<Food> foodIterator = foodList.iterator();

                while (foodIterator.hasNext()){
                    Food food = foodIterator.next();
                    likes += food.getFoodLike();
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

            String temp = food.getFoodName();
            String foodName = temp.substring(temp.indexOf("-") + 1);
            foodOverView.setFoodName(foodName);
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

            String temp = restaurantLabel.getLabelInfo();
            String labelName = temp.substring(temp.indexOf("-") + 1);
            labelOverView.setLabelName(labelName);

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

    @Override
    public String getUpdateRestaurantId(RestaurantOverView restaurantOverView) {

        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", restaurantOverView.getRestaurantName());

        RestaurantInfo get = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if(get == null){
            return null;
        }
        return get.getRestaurantId();
    }

    @Override
    public Boolean updateRestaurant(String restaurantId, RestaurantOverView restaurantOverView) {

        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("restaurant_id", restaurantId)
                .eq("has_delete", false);
        RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);

        if(restaurantInfo == null){
            return false;
        }

        restaurantInfo.setModTime(new Date());
        restaurantInfo.setRestaurantName(restaurantOverView.getRestaurantName());
        restaurantInfo.setRestaurantTag(restaurantOverView.getRestaurantTag());
        restaurantInfo.setRestaurantPosition(restaurantOverView.getRestaurantPosition());
        restaurantInfo.setRestaurantImage(restaurantOverView.getRestaurantImage());
        restaurantInfo.setRestaurantProvince(restaurantOverView.getRestaurantProvince());
        restaurantInfo.setRestaurantCity(restaurantOverView.getRestaurantCity());
        restaurantInfo.setRestaurantBlock(restaurantOverView.getRestaurantBlock());

        int result = restaurantInfoMapper.update(restaurantInfo, restaurantInfoQueryWrapper);
        if(result == 1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean addFood(FoodOverView foodOverView) {

        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false)
                .eq("food_name", foodOverView.getRestaurantName() + "-" + foodOverView.getFoodName());
        Food food = foodMapper.selectOne(foodQueryWrapper);
        if(food != null){
            return false;
        }

        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", foodOverView.getRestaurantName());
        RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if(restaurantInfo == null){
            return false;
        }

        String restaurantId = restaurantInfo.getRestaurantId();
        Food insert = new Food();
        insert.setFoodName(foodOverView.getRestaurantName() + "-" + foodOverView.getFoodName());
        insert.setRestaurantId(restaurantId);
        insert.setFoodPicture(foodOverView.getFoodImage());
        insert.setFoodLike(foodOverView.getFoodLikes());

        int result = foodMapper.insert(insert);
        if(result == 1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean deleteFood(FoodOverView foodOverView) {
        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false)
                .eq("food_name", foodOverView.getRestaurantName() + "-" + foodOverView.getFoodName());
        Food food = foodMapper.selectOne(foodQueryWrapper);
        if(food == null){
            return false;
        }

        food.setModTime(new Date());
        food.setHasDelete(true);
        int result = foodMapper.update(food, foodQueryWrapper);
        if(result == 1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String getUpdateFoodId(FoodOverView foodOverView) {

        Food get = new Food();
        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false)
                .eq("food_name", foodOverView.getRestaurantName()
                        + "-" + foodOverView.getFoodName());
        get = foodMapper.selectOne(foodQueryWrapper);

        if(get == null){
            return null;
        }

        return get.getFoodId();
    }

    @Override
    public Boolean updateFood(String foodId, FoodOverView foodOverView) {

        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false)
                .eq("food_id", foodId);
        log.info("food_id : " + foodId);
        Food food = foodMapper.selectOne(foodQueryWrapper);
        if(food == null){
            log.info("没查到更新");
            return false;
        }

        food.setModTime(new Date());
        food.setFoodName(foodOverView.getRestaurantName() + "-" + foodOverView.getFoodName());
        food.setFoodLike(foodOverView.getFoodLikes());
        food.setFoodPicture(foodOverView.getFoodImage());

        log.info("开始更新");
        int result = foodMapper.update(food, foodQueryWrapper);
        if (result == 1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean addLabel(LabelOverView labelOverView) {

        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper = new QueryWrapper<>();
        restaurantLabelQueryWrapper.eq("has_delete", false)
                .eq("label_info", labelOverView.getLabelName());
        RestaurantLabel restaurantLabel = restaurantLabelMapper.selectOne(restaurantLabelQueryWrapper);
        if(restaurantLabel != null){
            log.info("标签重复");
            return false;
        }

        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", labelOverView.getRestaurantName());
        RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if(restaurantInfo == null){
            log.info("没找到对应餐厅");
            return false;
        }

        String restaurantId = restaurantInfo.getRestaurantId();
        RestaurantLabel insert = new RestaurantLabel();
        insert.setLabelInfo(labelOverView.getRestaurantName() + "-" + labelOverView.getLabelName());
        insert.setRestaurantId(restaurantId);
        int result = restaurantLabelMapper.insert(insert);
        if(result == 1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean deleteLabel(LabelOverView labelOverView) {
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper = new QueryWrapper<>();
        restaurantLabelQueryWrapper.eq("has_delete", false)
                .eq("label_info", labelOverView.getRestaurantName() + "-" + labelOverView.getLabelName());
        RestaurantLabel restaurantLabel = restaurantLabelMapper.selectOne(restaurantLabelQueryWrapper);
        if(restaurantLabel == null){
            return false;
        }

        restaurantLabel.setHasDelete(true);
        int result = restaurantLabelMapper.update(restaurantLabel, restaurantLabelQueryWrapper);
        if(result == 1){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String getUpdateLabelId(LabelOverView labelOverView) {
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper = new QueryWrapper<>();
        restaurantLabelQueryWrapper.eq("has_delete", false)
                .eq("label_info", labelOverView.getRestaurantName() + "-" + labelOverView.getLabelName());
        RestaurantLabel restaurantLabel = restaurantLabelMapper.selectOne(restaurantLabelQueryWrapper);
        if (restaurantLabel == null){
            return null;
        }

        return restaurantLabel.getLabelId();
    }

    @Override
    public Boolean updateLabel(String labelId, LabelOverView labelOverView) {
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper = new QueryWrapper<>();
        restaurantLabelQueryWrapper.eq("has_delete", false)
                .eq("label_id", labelId);
        RestaurantLabel restaurantLabel = restaurantLabelMapper.selectOne(restaurantLabelQueryWrapper);
        if(restaurantLabel == null){
            return false;
        }

        restaurantLabel.setLabelInfo(labelOverView.getRestaurantName() + "-" + labelOverView.getLabelName());
        restaurantLabel.setModTime(new Date());

        int result = restaurantLabelMapper.update(restaurantLabel, restaurantLabelQueryWrapper);
        if(result == 1){
            return true;
        }
        else {
            return false;
        }
    }
}
