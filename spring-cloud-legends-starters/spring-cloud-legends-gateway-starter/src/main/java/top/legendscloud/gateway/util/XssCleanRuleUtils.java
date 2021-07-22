package top.legendscloud.gateway.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class XssCleanRuleUtils {

  private final static Pattern[] scriptPatterns = {
          Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
          Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
          Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
          Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
          Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
          Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
          Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
          Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
          Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
  };

  private static String badStrReg = "\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|\\/\\*.+?\\*\\/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)";

  private static Pattern sqlPattern = Pattern.compile(badStrReg, Pattern.CASE_INSENSITIVE);//整体都忽略大小写

  /**
   * GET请求参数过滤
   *
   * @param value
   * @return
   */
  public static String xssGetClean(String value) throws UnsupportedEncodingException {

    //过滤xss字符集
    if (value != null) {
      value = value.replaceAll("\0|\n|\r", "");
      for (Pattern pattern : scriptPatterns) {
        value = pattern.matcher(value).replaceAll("");
      }
      value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    //sql关键字检查
    return cleanGetSqlKeyWords(value);

  }


  public static String xssPostClean(String value) {

    //过滤xss字符集
    if (value != null) {
      value = value.replaceAll("\0|\n|\r", "");
      for (Pattern pattern : scriptPatterns) {
        value = pattern.matcher(value).replaceAll("");
      }
      value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
    //sql关键字检查
    return cleanPostSqlKeyWords(value);

  }

  /**
   * 解析参数SQL关键字
   *
   * @param value
   * @return
   */
  private static String cleanGetSqlKeyWords(String value) throws UnsupportedEncodingException {

    //参数需要url编码
    //这里需要将参数转换为小写来处理
    //不改变原值
    //value示例 order=asc&pageNum=1&pageSize=100&parentId=0
    String lowerValue = URLDecoder.decode(value, "UTF-8").toLowerCase();

    //获取到请求中所有参数值-取每个key=value组合第一个等号后面的值
    boolean isContains = Stream.of(lowerValue.split("\\&"))
            .map(kp -> kp.substring(kp.indexOf("=") + 1))
            .parallel()
            .anyMatch(param -> {
              if (sqlPattern.matcher(param).find()) {
                log.error("参数中包含不允许sql的关键词");
                return true;
              }
              return false;
            });

    return isContains ? "forbid" : value;
  }


  /**
   * 解析参数SQL关键字
   *
   * @param value
   * @return
   */
  private static String cleanPostSqlKeyWords(String value) {

    JSONObject json = JSONObject.parseObject(value);
    Map<String, Object> map = json;
    Map<String, Object> mapjson = new HashMap<>();

    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String value1 = entry.getValue().toString();

      //这里需要将参数转换为小写来处理-不改变原值
      String lowerValue = value1.toLowerCase();

      if (sqlPattern.matcher(lowerValue).find()) {
        log.error("参数中包含不允许sql的关键词");
        value1 = "forbid";
        mapjson.put(entry.getKey(), value1);
        break;
      } else {
        mapjson.put(entry.getKey(), entry.getValue());
      }

    }

    return JSONObject.toJSONString(mapjson);
  }
}