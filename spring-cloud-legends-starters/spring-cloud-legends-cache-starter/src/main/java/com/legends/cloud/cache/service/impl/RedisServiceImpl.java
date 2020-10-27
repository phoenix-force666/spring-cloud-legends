package com.legends.cloud.cache.service.impl;

import com.legends.cloud.cache.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author herion
 * @Description RedisTemplate 封装操作
 * @Date  2019/5/17
 * @Param 
 * @return 
 **/

@Service
@Slf4j
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean set(String key, Object value) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception var5) {
            log.error("{}",var5);
        }

        return result;
    }

    @Override
    public boolean set(String key, Object value, Long expireTime) {
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
            operations.set(key, value);
            this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
            result = true;
        } catch (Exception var6) {
            log.error("{}",var6);
        }

        return result;
    }

    @Override
    public void remove(String... keys) {
        String[] var2 = keys;
        int var3 = keys.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            this.remove(key);
        }

    }
    @Override
    public void removePattern(String pattern) {
        Set<Serializable> keys = this.redisTemplate.keys(pattern);
        if(keys.size() > 0) {
            this.redisTemplate.delete(keys);
        }

    }
    @Override
    public void remove(String key) {
        if(this.exists(key)) {
            this.redisTemplate.delete(key);
        }

    }

    @Override
    public boolean exists(String key) {
        return this.redisTemplate.hasKey(key).booleanValue();
    }

    @Override
    public Object get(String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = this.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    @Override
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = this.redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    @Override
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = this.redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    @Override
    public Set<Object> hmKeys(String key) {
        HashOperations<String, Object, Object> hash = this.redisTemplate.opsForHash();
        return hash.keys(key);
    }

    @Override
    public void hmDel(String masterKey, Object... hashKey) {
        HashOperations<String, Object, Object> hash = this.redisTemplate.opsForHash();
        hash.delete(masterKey, hashKey);
    }

    @Override
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = this.redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    @Override
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = this.redisTemplate.opsForList();
        return list.range(k, l, l1);
    }
    @Override
    public void add(String key, Object value) {
        SetOperations<String, Object> set = this.redisTemplate.opsForSet();
        set.add(key, new Object[]{value});
    }

    @Override
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = this.redisTemplate.opsForSet();
        return set.members(key);
    }

    @Override
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    @Override
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = this.redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    @Override
    public Object lPop(String k) {
        ListOperations<String, Object> list = this.redisTemplate.opsForList();
        return list.leftPop(k);
    }

    @Override
    public boolean expire(String key, long timeout) {
        return this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS).booleanValue();
    }

    @Override
    public long ttl(String key) {
        return this.redisTemplate.getExpire(key).longValue();
    }
}
