package com.example.foodcommentadmin;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.controller.UserController;
import com.example.foodcommentadmin.mapper.UserMapper;
import com.example.foodcommentadmin.pojo.Account;
import com.example.foodcommentadmin.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FoodCommentAdminApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserController userController;

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

    // 测试登录检查
    @Test
    public void testLogin(){
        Account account = new Account();
        account.setUserId("1");
        account.setPassword("123456");
        R r = userController.login(account);
        System.out.println(r);
    }
}
