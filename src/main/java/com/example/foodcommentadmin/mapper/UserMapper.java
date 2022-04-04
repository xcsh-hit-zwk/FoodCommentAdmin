package com.example.foodcommentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.foodcommentadmin.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

// 在对应的Mapper上继承BastMapper
@Repository // 代表持久层
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 所有CRUD操作都已经编写完成了
    // 不需要写xml了

}
