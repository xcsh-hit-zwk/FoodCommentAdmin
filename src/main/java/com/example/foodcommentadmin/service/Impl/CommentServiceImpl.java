package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.CommentMapper;
import com.example.foodcommentadmin.mapper.RestaurantInfoMapper;
import com.example.foodcommentadmin.mapper.UserCommentLikeMapper;
import com.example.foodcommentadmin.mapper.UserMapper;
import com.example.foodcommentadmin.pojo.*;
import com.example.foodcommentadmin.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author: zhangweikun
 * @create: 2022-05-19 18:22
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    private UserCommentLikeMapper userCommentLikeMapper;

    /**
     * @param commentAddEntity
     * @return comment_id
     */
    @Override
    public String addComment(CommentAddEntity commentAddEntity) {
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.eq("has_delete", false)
                .eq("restaurant_name", commentAddEntity.getRestaurantName());
        RestaurantInfo restaurantInfo = restaurantInfoMapper.selectOne(restaurantInfoQueryWrapper);
        if (restaurantInfo == null){
            return null;
        }
        String restaurantId = restaurantInfo.getRestaurantId();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("user_id", commentAddEntity.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            return null;
        }
        String userId = user.getId();

        Comment comment = new Comment();
        comment.setCommentLike(0);
        comment.setCommentInfo(commentAddEntity.getCommentInfo());
        comment.setRestaurantId(restaurantId);
        comment.setUserId(userId);
        int result = commentMapper.insert(comment);
        if (result == 1){
            return comment.getCommentId();
        }
        return null;
    }

    @Override
    public RestaurantComment getComment(String commentId) {
        RestaurantComment restaurantComment = new RestaurantComment();

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("has_delete", false)
                .eq("comment_id", commentId);
        Comment comment = commentMapper.selectOne(commentQueryWrapper);
        if (comment == null){
            return null;
        }

        restaurantComment.setCommentId(comment.getCommentId());
        restaurantComment.setCommentLike(comment.getCommentLike());
        restaurantComment.setCommentInfo(comment.getCommentInfo());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("has_delete", false)
                .eq("id", comment.getUserId());
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null){
            return null;
        }

        restaurantComment.setUsername(user.getUserId());
        restaurantComment.setUserImage(user.getUserImage());
        restaurantComment.setNickname(user.getNickname());

        return restaurantComment;
    }

    @Override
    public Boolean modifyComment(String commentId, String commentInfo) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("has_delete", false)
                .eq("comment_id", commentId);
        Comment comment = commentMapper.selectOne(commentQueryWrapper);
        if (comment == null){
            return false;
        }

        comment.setCommentInfo(commentInfo);
        comment.setModTime(new Date());
        int result = commentMapper.update(comment, commentQueryWrapper);
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteComment(String commentId) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("has_delete", false)
                .eq("comment_id", commentId);
        Comment comment = commentMapper.selectOne(commentQueryWrapper);
        if (comment == null){
            return false;
        }

        comment.setHasDelete(true);
        comment.setModTime(new Date());
        int result = commentMapper.update(comment, commentQueryWrapper);
        if (result == 1){
            QueryWrapper<UserCommentLikeEntity> userCommentLikeEntityQueryWrapper = new QueryWrapper<>();
            userCommentLikeEntityQueryWrapper.eq("has_delete", false)
                    .eq("comment_id", commentId);
            List<UserCommentLikeEntity> userCommentLikeEntityList = userCommentLikeMapper
                    .selectList(userCommentLikeEntityQueryWrapper);
            if (!!userCommentLikeEntityList.isEmpty()){
                // 清空点赞记录
                Iterator<UserCommentLikeEntity> userCommentLikeEntityIterator = userCommentLikeEntityList.iterator();
                while (userCommentLikeEntityIterator.hasNext()){
                    UserCommentLikeEntity userCommentLikeEntity = userCommentLikeEntityIterator.next();
                    QueryWrapper<UserCommentLikeEntity> delete = new QueryWrapper<>();
                    delete.eq("has_delete", false)
                            .eq("usercommentlike_id", userCommentLikeEntity.getUsercommentlikeId());
                    UserCommentLikeEntity temp = userCommentLikeMapper.selectOne(delete);
                    temp.setModTime(new Date());
                    temp.setHasDelete(true);
                    userCommentLikeMapper.update(temp, delete);
                }
            }
            return true;
        }
        return false;
    }
}
