package com.example.foodcommentadmin.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 数据库时间戳更新handler
 * @author: zhangweikun
 * @create: 2022-04-16 16:40
 **/

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private Date date;
    private SimpleDateFormat simpleDateFormat;
    @Override
    public void insertFill(MetaObject metaObject) {
        date = new Date();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.info("start Time Stamp insert fill......");
        this.setFieldValByName("createTime", simpleDateFormat.format(date), metaObject);
        this.setFieldValByName("modTime", simpleDateFormat.format(date), metaObject);
        this.setFieldValByName("commentTime", simpleDateFormat.format(date), metaObject);

        log.info("start default value insert fill......");
        this.setFieldValByName("hasDelete", false, metaObject);
        this.setFieldValByName("hasLogin", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start Time Stamp update fill......");
        this.setFieldValByName("modTime", new Date(), metaObject);
    }
}
