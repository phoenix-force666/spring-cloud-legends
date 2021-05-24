package top.legendscloud.apollo.util;
import org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI;
import org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 类EncryptUtil.java的实现描述：配置加密工具
 * @author xiongpan 2018年9月21日 下午3:19:43
 */
public class EncryptUtil {
    /**
     * 制表符、空格、换行符 PATTERN
     */
    private static Pattern BLANK_PATTERN = Pattern.compile("\\s*|\t|\r|\n");
    /**
     * 加密算法
     */
    private static String ALGORITHM = "PBEWithMD5AndDES";
    public static String getEncryptedParams(String input,String password) {
        //输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        PrintStream cacheStream = new PrintStream(byteArrayOutputStream);
        //更换数据输出位置
        System.setOut(cacheStream);
        //加密参数组装
        String[] args = {"input=" + input, "password=" + password, "algorithm=" + ALGORITHM};
        JasyptPBEStringEncryptionCLI.main(args);

        //执行加密后的输出
        String message = byteArrayOutputStream.toString();
        String str = replaceBlank(message);
        int index = str.lastIndexOf("-");
        //返回加密后的数据
        return str.substring(index + 1);
    }

    public static String getDecryptionParams(String input,String password) {
        //输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        PrintStream cacheStream = new PrintStream(byteArrayOutputStream);
        //更换数据输出位置
        System.setOut(cacheStream);
        //加密参数组装
        String[] args = {"input=" + input, "password=" + password, "algorithm=" + ALGORITHM};
        JasyptPBEStringDecryptionCLI.main(args);
        //执行加密后的输出
        String message = byteArrayOutputStream.toString();
        String str = replaceBlank(message);
        int index = str.lastIndexOf("-");
        //返回加密后的数据
        return str.substring(index + 1);
    }
    /**
     * 替换制表符、空格、换行符
     *
     * @param str
     * @return
     */
    private static String replaceBlank(String str) {
        String dest = "";

        if (!StringUtils.isEmpty(str)) {
            Matcher matcher = BLANK_PATTERN.matcher(str);
            dest = matcher.replaceAll("");
        }
        return dest;
    }

    public static void main(String[] args) {
        String input = "herion";
        String result=EncryptUtil.getEncryptedParams(input, "Q0Xv57uP82Dw9oJn3");
        System.out.println("--------->"+result);
    }

}