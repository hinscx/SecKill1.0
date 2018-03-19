package com.hins.dao;

import com.hins.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {

    //插入购买明细,可过滤重复秒杀
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


    //根据秒杀商品的id查询明细SuccessKilled对象(该对象携带了Seckill秒杀产品对象)
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
