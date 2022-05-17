package com.example.foodcommentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.foodcommentadmin.pojo.UserCommentLikeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangweikun
 * @create: 2022-05-17 17:17
 */
@Repository
@Mapper
public interface UserCommentLikeMapper extends BaseMapper<UserCommentLikeEntity> {
}
