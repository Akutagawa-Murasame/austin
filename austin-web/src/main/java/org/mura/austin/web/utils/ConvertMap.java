package org.mura.austin.web.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Akutagawa Murasame
 * @date 2022/6/13 21:34
 *
 * 项目的前端页面使用了amis，这个框架对于嵌套的json支持性不太好，所以只能先转换成map
 */
public class ConvertMap {
    /**
     * 将List的对象转换成Map(无嵌套)的List
     */
    public static <T> List<Map<String, Object>> flatList(List<T> param, List<String> fieldName) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (T t : param) {
            Map<String, Object> map = flatSingle(t, fieldName);

            result.add(map);
        }

        return result;
    }

    /**
     * 将单个对象转换成Map(无嵌套)
     * 只将fieldName中指定的字段解析为json加入
     * 其他字段按照Object加入
     */
    public static Map<String, Object> flatSingle(Object obj, List<String> fieldName) {
        Map<String, Object> result = MapUtil.newHashMap(32);
        Field[] fields = ReflectUtil.getFields(obj.getClass());

        for (Field field : fields) {
            if (fieldName.contains(field.getName())) {
                JSONObject jsonObject;
                Object value = ReflectUtil.getFieldValue(obj, field);

                if (value instanceof String) {
                    jsonObject = JSON.parseObject((String) value);
                } else {
                    jsonObject = JSONObject.parseObject(JSON.toJSONString(value));
                }

                result.putAll(jsonObject);
            }

            result.put(field.getName(), ReflectUtil.getFieldValue(obj, field));
        }

        return result;
    }
}
