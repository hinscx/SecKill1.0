package com.hins.service;

import com.hins.dto.Exposer;
import com.hins.dto.SeckillExecution;
import com.hins.entity.Seckill;
import com.hins.exception.RepeatKillException;
import com.hins.exception.SeckillCloseException;
import com.hins.exception.SeckillException;

import java.util.List;

public interface SeckillService {

    // 查询全部的秒杀记录
    List<Seckill> getSeckillList();

     //查询单个秒杀记录
    Seckill getById(long seckillId);

    // 在秒杀开启时输出秒杀接口的地址，否则输出系统时间和秒杀时间
    Exposer exportSeckillUrl(long seckillId);

     // 执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
