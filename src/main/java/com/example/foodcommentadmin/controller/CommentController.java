package com.example.foodcommentadmin.controller;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.enums.ResultCode;
import com.example.foodcommentadmin.pojo.CommentAddEntity;
import com.example.foodcommentadmin.pojo.RestaurantComment;
import com.example.foodcommentadmin.pojo.SearchInfo;
import com.example.foodcommentadmin.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhangweikun
 * @create: 2022-05-19 18:32
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/Comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/AddComment")
    public R addComment(@Validated @RequestBody CommentAddEntity commentAddEntity){
        String commentId = commentService.addComment(commentAddEntity);
        if (commentId != null){
            return R.ok().data(commentId);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

    // searchWay = "GetComment" searchInfo = "%comment_id"
    @PostMapping("/GetComment")
    public R getComment(@Validated @RequestBody SearchInfo searchInfo){
        String searchWay = searchInfo.getSearchWay();
        String info = searchInfo.getInfo();
        if (!searchWay.equals("GetComment")){
            return R.setResult(ResultCode.WRONG_SEARCH);
        }
        RestaurantComment restaurantComment = commentService.getComment(info);
        if (restaurantComment != null){
            return R.ok().data(restaurantComment);
        }
        return R.setResult(ResultCode.EMPTY_SET);
    }

}
