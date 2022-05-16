package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.*;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 餐厅设置修改各种信息接口的实现类
 * @author: zhangweikun
 * @create: 2022-04-23 09:26
 **/
@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private RestaurantLabelMapper restaurantLabelMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public RestaurantDetail getRestaurantDetail(String restaurantName) {
        RestaurantDetail restaurantDetail;
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", restaurantName);
        RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if (restaurantInfo == null){
            return null;
        }
        // 填充RestaurantDetail
        restaurantDetail = fillRestaurantDetail(restaurantInfo);
        return restaurantDetail;
    }

    @Override
    public Boolean addFoodLike(String foodName) {
        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false)
                .eq("food_name", foodName);
        Food food = foodMapper.selectOne(foodQueryWrapper);
        if (food == null){
            return false;
        }

        food.setFoodLike(food.getFoodLike()+1);
        food.setModTime(new Date());
        int result = foodMapper.update(food, foodQueryWrapper);
        if (result == 1){
            return true;
        }
        return false;
    }

    private RestaurantDetail fillRestaurantDetail(RestaurantInfo restaurantInfo){
        RestaurantDetail restaurantDetail = new RestaurantDetail();

        String restaurantId = restaurantInfo.getRestaurantId();

        restaurantDetail.setRestaurantId(restaurantInfo.getRestaurantId());
        restaurantDetail.setRestaurantName(restaurantInfo.getRestaurantName());
        restaurantDetail.setRestaurantTag(restaurantInfo.getRestaurantTag());
        restaurantDetail.setRestaurantBlock(restaurantInfo.getRestaurantBlock());
        restaurantDetail.setRestaurantPosition(restaurantInfo.getRestaurantPosition());

        // 填充招牌菜及点赞数
        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false)
                .eq("restaurant_id", restaurantId);
        List<Food> foodList = foodMapper.selectList(foodQueryWrapper);
        List<FoodOverView> foodOverViewList = fillFoodOverView(foodList, restaurantInfo.getRestaurantName());
        restaurantDetail.setFoodList(foodOverViewList);
        if (foodOverViewList.isEmpty()){
            restaurantDetail.setRestaurantLikes(0);
        }
        else {
            Iterator<FoodOverView> foodOverViewIterator = restaurantDetail.getFoodList().iterator();
            int likes = 0;
            while (foodOverViewIterator.hasNext()){
                likes += foodOverViewIterator.next().getFoodLikes();
            }
            restaurantDetail.setRestaurantLikes(likes);
        }

        // 填充标签
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper = new QueryWrapper<>();
        restaurantLabelQueryWrapper.eq("has_delete", false)
                .eq("restaurant_id", restaurantId);
        List<RestaurantLabel> restaurantLabelList = restaurantLabelMapper.selectList(restaurantLabelQueryWrapper);
        List<String> labelList = fillLabelOverView(restaurantLabelList);
        restaurantDetail.setLabelList(labelList);

        // 填充评论
        QueryWrapper<Comment> commentQueryWrapper =  new QueryWrapper<>();
        commentQueryWrapper.eq("has_delete", false)
                .eq("restaurant_id", restaurantId);
        List<Comment> commentList = commentMapper.selectList(commentQueryWrapper);
        List<RestaurantComment> restaurantCommentList = fillRestaurantComment(commentList);
        restaurantDetail.setCommentList(restaurantCommentList);

        return restaurantDetail;
    }

    private List<FoodOverView> fillFoodOverView(List<Food> foodList, String restaurantName){
        List<FoodOverView> foodOverViewList = new ArrayList<>();
        if (!foodList.isEmpty()){
            Iterator<Food> foodIterator = foodList.iterator();
            while (foodIterator.hasNext()){
                Food food = foodIterator.next();
                FoodOverView foodOverView = new FoodOverView();
                // 分割字符串
                String temp = food.getFoodName();
                String foodName = temp.substring(temp.indexOf("-") + 1);
                foodOverView.setFoodName(foodName);
                foodOverView.setFoodImage(food.getFoodPicture());
                foodOverView.setFoodLikes(food.getFoodLike());
                foodOverView.setRestaurantName(restaurantName);

                foodOverViewList.add(foodOverView);
            }
        }
        return foodOverViewList;
    }

    private List<String> fillLabelOverView(List<RestaurantLabel> restaurantLabelList){
        List<String> labelList = new ArrayList<>();
        if (!restaurantLabelList.isEmpty()){
            Iterator<RestaurantLabel> restaurantLabelIterator = restaurantLabelList.iterator();
            while (restaurantLabelIterator.hasNext()){
                RestaurantLabel restaurantLabel = restaurantLabelIterator.next();
                String temp = restaurantLabel.getLabelInfo();
                String labelName = temp.substring(temp.indexOf("-") + 1);
                labelList.add(labelName);
            }
        }
        return labelList;
    }

    private List<RestaurantComment> fillRestaurantComment(List<Comment> commentList){
        List<RestaurantComment> restaurantCommentList = new ArrayList<>();
        if(!commentList.isEmpty()){
            Iterator<Comment> commentIterator = commentList.iterator();
            while (commentIterator.hasNext()){
                Comment comment = commentIterator.next();

                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("has_delete", false)
                        .eq("id", comment.getUserId());
                User user = userMapper.selectOne(userQueryWrapper);
                if (user != null){
                    RestaurantComment restaurantComment = new RestaurantComment();
                    restaurantComment.setUsername(user.getUserId());
                    restaurantComment.setUserImage(user.getUserImage());
                    restaurantComment.setNickname(user.getNickname());
                    restaurantComment.setCommentInfo(comment.getCommentInfo());
                    restaurantComment.setCommentLike(comment.getCommentLike());

                    restaurantCommentList.add(restaurantComment);
                }
                // 清除错误数据
                else {
                    QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
                    commentQueryWrapper.eq("has_delete", false)
                            .eq("comment_id", comment.getCommentId());
                    comment.setHasDelete(true);
                    comment.setModTime(new Date());
                    int result = commentMapper.update(comment, commentQueryWrapper);
                    log.info("评论纠错结果: " + String.valueOf(result));
                }
            }
        }

        return restaurantCommentList;
    }


//     /** 向Food表中添加餐厅招牌菜
//     * @param restaurantName
//     * @param restaurantFoods
//     * @return 插入失败的招牌菜
//     */
//    @Override
//    public List<RestaurantFoodEntity> addFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods) {
//        List<RestaurantFoodEntity> failedInsertList = new ArrayList<>();
//        // 查询restaurant_name对应的restaurant_id
//        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
//        restaurantInfoQueryWrapper.eq("restaurant_name", restaurantName);
//        RestaurantInfo restaurantInfo = restaurantInfoMapper
//                .selectOne(restaurantInfoQueryWrapper);
//        String restaurantId = restaurantInfo.getRestaurantId();
//
//        // 通过前端传来的RestaurantFoodEntity对象和上面查到的restaurant_id
//        // 来拼出Food对象并插入进表中
//        Iterator<RestaurantFoodEntity> restaurantFoodEntityIterator = restaurantFoods.iterator();
//        while (restaurantFoodEntityIterator.hasNext()){
//            Food food = new Food();
//            RestaurantFoodEntity restaurantFoodEntity = restaurantFoodEntityIterator.next();
//
//            food.setFoodName(restaurantFoodEntity.getFoodName());
//            food.setRestaurantId(restaurantId);
//            food.setFoodLike(restaurantFoodEntity.getFoodLike());
//            food.setFoodPicture(restaurantFoodEntity.getFoodPicture());
//
//            // 通过food_name和restaurant_id来合并查询是否重复添加招牌菜
//            QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
//            foodQueryWrapper.eq("food_name", food.getFoodName())
//                    .eq("restaurant_id", food.getRestaurantId());
//            Food temp = foodMapper.selectOne(foodQueryWrapper);
//            if(temp == null){
//                foodMapper.insert(food);
//            }
//            else {
//                failedInsertList.add(restaurantFoodEntity);
//            }
//
//        }
//        return failedInsertList;
//    }
//
//    /**
//     * 删除对应招牌菜
//     * @param restaurantName
//     * @param restaurantFoods
//     * @return 返回删除失败的招牌菜
//     */
//    @Override
//    public List<RestaurantFoodEntity> deleteFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods) {
//        return null;
//    }
//
//    @Override
//    public List<RestaurantFoodEntity> updateFood(String restaurantName, List<RestaurantFoodEntity> restaurantFoods) {
//        return null;
//    }
//
//    @Override
//    public Boolean addLabel(String restaurantName, List<RestaurantLabel> restaurantLabels) {
//        return null;
//    }
//
//    @Override
//    public Boolean deleteLabel(String restaurantName, List<RestaurantLabel> restaurantLabels) {
//        return null;
//    }
//
//    @Override
//    public Boolean updateLabel(String restaurantName, List<RestaurantLabel> restaurantLabels) {
//        return null;
//    }

}
