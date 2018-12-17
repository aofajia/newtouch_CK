package com.ruoyi.system.domain;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Redis操作工具类
 */
public class RedisUtil {
    //RedisConfig.getConnection().host
    //RedisConfig.getConnection().post
    private static final String IP = "127.0.0.1"; // ip
    private static final int PORT = 6379;        // 端口
    private static final String AUTH = "";          // 密码(原始默认是没有密码)
    private static int MAX_ACTIVE = 1024;       // 最大连接数
    private static int MAX_IDLE = 200;          // 设置最大空闲数
    private static int MAX_WAIT = 10000;        // 最大连接时间
    private static int TIMEOUT = 1000000;         // 超时时间
    private static boolean BORROW = true;         // 在borrow一个事例时是否提前进行validate操作
    private static JedisPool pool = null;
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * 初始化线程池
     */
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(BORROW);
        pool = new JedisPool(config, IP, PORT, TIMEOUT);
    }


    /**
     * 获取连接
     */
    public static synchronized Jedis getJedis() {
        try {
            if (pool != null) {
                return pool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.info("连接池连接异常");
            return null;
        }

    }

    /**
     * @param @param  key
     * @param @param  seconds
     * @param @return
     * @return boolean 返回类型
     * @Description:设置失效时间
     */
    public static void disableTime(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.expire(key, seconds);

        } catch (Exception e) {
            logger.debug("设置失效失败.");
        } finally {
            getColse(jedis);
        }
    }


    /**
     * @param @param  key
     * @param @param  obj
     * @param @return
     * @return boolean 返回类型
     * @Description:插入对象
     */
    public static boolean addObject(String key, Object obj) {

        Jedis jedis = null;
        String value = JSONObject.toJSONString(obj);
        try {
            jedis = getJedis();
            String code = jedis.set(key, value);
            if (code.equals("ok")) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("插入数据有异常.");
            return false;
        } finally {
            getColse(jedis);
        }
        return false;
    }


    /**
     * @param @param key
     * @param @param value
     * @return void 返回类型
     * @Description:存储key~value
     */

    public static boolean addValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            String code = jedis.set(key, value);
            if (code.equals("ok")) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("插入数据有异常.");
            return false;
        } finally {
            getColse(jedis);
        }
        return false;
    }


    /**
     * @param @param key
     * @param @param value
     * @return void 返回类型
     * @Description:存储key~value
     */

    public static boolean getValue(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String code = jedis.get(key);
            System.out.println(code);
            if (code.equals("ok")) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("插入数据有异常.");
            return false;
        } finally {
            getColse(jedis);
        }
        return false;
    }


    /**
     * @param @param  key
     * @param @return
     * @return boolean 返回类型
     * @Description:删除key
     */
    public static boolean delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long code = jedis.del(key);
            if (code > 1) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("删除key异常.");
            return false;
        } finally {
            getColse(jedis);
        }
        return false;
    }

    /**
     * @param @param jedis
     * @return void 返回类型
     * @Description:
     */

    public static void getColse(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * redis序列化存储对象
     *
     * @param object
     * @return
     */
    public static Boolean setObject(String key, Object object) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(key)) {
                jedis.del(key);
            }
            //存储redis的时候需要序列化
            jedis.set(key.getBytes(), serialize(object));
        } catch (Exception e) {
            logger.debug("存储对象失败");
            return false;
        } finally {
            getColse(jedis);
        }
        return true;
    }

    public static Object getObject(String key) {
        Jedis jedis = null;
        Object target = null;
        try {
            jedis = getJedis();
            byte[] entity = jedis.get(key.getBytes());
            //通过反序列化获取数据
            target = (Object) unserialize(entity);
        } catch (Exception e) {
            logger.debug("获取对象失败");
        } finally {
            getColse(jedis);
        }
        return target;
    }


    /**
     * Redis存储对象之前序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {

            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * Redis读取对象之前反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {

            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        }
        return null;
    }

}
