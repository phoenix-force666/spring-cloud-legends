package top.legendscloud.gateway.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class SqLinjectionRuleUtils {

    private static String badStrReg = "\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|\\/\\*.+?\\*\\/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)";

    private static Pattern sqlPattern = Pattern.compile(badStrReg, Pattern.CASE_INSENSITIVE);//整体都忽略大小写


    /**
     * get请求sql注入校验
     *
     * @param value
     * @return
     */
    public static boolean getRequestSqlKeyWordsCheck(String value) throws UnsupportedEncodingException {

        //参数需要url编码
        //这里需要将参数转换为小写来处理
        //不改变原值
        //value示例 order=asc&pageNum=1&pageSize=100&parentId=0
        String lowerValue = URLDecoder.decode(value, "UTF-8").toLowerCase();

        //获取到请求中所有参数值-取每个key=value组合第一个等号后面的值
        return Stream.of(lowerValue.split("\\&"))
                .map(kp -> kp.substring(kp.indexOf("=") + 1))
                .parallel()
                .anyMatch(param -> {
                    if (sqlPattern.matcher(param).find()) {
                        log.error("参数中包含不允许sql的关键词");
                        return true;
                    }
                    return false;
                });
    }


    /**
     * post请求sql注入校验
     *
     * @param value
     * @return
     */
    public static boolean postRequestSqlKeyWordsCheck(String value) {

        Object jsonObj = JSON.parse(value);
        if (jsonObj instanceof JSONObject) {
            JSONObject json = (JSONObject) jsonObj;
            Map<String, Object> map = json;

            //对post请求参数值进行sql注入检验
            return map.entrySet().stream().parallel().anyMatch(entry -> {

                //这里需要将参数转换为小写来处理
                String lowerValue = Optional.ofNullable(entry.getValue())
                        .map(Object::toString)
                        .map(String::toLowerCase)
                        .orElse("");


                if (sqlPattern.matcher(lowerValue).find()) {
                    log.error("参数[{}]中包含不允许sql的关键词", lowerValue);
                    return true;
                }
                return false;
            });
        } else {
            JSONArray json = (JSONArray) jsonObj;
            List<Object> list = json;
            //对post请求参数值进行sql注入检验
            return list.stream().parallel().anyMatch(obj -> {

                //这里需要将参数转换为小写来处理
                String lowerValue = Optional.ofNullable(obj)
                        .map(Object::toString)
                        .map(String::toLowerCase)
                        .orElse("");


                if (sqlPattern.matcher(lowerValue).find()) {
                    log.error("参数[{}]中包含不允许sql的关键词", lowerValue);
                    return true;
                }
                return false;
            });
        }
    }
}