package com.example.foodcommentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.foodcommentadmin.pojo.RestaurantLabel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zhangweikun
 * @create: 2022-04-15 10:44
 **/
@Repository
@Mapper
public interface RestaurantLabelMapper extends BaseMapper<RestaurantLabel> {
}
