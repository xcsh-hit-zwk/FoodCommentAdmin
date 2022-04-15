package com.example.foodcommentadmin;

import com.example.foodcommentadmin.common.R;
import com.example.foodcommentadmin.controller.UserController;
import com.example.foodcommentadmin.mapper.*;
import com.example.foodcommentadmin.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.LinkOption;
import java.util.List;

@SpringBootTest
class FoodCommentAdminApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserController userController;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    private RestaurantLabelMapper restaurantLabelMapper;

    @Test
    void contextLoads() {
        //参数是一个Wrapper，是一个条件构造器，不用就写null
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    // 测试Comment表读取
    @Test
    void testReadComment(){
        List<Comment> comments = commentMapper.selectList(null);
        comments.forEach(System.out::println);

    }

    // 测试Food表读取
    @Test
    void testReadFood(){
        List<Food> foods = foodMapper.selectList(null);
        foods.forEach(System.out::println);
    }

    // 测试RestaurantInfo表读取
    @Test
    void testReadRestaurantInfo(){
        List<RestaurantInfo> restaurantInfos = restaurantInfoMapper.selectList(null);
        restaurantInfos.forEach(System.out::println);
    }

    // 测试RestaurantLabel表读取
    @Test
    void testReadRestaurantLabel(){
        List<RestaurantLabel> restaurantLabels = restaurantLabelMapper.selectList(null);
        restaurantLabels.forEach(System.out::println);
    }

    // 测试Comment表插入
    @Test
    void testInsertComment(){

    }

    // 测试Food表插入
    @Test
    void testInsertFood(){

    }

    // 测试RestaurantInfo表插入
    @Test
    void testInsertRestaurantInfo(){

    }

    // 测试RestaurantLabel表插入
    @Test
    void testInsertRestaurantLabel(){

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

    @Test
    public void testSignUp(){
        User user = new User();
        user.setUserId("4");
        user.setPassword("123456");
        user.setNickname("xcsh005");
        user.setCreateTime("5");
        user.setModTime("5");
        user.setHasLogin(false);
        user.setHasDelete(false);

        R r = userController.signUp(user);
        System.out.println(r);
    }
}
