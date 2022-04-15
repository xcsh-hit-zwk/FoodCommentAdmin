package com.example.foodcommentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.foodcommentadmin.pojo.Food;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zhangweikun
 * @create: 2022-04-15 10:43
 **/
@Repository
@Mapper
public interface FoodMapper extends BaseMapper<Food> {
}
