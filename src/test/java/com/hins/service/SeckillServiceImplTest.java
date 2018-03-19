package com.hins.service;

import com.hins.dto.Exposer;
import com.hins.dto.SeckillExecution;
import com.hins.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckills=seckillService.getSeckillList();
        logger.info("seckill list={}", seckills);

    }

    @Test
    public void getById() throws Exception {
        long seckillId=1001;
        Seckill seckill=seckillService.getById(seckillId);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId=1000;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        logger.info("exposerl={}", exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long seckillId=1000;
        long userPhone=13476191876L;
        String md5="bf204e2683e7452aa7db1a50b5713bae";

        SeckillExecution seckillExecution=seckillService.executeSeckill(seckillId,userPhone,md5);

        logger.info("exposerl={}", seckillExecution);
    }

}