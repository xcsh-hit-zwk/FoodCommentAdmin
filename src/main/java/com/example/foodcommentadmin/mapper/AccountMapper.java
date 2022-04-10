package com.example.foodcommentadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.foodcommentadmin.pojo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
