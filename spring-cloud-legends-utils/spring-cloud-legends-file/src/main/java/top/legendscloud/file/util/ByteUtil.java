package top.legendscloud.file.util;

/**
 * @author herion
 * @version V1.0
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date ${date}
 */
public class ByteUtil {

    /**
     * 将文件头转换成16进制字符串
     * @Author herion
     * @Description
     * @Date 14:06 2019/8/31
     * @Param [src]
     * @return java.lang.String
     **/
    public static String bytesToHexString(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}