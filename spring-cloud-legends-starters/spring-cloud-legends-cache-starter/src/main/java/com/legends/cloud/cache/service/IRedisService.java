package com.legends.cloud.cache.service;

import java.util.List;
import java.util.Set;

/**
 * Created by zhanghai on 2019/5/17.
 */
public interface IRedisService {

    public boolean set(String key, Object value);

    public boolean set(String key, Object value, Long expireTime);

    public void remove(String... keys);

    public void remove(String key);

    public void removePattern(String pattern);

    public boolean exists(String key);

    public Object get(String key);

    public void hmSet(String key, Object hashKey, Object value);

    public Object hmGet(String key, Object hashKey);

    public Set<Object> hmKeys(String key);

    public void hmDel(String masterKey, Object... hashKey);

    public void lPush(String k, Object v);

    public List<Object> lRange(String k, long l, long l1);

    public void add(String key, Object value);

    public Set<Object> setMembers(String key);

    public void zAdd(String key, Object value, double scoure);

    public Set<Object> rangeByScore(String key, double scoure, double scoure1);

    public Object lPop(String k);

    public boolean expire(String key, long timeout);

    public long ttl(String key);
}
