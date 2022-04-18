package com.example.foodcommentadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.foodcommentadmin.mapper.FoodMapper;
import com.example.foodcommentadmin.mapper.RestaurantInfoMapper;
import com.example.foodcommentadmin.mapper.RestaurantLabelMapper;
import com.example.foodcommentadmin.pojo.RestaurantInfo;
import com.example.foodcommentadmin.pojo.RestaurantLabel;
import com.example.foodcommentadmin.pojo.RestaurantOverView;
import com.example.foodcommentadmin.service.RestaurantOverViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 餐厅简略信息的Service类实现类
 * @author: zhangweikun
 * @create: 2022-04-17 14:51
 **/
@Slf4j
@Service
public class RestaurantOverViewServiceImpl implements RestaurantOverViewService {

    @Autowired
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    private RestaurantLabelMapper restaurantLabelMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public List<RestaurantOverView> totalRestaurantOverView(String city) {

        List<RestaurantOverView> restaurantOverViewList = new ArrayList<>();

        // 查饭店信息
        QueryWrapper<RestaurantInfo> restaurantInfoQueryWrapper = new QueryWrapper<>();
        restaurantInfoQueryWrapper.like("restaurant_city", city)
                .eq("has_delete", false);
        List<RestaurantInfo> restaurantInfos = restaurantInfoMapper
                .selectList(restaurantInfoQueryWrapper);

        if(restaurantInfos.equals(null)){
            return null;
        }

        // 查饭店标签
        QueryWrapper<RestaurantLabel> restaurantLabelQueryWrapper;
        Iterator<RestaurantInfo> infoIterator = restaurantInfos.iterator();
        List<RestaurantLabel> restaurantLabels;
        while (infoIterator.hasNext()){
            restaurantLabelQueryWrapper = new QueryWrapper<>();
            RestaurantInfo restaurantInfo = infoIterator.next();
            String restaurantId = restaurantInfo.getRestaurantId();
            restaurantLabelQueryWrapper.eq("restaurant_id", restaurantId)
                    .eq("has_delete", false);
            restaurantLabels = restaurantLabelMapper.selectList(restaurantLabelQueryWrapper);
            // 如果标签List是空的，说明饭店没标签，是有问题的查询，需要返回null
            if(restaurantLabels != null){
                // 这个sum的计算方法以后还要修改
                int sum = 0;
                RestaurantOverView restaurantOverView = new RestaurantOverView();

                restaurantOverView.setRestaurantId(restaurantInfo.getRestaurantId());
                restaurantOverView.setRestaurantName(restaurantInfo.getRestaurantName());
                restaurantOverView.setRestaurantTag(restaurantInfo.getRestaurantTag());
                restaurantOverView.setRestaurantPosition(restaurantInfo.getRestaurantPosition());
                restaurantOverView.setRestaurantImage(restaurantInfo.getRestaurantImage());
                restaurantOverView.setRestaurantProvince(restaurantInfo.getRestaurantProvince());
                restaurantOverView.setRestaurantCity(restaurantInfo.getRestaurantCity());
                restaurantOverView.setRestaurantBlock(restaurantInfo.getRestaurantBlock());

                Iterator<RestaurantLabel> labelIterator = restaurantLabels.iterator();
                while (labelIterator.hasNext()){
                    RestaurantLabel restaurantLabel = labelIterator.next();
                    sum += restaurantLabel.getLabelLike();
                }

                restaurantOverView.setLikes(sum);

                restaurantOverViewList.add(restaurantOverView);
            }
            else {
                return null;
            }
        }
        return restaurantOverViewList;
    }

    @Override
    public List<RestaurantOverView> nameRestaurantOverView(String name) {
        return null;
    }

    @Override
    public List<RestaurantOverView> tagRestaurantOverView(String tag) {
        return null;
    }

    @Override
    public List<RestaurantOverView> foodRestaurantOverView(String food) {
        return null;
    }

    @Override
    public List<RestaurantOverView> blockRestaurantOverView(String block) {
        return null;
    }
}
