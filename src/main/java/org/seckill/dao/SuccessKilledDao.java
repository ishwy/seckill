package org.seckill.dao;

import com.sun.org.glassfish.gmbal.ParameterNames;
import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Successkilled;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SuccessKilledDao {

    /**
     * 插入购买明细,过滤重复,通过索引完成
     * @param seckillId
     * @param userPhone
     * @return 插入的行数,是否>1,表示是否成功
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 通过id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    Successkilled queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone") long userPhone);

    /**
     * 查询所有秒杀成功的数据
     * @param offet
     * @param limit
     * @return
     */
    List<Successkilled> queryAll(@Param("offet") int offet,@Param("limit") Integer limit);
}
