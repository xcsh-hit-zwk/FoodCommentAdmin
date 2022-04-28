package com.example.foodcommentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.foodcommentadmin.pojo.AdminEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * description:
 * @author: zhangweikun
 * @create: 2022-4-28 9:06
 */
@Repository
@Mapper
public interface AdminMapper extends BaseMapper<AdminEntity> {
}
