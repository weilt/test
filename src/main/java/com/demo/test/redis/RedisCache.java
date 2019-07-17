package com.demo.test.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.*;


@SuppressWarnings({"unchecked"})
public class RedisCache {
    private final static Logger log = LoggerFactory.getLogger(RedisCache.class);
    private ResourceLoader loader = new DefaultResourceLoader();
    private static JedisPool jedisPool;// 非切片连接池
    // 测试用
    public static String ip = "127.0.0.1";
    public static Integer port = 6379;
    public static String role = "maste";
    public static String pwd = "";

    public final static int db0 = 0;//redis区
    public final static int db1 = 1;//redis区
    public final static int db2 = 2;//redis区
    public final static int db3 = 3;//redis区
    public final static int db4 = 4;//redis区
    public final static int db5 = 5;//redis区
    public final static int db6 = 6;//redis区
    public final static int db7 = 7;//redis区
    public final static int db8 = 8;//redis区
    public final static int db9 = 9;//redis区
    public final static int db10 = 10;//redis区
    public final static int db11 = 11;//redis区
    public final static int db12 = 12;//redis区
    public final static int db13 = 13;//redis区
    public final static int db14 = 14;//redis区
    public final static int db15 = 15;//redis区

    public RedisCache() throws IOException {

        log.info("redis start connection");
        if (jedisPool == null) {

//                Properties properties = PropertiesUtils.getProperties("my-config.properties.properties");
//                ip = properties.getProperty("chat.redis.ip");
//                port = Integer.valueOf(properties.getProperty("chat.redis.port"));
//                pwd = properties.getProperty("chat.redis.pwd");

            initialPool();

        }
        log.info(" redis ip : {}, redis port : {}, redis role : {}, redis pwd : {}", RedisCache.ip, RedisCache.port, RedisCache.role, RedisCache.pwd);
        log.info("redis end connection");
    }


    public Jedis getJedisResource() {
        return jedisPool.getResource();
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }


    /**
     * 初始化非切片池
     */
    private void initialPool() {
		/*// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1000);
		config.setMaxIdle(100);
		config.setMaxWaitMillis(1000l);
		config.setTestOnBorrow(false);
		jedisPool = new JedisPool(config, ip, port,1000,pwd);*/

        try {
            JedisPoolConfig config = new JedisPoolConfig();
            // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
            config.setBlockWhenExhausted(true);
            // 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            // 是否启用pool的jmx管理功能, 默认true
            config.setJmxEnabled(true);
            // 最大空闲连接数, 默认8个 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(10);
            // 最大连接数, 默认8个
            config.setMaxTotal(500);
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            jedisPool = new JedisPool(config, ip, port, 3000, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                new RedisCache();
                if (jedisPool != null) {
                    Jedis resource = jedisPool.getResource();
                    return resource;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 设置值
     *
     * @param key      键
     * @param value    值
     * @param experice 失效时间
     * @return
     * @Title: set
     * @Description: TODO
     */
    public static boolean set(String key, String value, int experice) {
        Jedis jedis = getJedis();
        try {
            if ("OK".equals(jedis.set(key, value))) {
                jedis.expire(key, experice);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }


    /**
     * 设置值
     *
     * @param key   键
     * @param value 值
     * @return
     * @Title: set
     * @Description: TODO
     */
    public static boolean set(String key, String value) {
        Jedis jedis = getJedis();
        try {
            if ("OK".equals(jedis.set(key, value))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 设置值
     * 分区
     *
     * @param key        键
     * @param value      值
     * @param redisIndex 区
     * @return
     * @Title: set
     * @Description: TODO
     */
    public static boolean setpar(String key, String value, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            if ("OK".equals(jedis.set(key, value))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 设置值
     * 分区
     *
     * @param key        键
     * @param value      值
     * @param experice   失效时间
     * @param redisIndex 区
     * @return
     * @Title: set
     * @Description: TODO
     */
    public static boolean setpar(String key, String value, int experice, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            if ("OK".equals(jedis.set(key, value))) {
                jedis.expire(key, experice);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 设置值 h
     * 分区
     *
     * @param key        键
     * @param values     值
     * @param experice   失效时间
     * @param redisIndex 区
     * @Title: set
     * @Description: TODO
     * @returns
     */
    public static boolean hsetpar(String key, List<String> fields, List<String> values, int experice, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
//			if ("OK".equals(jedis.set(key, value))) {
//				jedis.expire(key, experice);
//				return true;
//			}
            for (int i = 0; i < values.size(); i++) {
                jedis.hset(key, fields == null ? i + "" : fields.get(i), values.get(i));
            }
            jedis.expire(key, experice);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 设置值 h
     * 分区
     *
     * @param key        键
     * @param values     值
     * @param experice   失效时间
     * @param redisIndex 区
     * @Title: set
     * @Description: TODO
     * @returns
     */
    public static <T> boolean hsetpars(String key, List<String> fields, List<Double> values, int experice, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
//			if ("OK".equals(jedis.set(key, value))) {
//				jedis.expire(key, experice);
//				return true;
//			}
            for (int i = 0; i < values.size(); i++) {
                jedis.hset(key, fields == null ? i + "" : fields.get(i), values.get(i) + "");
//                jedis.hset()
            }
            jedis.expire(key, experice);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 获取值h
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static Map<String, String> hgetgey(String key, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            Map<String, String> hgetAll = jedis.hgetAll(key);
            return hgetAll;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 获取值h
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static String hget(String key, String field, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            String value = jedis.hget(key, field);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 删除值h
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static void hdel(String key, String field, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            jedis.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static String get(String key) {
        Jedis jedis = getJedis();
        try {

            String value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }


    /**
     * 获取值
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static String get(String key, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            String value = jedis.get(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }


    /**
     * 删除值
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static void del(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 删除值 相似删除
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static void delRegex(String key, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {


            jedis = jedisPool.getResource();
            jedis.select(redisIndex == null ? 0 : redisIndex);
            Set<String> set = jedis.keys(key + "*");
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                System.out.println(keyStr);
                jedis.del(keyStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 删除值 相似删除
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static void delRegex(String key) {
        Jedis jedis = getJedis();
        try {
            jedis = jedisPool.getResource();
            Set<String> set = jedis.keys(key + "*");
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                jedis.del(keyStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 删除值
     *
     * @param key
     * @return
     * @Title: get
     * @Description: TODO
     */
    public static void del(String key, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public static List<String> getRegex(String key, Integer redisIndex) {
        List<String> tokens = new ArrayList<>();
        Jedis jedis = getJedis();
        try {
            jedis = jedisPool.getResource();
            jedis.select(redisIndex == null ? 0 : redisIndex);

//			List<String> lrange = jedis.lrange(key, 0, 10);
            Set<String> set = jedis.keys(key);
            log.info(key);
            for (String s : set) {
                String tk = jedis.get(s);
                log.info(s);
                tokens.add(tk);
            }
            return tokens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    /**
     * 添加map到redis
     *
     * @param key
     * @param map
     * @param redisIndex 数据库索引
     */
    public static void putMap(String key, Map<String, String> map, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            jedis.hmset(key, map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 追加到map
     *
     * @param key
     * @param field
     * @param value
     * @param redisIndex
     */
    public static void appendMap(String key, String field, String value, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == null ? 0 : redisIndex);
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

//    /**
//     * 删除map指定的值
//     *
//     * @param key
//     * @param redisIndex
//     * @param field      1-N个map的key
//     */
//    public static void removeMap(String key, Integer redisIndex, String... field) {
//        Jedis jedis = getJedis();
//        try {
//            jedis.select(redisIndex == null ? 0 : redisIndex);
//            jedis.hdel(key, field);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (jedis != null)
//                jedis.close();
//        }
//    }

//    /**
//     * 获取值
//     *
//     * @param key
//     * @param field
//     * @return
//     */
//    public static String getMap(String key, String field, Integer redisIndex) {
//        Jedis jedis = getJedis();
//        try {
//            jedis.select(redisIndex == null ? 0 : redisIndex);
//            String value = jedis.hget(key, field);
//            return value;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (jedis != null)
//                jedis.close();
//        }
//    }

//    /**
//     * 获取map
//     *
//     * @param key
//     * @return
//     */
//    public static Map<String, String> getMap(String key, Integer redisIndex) {
//        Jedis jedis = getJedis();
//        try {
//            jedis.select(redisIndex == null ? 0 : redisIndex);
//            Map<String, String> map = jedis.hgetAll(key);
//            return map;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (jedis != null)
//                jedis.close();
//        }
//    }

    /**
     * 设置值 （LIST）
     *
     * @param key     键
     * @param value   值
     * @param expired 过期时间
     * @param index   分区
     * @param <T>
     */
    public static <T> void lSet(String key, List<T> value, Long expired, Integer index) {
        Jedis jedis = getJedis();
        try {
            jedis.select(index == null ? 0 : index);
            for (T t : value) {
                jedis.lpush(key, t.toString());
            }
            if (expired > 0) jedis.expire(key, expired.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取list的值
     *
     * @param key
     * @param index
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T lGet(String key, Integer index, Class<T> tClass) {
        Jedis jedis = getJedis();
        try {
            jedis.select(index == null ? 0 : index);
            String s = jedis.get(key);
            T ts = (T) JSON.parseArray(s).toJavaList(tClass);
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 获取list的长度
     *
     * @param key
     * @param index
     * @return
     */
    public static Long lSize(String key, Integer index) {
        Jedis jedis = getJedis();
        try {
            jedis.select(index == 0 ? 0 : index);
            Long llen = jedis.llen(key);
            return llen;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 删除指定索引位置的值
     *
     * @param key
     * @param index
     * @param dbIndex
     * @return
     */
    public static String lDel(String key, Integer index, Integer dbIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(dbIndex == 0 ? 0 : dbIndex);
            String ltrim = jedis.ltrim(key, index, -1);
            return ltrim;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    public static boolean isExists(String key, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == 0 ? 0 : redisIndex);
            return jedis.exists(key);
        } catch (Exception e) {
            return false;
        } finally {
            jedis.close();
        }
    }

    public static Set<String> zGet(String key, Integer start, Integer end, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == 0 ? 0 : redisIndex);
            Set<String> results = jedis.zrangeByScore(key, start, end);
            return results;
        } catch (Exception e) {
            return null;
        } finally {
            jedis.close();
        }
    }

    public static boolean zSet(String key, String value, Integer scroe, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == 0 ? 0 : redisIndex);
            if ("OK".equals(jedis.zadd(key, scroe, value)))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        } finally {
            jedis.close();
        }
    }

    public static Long incr(String key, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == 0 ? 0 : redisIndex);
            if (jedis.exists(key)) {
                jedis.incr(key);
                return Long.valueOf(jedis.get(key));
            }else {
                jedis.set(key,"1");
                return Long.valueOf(jedis.get(key));
            }
        } catch (Exception e) {
            return -1L;
        } finally {
            jedis.close();
        }
//        return  -1L;
    }

    public static Long decr(String key, Integer redisIndex) {
        Jedis jedis = getJedis();
        try {
            jedis.select(redisIndex == 0 ? 0 : redisIndex);
            if (jedis.exists(key)) {
                if (Integer.valueOf(jedis.get(key)) > 0) {
                    jedis.decr(key);
                    return Long.valueOf(jedis.get(key));
                } else {
                    jedis.del(key);
                    return -1L;
                }
            }
        } catch (Exception e) {
            return -1L;
        } finally {
            jedis.close();
        }
        return  -1L;
    }
}
