package org.seckill.dao;


import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * 基于mybatis 的mapper自动实现接口
 * DAO层就相当于定义    查询 sql 的一个方法和方法所要的参数
 *
 * 2017-11-1 13:40:59
 * DAO层, 只定义要实现的sql方法名. 具体实现通过Serviceimpl包实现
 */
public interface SeckillDao {

    /**
     * 减库存接口
     * @param seckillId
     * @param killTime
     * @return 更新行数是否>1,>1表示更新的记录行数  前面的int表示返回类型
     */
    int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offet
     * @param limit
     * @return
     */

    List<Seckill> queryAll(@Param("offet") int offet,@Param("limit") int limit);
}
