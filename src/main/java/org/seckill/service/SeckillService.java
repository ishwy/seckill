package org.seckill.service;

import org.seckill.dto.Exporse;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.Successkilled;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Service业务实现接口
 * 方法定义粒度,参数,返回类型(return 类型/异常)
 */
public interface SeckillService {

    /**
     * 查询所有的秒杀记录
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询所有秒杀成功记录
     */
    List<Successkilled> getSeckillEdList(int offet,Integer limit);

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启是输出秒杀接口地址，
     * 否则输出系统和秒杀时间告诉用户
     *
     * @param seckillId
     */
    Exporse exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
