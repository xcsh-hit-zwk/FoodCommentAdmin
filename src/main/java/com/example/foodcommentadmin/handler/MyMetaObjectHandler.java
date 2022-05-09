package com.example.foodcommentadmin.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("modTime", new Date(), metaObject);
        this.setFieldValByName("commentTime", new Date(), metaObject);

        log.info("start default value insert fill......");
        this.setFieldValByName("hasDelete", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }


}
