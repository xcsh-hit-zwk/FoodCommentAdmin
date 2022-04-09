package com.example.foodcommentadmin;

import com.example.foodcommentadmin.mapper.UserMapper;
import com.example.foodcommentadmin.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FoodCommentAdminApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        //参数是一个Wrapper，是一个条件构造器，不用就写null
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    // 测试插入
    @Test
    public void testInsert(){
        User user = new User();
        user.setId("4");
        user.setUserId("4");
        user.setPassword("123456");
        user.setNickname("xcsh004");
        user.setCreateTime("4");
        user.setModTime("4");
        user.setHasLogin(false);
        user.setHasDelete(false);

        int result = userMapper.insert(user);
        System.out.println(result);
        System.out.println(user);
    }


}
