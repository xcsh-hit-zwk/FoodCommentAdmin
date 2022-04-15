package com.example.foodcommentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.foodcommentadmin.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zhangweikun
 * @create: 2022-04-15 10:41
 **/
@Repository
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
