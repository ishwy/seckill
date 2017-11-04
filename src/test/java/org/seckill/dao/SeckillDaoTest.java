package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，
 * 依赖spring-test，junit
 */
//junit启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件目录
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入DAO实现类依赖
    @Resource
    private SeckillDao seckillDao;


    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.print(seckill.getName());
        System.out.println(seckill);
        /*
        1000元秒杀iphone6Seckill
        {
            seckillId=1000,
            name='1000元秒杀iphone6',
            number=100,
            startTime=Mon Oct 23 14:34:44 GMT+08:00 2017,
            endTime=Mon May 23 00:00:00 GMT+08:00 2016,
            createTime=Mon Oct 23 14:34:39 GMT+08:00 2017
        }
        */
    }

    @Test
    public void queryAll() throws Exception {

        List<Seckill> seckills = seckillDao.queryAll(0,100);

        for (Seckill seckill1 : seckills){
            System.out.println(seckill1);
        }
    }

    @Test
    public void reduceNumber() throws Exception {
        Date killTime = new Date();

        int updateCount = seckillDao.reduceNumber(1000L,killTime);
        System.out.println("updateCount = "+updateCount);
    }
}