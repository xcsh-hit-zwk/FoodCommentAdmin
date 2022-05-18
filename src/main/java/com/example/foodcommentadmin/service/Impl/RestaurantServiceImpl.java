package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.*;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description: 餐厅设置修改各种信息接口的实现类
 * @author: zhangweikun
 * @create: 2022-04-23 09:26
 **/
// todo 同类型餐厅返回还没写，以及排序
// todo 招牌菜排序还没写
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

    @Autowired
    private UserCommentLikeMapper userCommentLikeMapper;

    @Autowired
    private UserFoodLikeMapper userFoodLikeMapper;

    private RestaurantInfo restaurantInfo;

    @Override
    public RestaurantDetail getRestaurantDetail(String restaurantName) {
        RestaurantDetail restaurantDetail;
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", restaurantName);
        restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if (restaurantInfo == null){
            return null;
        }
        // 填充RestaurantDetail
        restaurantDetail = fillRestaurantDetail(restaurantInfo);
        return restaurantDetail;
    }

    @Override
    public Boolean addFoodLike(String username, String foodName, String restaurantName) {
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
            String userId = getUserId(username);
            String foodId = getFoodId(foodName);
            String restaurantId = getRestaurantId(restaurantName);
            QueryWrapper<UserFoodLikeEntity> userFoodLikeEntityQueryWrapper = new QueryWrapper<>();
            userFoodLikeEntityQueryWrapper.eq("has_delete", false)
                    .eq("user_id", userId)
                    .eq("food_id", foodId)
                    .eq("restaurant_id", restaurantId);
            UserFoodLikeEntity userFoodLikeEntity = userFoodLikeMapper.selectOne(userFoodLikeEntityQueryWrapper);
            // 如果点赞过了，就清除点赞记录
            if (userFoodLikeEntity != null){
                userFoodLikeEntity.setHasDelete(true);
                userFoodLikeEntity.setModTime(new Date());
                userFoodLikeMapper.update(userFoodLikeEntity, userFoodLikeEntityQueryWrapper);
                return false;
            }

            userFoodLikeEntity = new UserFoodLikeEntity();
            userFoodLikeEntity.setUserId(userId);
            userFoodLikeEntity.setFoodId(foodId);
            userFoodLikeEntity.setRestaurantId(restaurantId);

            result = userFoodLikeMapper.insert(userFoodLikeEntity);
            if (result == 1){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Boolean cancelFoodLike(String username, String foodName, String restaurantName) {
        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false)
                .eq("food_name", foodName);
        Food food = foodMapper.selectOne(foodQueryWrapper);
        if (food == null){
            return false;
        }

        food.setFoodLike(food.getFoodLike()-1);
        food.setModTime(new Date());
        int result = foodMapper.update(food, foodQueryWrapper);
        if (result == 1){
            String userId = getUserId(username);
            String foodId = getFoodId(foodName);
            String restaurantId = getRestaurantId(restaurantName);
            QueryWrapper<UserFoodLikeEntity> userFoodLikeEntityQueryWrapper = new QueryWrapper<>();
            userFoodLikeEntityQueryWrapper.eq("has_delete", false)
                    .eq("user_id", userId)
                    .eq("food_id", foodId)
                    .eq("restaurant_id", restaurantId);
            UserFoodLikeEntity userFoodLikeEntity = userFoodLikeMapper.selectOne(userFoodLikeEntityQueryWrapper);
            if (userFoodLikeEntity == null){
                return false;
            }

            userFoodLikeEntity.setHasDelete(true);
            userFoodLikeEntity.setModTime(new Date());

            result = userFoodLikeMapper.update(userFoodLikeEntity, userFoodLikeEntityQueryWrapper);
            if (result == 1){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Boolean addCommentLike(String commentId, String username, String restaurantName) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("has_delete", false)
                .eq("comment_id", commentId);
        Comment comment = commentMapper.selectOne(commentQueryWrapper);
        if (comment == null){
            return false;
        }

        int likes = comment.getCommentLike();
        comment.setCommentLike(likes+1);
        comment.setModTime(new Date());
        int result = commentMapper.update(comment, commentQueryWrapper);
        if (result == 1){

            // 记录点赞
            String userId = getUserId(username);
            String restaurantId = getRestaurantId(restaurantName);
            QueryWrapper<UserCommentLikeEntity> userCommentLikeEntityQueryWrapper = new QueryWrapper<>();
            userCommentLikeEntityQueryWrapper.eq("has_delete", false)
                    .eq("comment_id", commentId)
                    .eq("user_id", userId)
                    .eq("restaurant_id", restaurantId);
            UserCommentLikeEntity userCommentLikeEntity = userCommentLikeMapper
                    .selectOne(userCommentLikeEntityQueryWrapper);
            // 如果点赞过了，就清除点赞记录
            if (userCommentLikeEntity != null){
                userCommentLikeEntity.setHasDelete(true);
                userCommentLikeEntity.setModTime(new Date());
                userCommentLikeMapper.update(userCommentLikeEntity, userCommentLikeEntityQueryWrapper);
                return false;
            }

            userCommentLikeEntity = new UserCommentLikeEntity();
            userCommentLikeEntity.setCommentId(commentId);
            userCommentLikeEntity.setRestaurantId(restaurantId);
            userCommentLikeEntity.setUserId(userId);

            result = userCommentLikeMapper.insert(userCommentLikeEntity);
            if (result == 1){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Boolean cancelCommentLike(String commentId, String username, String restaurantName) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("has_delete", false)
                .eq("comment_id", commentId);
        Comment comment = commentMapper.selectOne(commentQueryWrapper);
        if (comment == null){
            return false;
        }

        int likes = comment.getCommentLike();
        comment.setCommentLike(likes-1);
        comment.setModTime(new Date());
        int result = commentMapper.update(comment, commentQueryWrapper);
        if (result == 1){
            // 记录点赞
            String userId = getUserId(username);
            String restaurantId = getRestaurantId(restaurantName);
            QueryWrapper<UserCommentLikeEntity> userCommentLikeEntityQueryWrapper = new QueryWrapper<>();
            userCommentLikeEntityQueryWrapper.eq("has_delete", false)
                    .eq("comment_id", commentId)
                    .eq("user_id", userId)
                    .eq("restaurant_id", restaurantId);
            UserCommentLikeEntity userCommentLikeEntity = userCommentLikeMapper
                    .selectOne(userCommentLikeEntityQueryWrapper);
            if (userCommentLikeEntity == null){
                return false;
            }

            userCommentLikeEntity.setModTime(new Date());
            userCommentLikeEntity.setHasDelete(true);
            result = userCommentLikeMapper.update(userCommentLikeEntity, userCommentLikeEntityQueryWrapper);
            if (result == 1){
                return true;
            }
            return false;
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
        // 按点赞数降序
        Collections.sort(foodOverViewList, ((o1, o2) -> {
            if (o1.getFoodLikes() < o2.getFoodLikes())
                return 1;
            else
                return -1;
        }));
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

        // 填充同类型餐厅，只填充六个
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_tag", restaurantInfo.getRestaurantTag());
        List<RestaurantInfo> restaurantInfoList = restaurantInfoMapper.selectList(restaurantInfoQueryWrapper);
        List<RestaurantOverView> restaurantOverViewList = fillSameTag(restaurantInfoList);
        restaurantDetail.setSameTagList(restaurantOverViewList);

        // 填充点赞过的评论
        QueryWrapper<UserCommentLikeEntity> userCommentLikeEntityQueryWrapper = new QueryWrapper<>();
        userCommentLikeEntityQueryWrapper.eq("has_delete", false)
                .eq("restaurant_id", restaurantId);
        List<UserCommentLikeEntity> userCommentLikeEntityList = userCommentLikeMapper
                .selectList(userCommentLikeEntityQueryWrapper);
        List<CommentLiked> commentLikedList = fillCommentLiked(userCommentLikeEntityList);
        restaurantDetail.setLikedCommentList(commentLikedList);

        // 填充点赞过的招牌菜
        QueryWrapper<UserFoodLikeEntity> userFoodLikeEntityQueryWrapper = new QueryWrapper<>();
        userFoodLikeEntityQueryWrapper.eq("has_delete", false)
                .eq("restaurant_id", restaurantId);
        List<UserFoodLikeEntity> userFoodLikeEntityList = userFoodLikeMapper
                .selectList(userFoodLikeEntityQueryWrapper);
        List<FoodLiked> foodLikedList = fillFoodLiked(userFoodLikeEntityList);
        restaurantDetail.setLikedFoodList(foodLikedList);

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
                    restaurantComment.setCommentId(comment.getCommentId());
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

    private List<RestaurantOverView> fillSameTag(List<RestaurantInfo> restaurantInfoList){
        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        if (restaurantInfoList.isEmpty()){
            return restaurantOverViewList;
        }

        Iterator<RestaurantInfo> restaurantInfoIterator = restaurantInfoList.iterator();
        while (restaurantInfoIterator.hasNext()){
            RestaurantInfo next = restaurantInfoIterator.next();
            RestaurantOverView restaurantOverView = new RestaurantOverView();

            restaurantOverView.setRestaurantName(next.getRestaurantName());
            restaurantOverView.setRestaurantTag(next.getRestaurantTag());
            restaurantOverView.setRestaurantPosition(next.getRestaurantPosition());
            restaurantOverView.setRestaurantImage(next.getRestaurantImage());
            restaurantOverView.setRestaurantProvince(next.getRestaurantProvince());
            restaurantOverView.setRestaurantCity(next.getRestaurantCity());
            restaurantOverView.setRestaurantBlock(next.getRestaurantBlock());

            // 填充点赞数
            QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
            foodQueryWrapper.eq("has_delete", false)
                    .eq("restaurant_id", next.getRestaurantId());
            List<Food> foodList = foodMapper.selectList(foodQueryWrapper);
            if (foodList.isEmpty()){
                restaurantOverView.setLikes(0);
            }
            else {
                Iterator<Food> foodIterator = foodList.iterator();
                int likes = 0;
                while (foodIterator.hasNext()){
                    likes += foodIterator.next().getFoodLike();
                }
                restaurantOverView.setLikes(likes);
            }

            restaurantOverViewList.add(restaurantOverView);
        }

        // 按点赞数降序
        Collections.sort(restaurantOverViewList, ((o1, o2) -> {
            if (o1.getLikes() < o2.getLikes())
                return 1;
            else
                return -1;
        }));

        // 截取前六个，并且去重
        int position = restaurantInfoList.indexOf(restaurantInfo);
        if (restaurantInfoList.size() > 6){
            if (position < 6){
                restaurantOverViewList.remove(position);
            }
            restaurantOverViewList.subList(0, 6);
        }

        return restaurantOverViewList;
    }

    private List<CommentLiked> fillCommentLiked(List<UserCommentLikeEntity> userCommentLikeEntityList){
        List<CommentLiked> commentLikedList = new ArrayList<>();

        if (userCommentLikeEntityList.isEmpty()){
            return commentLikedList;
        }

        Iterator<UserCommentLikeEntity> iterator = userCommentLikeEntityList.iterator();
        while (iterator.hasNext()){
            UserCommentLikeEntity next = iterator.next();
            CommentLiked commentLiked = new CommentLiked();

            commentLiked.setUsercommentlikeId(next.getUsercommentlikeId());
            commentLiked.setCommentId(next.getCommentId());
            commentLiked.setUserId(next.getUserId());
            commentLiked.setRestaurantId(next.getRestaurantId());

            // 获取用户
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("has_delete", false)
                            .eq("id", next.getUserId());
            User user = userMapper.selectOne(userQueryWrapper);
            // 获取餐厅
            QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
            restaurantInfoQueryWrapper.eq("has_delete", false)
                            .eq("restaurant_id", next.getRestaurantId());
            RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);

            if (user != null && restaurantInfo != null){
                // 填充数据
                commentLiked.setUsername(user.getUserId());
                commentLiked.setRestaurantName(restaurantInfo.getRestaurantName());
                commentLikedList.add(commentLiked);
            }
            else {
                // 清除错误数据
                QueryWrapper<UserCommentLikeEntity> userCommentLikeEntityQueryWrapper = new QueryWrapper<>();
                userCommentLikeEntityQueryWrapper.eq("has_delete", false)
                        .eq("usercommentlike_id", next.getUsercommentlikeId());
                next.setHasDelete(true);
                next.setModTime(new Date());
                userCommentLikeMapper.update(next, userCommentLikeEntityQueryWrapper);
            }
        }

        return commentLikedList;
    }

    private List<FoodLiked> fillFoodLiked(List<UserFoodLikeEntity> userFoodLikeEntityList){
        List<FoodLiked> foodLikedList = new ArrayList<>();

        if (userFoodLikeEntityList.isEmpty()){
            return foodLikedList;
        }

        Iterator<UserFoodLikeEntity> iterator = userFoodLikeEntityList.iterator();
        while (iterator.hasNext()){
            UserFoodLikeEntity next = iterator.next();
            FoodLiked foodLiked = new FoodLiked();

            foodLiked.setUserfoodlikeId(next.getUserfoodlikeId());
            foodLiked.setUserId(next.getUserId());
            foodLiked.setFoodId(next.getFoodId());
            foodLiked.setRestaurantId(next.getRestaurantId());
            // 获取用户
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("has_delete", false)
                    .eq("id", next.getUserId());
            User user = userMapper.selectOne(userQueryWrapper);
            // 获取招牌菜
            QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
            foodQueryWrapper.eq("has_delete", false)
                            .eq("food_id", next.getFoodId());
            Food food = foodMapper.selectOne(foodQueryWrapper);
            // 获取餐厅
            QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
            restaurantInfoQueryWrapper.eq("has_delete", false)
                            .eq("restaurant_id", next.getRestaurantId());
            RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
            // 填充数据
            if (user != null && food != null && restaurantInfo != null){
                foodLiked.setUsername(user.getUserId());

                String temp = food.getFoodName();
                String foodName = temp.substring(temp.indexOf("-") + 1);
                foodLiked.setFoodName(foodName);

                foodLiked.setRestaurantName(restaurantInfo.getRestaurantName());

                foodLikedList.add(foodLiked);
            }
            else {
                // 清除错误数据
                QueryWrapper<UserFoodLikeEntity> userFoodLikeEntityQueryWrapper = new QueryWrapper<>();
                userFoodLikeEntityQueryWrapper.eq("has_delete", false)
                        .eq("userfoodlike_id", next.getUserfoodlikeId());
                next.setHasDelete(true);
                next.setModTime(new Date());
                userFoodLikeMapper.update(next, userFoodLikeEntityQueryWrapper);
            }
        }

        return foodLikedList;
    }

    private String getRestaurantId(String restaurantName){
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", restaurantName);
        RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if (restaurantInfo == null){
            return null;
        }

        return restaurantInfo.getRestaurantId();
    }

    private String getUserId(String username){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("user_id", username);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            return null;
        }

        return user.getId();
    }

    private String getFoodId(String foodName){
        QueryWrapper<Food> foodQueryWrapper = new QueryWrapper<>();
        foodQueryWrapper.eq("has_delete", false)
                .eq("food_name", foodName);
        Food food = foodMapper.selectOne(foodQueryWrapper);
        if (food == null){
            return null;
        }

        return food.getFoodId();
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
