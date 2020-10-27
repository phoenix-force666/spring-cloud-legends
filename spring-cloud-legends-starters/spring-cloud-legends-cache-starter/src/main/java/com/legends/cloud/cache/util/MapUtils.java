package com.legends.cloud.cache.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghai on 2018/8/1.
 */
public class MapUtils {
    /**
     * Obj 转换为 map
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> ObjToMap(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

}
