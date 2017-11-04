package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exporse;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.Successkilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by IDEA
 * Date: 2017/10/24
 * Time: 11:00
 * Author: shwy
 * SeckillService 接口实现
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //首先导入dao

    //这里使用spring托管Service服务
    //这里的Dao层, 已经通过mybatis和spring的整合,通过mapper自动把所需要的,装载到spring的IOC容器中
    @Resource
    private SeckillDao seckillDao;

    @Resource
    private SuccessKilledDao successKilledDao;

    //用户混淆加密md5
    private final String salt = "1sdq4545q6w4";

    //查询所有的秒杀记录
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    //查询所有秒杀成功记录
    public List<Successkilled> getSeckillEdList(int offet,Integer limit){
        return successKilledDao.queryAll(offet, limit);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /*
        在判断当前时间和秒杀时间是否相等
     */
    public Exporse exportSeckillUrl(long seckillId) {
        //先判断是否存在秒杀商品id
        Seckill seckill = seckillDao.queryById(seckillId);

        if (seckill == null) {
            return new Exporse(false, seckillId);
        }
        //不为空
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        //系统当前时间
        Date nowTime = new Date();
        //当前时间如果小于开始时间说明,秒杀还没开始
        //          大于开始时间说明,秒杀已经结束
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exporse(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        String md5 = getMd5(seckillId);
        return new Exporse(true, md5, seckillId);
    }

    /**
     * 设置md5加密
     *
     * @param seckillId
     * @return string md5
     */
    private String getMd5(long seckillId) {
        String base = seckillId + "/" + salt;

        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * 执行秒杀方法
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     * 使用注解控制事物方法的优点:
     *  1. 团队开发的编程风格统一
     *  2. 保证事物方法的执行时间尽可能短,不要穿插cache/http相关的请求 或者 剥离事物方法外部.
     *  3. 只有当多条数据库操作的时候,才使用事物控制
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {

        //判断用户的md5,和我们生产的md5是否匹配,如果不匹配,直接抛出异常
        if(md5 == null || !md5.equals(getMd5(seckillId))){
            throw new SeckillException("seckill data rewrite 2");
        }

        //执行秒杀: 减库存 + 写入信息

        //执行秒杀的时间,就是用户执行秒杀的时间
        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            //更新数量大于0的时候,才表示秒杀成功了
            if (updateCount <= 0) {
                //没更新到秒杀记录,秒杀结束
                throw new SeckillCloseException("seckill close 1");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //如果一个用户重复提交,数据库更新到等于0,抛出异常
                if (insertCount <= 0) {
                    //抛出重复异常
                    throw new RepeatKillException("secckill repeat 2");
                } else {
                    //秒杀成功,返回秒杀成功实体对象
                    Successkilled successkilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);

                    System.out.println("dddddddddddddddddddddddddddddddddddddd");
                                                            //使用枚举实现类
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successkilled);
                }
            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);

            //所有编译期异常 都转化为运行期异常,spring这是会完成rollback
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }
}
