package com.zzw;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestPing {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");

        // 清空当前数据库
        jedis.flushDB();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        jsonObject.put("name", "zhangsan");
        String result = jsonObject.toJSONString();


        // 开启事务
        Transaction multi = jedis.multi();
        try {
            multi.set("user1", result);
            multi.set("user2", result);
            // 代码抛出异常，执行失败！
            int i = 1 / 0;
            // 执行事务
            multi.exec();
        } catch (Exception e) {
            // 放弃事务
            multi.discard();
            e.printStackTrace();
        } finally {
            // 关闭连接
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();
        }
    }
}
