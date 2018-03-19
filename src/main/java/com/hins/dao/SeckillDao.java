package com.hins.dao;

import com.hins.entity.Seckill;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SeckillDao {

    //减商品库存
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    //查询单条商品明细
    Seckill queryById(long seckillId);

    //查询所有秒杀商品, 用于list.jsp
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}