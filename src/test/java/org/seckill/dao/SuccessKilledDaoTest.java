package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Successkilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
/**
 * 配置spring和junit整合，
 * 依赖spring-test，junit
 */
//junit启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件目录
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    //依赖注入相关参数
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        //秒杀商品id
        long seckillId = 1000L;
        long userPhone = 13588035060L;

        int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);

        System.out.println("insertCount = " + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long seckillId = 1000L;
        long userPhone = 13588035060L;

        Successkilled successkillData = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);

        //successkillData 对象数据
        System.out.println(successkillData);

        //successkillData 获取seckill数据
        //因为在这里实体类但是，就已经定义了seckill属性对象了

        System.out.println(successkillData.getSeckill());

        /*
        Successkilled{seckillId=1000, userPhone=13588035060, state=0, createTime=Mon Oct 23 15:19:44 GMT+08:00 2017}
        Seckill{seckillId=1000, name='1000元秒杀iphone6', number=0, startTime=Mon Oct 23 15:02:12 GMT+08:00 2017, endTime=Sun Jan 21 12:52:02 GMT+08:00 2018, createTime=Mon Oct 23 14:34:39 GMT+08:00 2017}

         */

    }

}