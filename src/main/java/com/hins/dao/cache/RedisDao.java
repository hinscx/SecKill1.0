package com.hins.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.hins.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private  RuntimeSchema<Seckill> scheme = RuntimeSchema.createFrom(Seckill.class);

    private JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(long seckillId){
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //没实现内部序列化
                //get->byte[] -> 反序列化 ->Object(Seckill)
                //自定义序列化protostuff
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                    Seckill seckill = scheme.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes, seckill, scheme);
                    return seckill;
                }
            }

            finally {
                jedis.close();
            }

        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public String putSeckill(Seckill seckill) {
        try {
            Jedis jedis = jedisPool.getResource();

            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill, scheme,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;//只缓存一小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return  result;
            }
            finally {
                jedis.close();
            }

        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
        }


        return null;
    }



    //返回执行了补库存操作后item的库存数量
    public int addStock(long seckillId, int stocks) throws Exception {

        long totalStocks = 0;

        try {
            Jedis jedis = jedisPool.getResource();


            try {
                for(int i = 0; i < stocks; i++) {
                    totalStocks = jedis.lpush(Long.toString(seckillId) + "stock", "one");

                }
            }
            finally {
                jedis.close();
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }

        return (int)totalStocks;
    }

    //尝试去seckillId的库存list里取一个库存
    //返回0代表没有库存了
    //返回1代表成功抢到一个库存
    public int reduceStockByOne(long seckillId) throws Exception {
        int num = 0;

        try {
            Jedis jedis = jedisPool.getResource();

            try {
                String res = jedis.lpop(Long.toString(seckillId) + "stock");
                if(res.equals("one")) num++;
            }
            finally {
                jedis.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return num;
    }

    public int addOrder(long seckillId, long userPhone) throws Exception {

        long num = 0;

        try {
            Jedis jedis = jedisPool.getResource();
            try {
                num = jedis.sadd(Long.toString(seckillId) + "order", Long.toString(userPhone));
            }
            finally {
                jedis.close();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return (int)num;
    }










}
