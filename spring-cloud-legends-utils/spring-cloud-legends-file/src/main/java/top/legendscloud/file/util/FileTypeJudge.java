package top.legendscloud.file.util;

import top.legendscloud.file.enums.FileGroupTypeEnum;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件校验
 * @Author herion
 * @Description
 * @Date 14:05 2019/8/31
 * @Param
 * @return
 **/
public final class FileTypeJudge {

    /**
     * Constructor
     */
    private FileTypeJudge() {
    }


   /**
    * 得到文件头
    * @Author herion
    * @Description
    * @Date 14:10 2019/8/31
    * @Param [is]
    * @return java.lang.String
    **/
    private static String getFileContent(InputStream is) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            is.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return ByteUtil.bytesToHexString(b);
    }

    /**
     *  判断文件类型
     * @Author herion
     * @Description
     * @Date 14:10 2019/8/31
     * @Param [is]
     * @return top.legendscloud.file.enums.FileTypeEnumEnum
     **/
    public static FileGroupTypeEnum getType(InputStream is) throws IOException {

        String fileHead = getFileContent(is);
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }
        fileHead = fileHead.toUpperCase();
        System.out.println("fileHead："+fileHead);
        FileGroupTypeEnum[] FileTypeEnums = FileGroupTypeEnum.values();

        for (FileGroupTypeEnum type : FileTypeEnums) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }
    /**
     *
     * @param value 表示文件类型
     * @return 1 表示图片,2 表示文档,3 表示视频,4 表示种子,5 表示音乐,7 表示其它
     * @return
     */
    public static Integer isFileTypeEnum(FileGroupTypeEnum value) {
        // 其他
        Integer type = 7;
        // 图片
        FileGroupTypeEnum[] pics = { FileGroupTypeEnum.JPEG, FileGroupTypeEnum.PNG, FileGroupTypeEnum.GIF, FileGroupTypeEnum.TIFF, FileGroupTypeEnum.BMP, FileGroupTypeEnum.DWG, FileGroupTypeEnum.PSD };

        FileGroupTypeEnum[] docs = { FileGroupTypeEnum.RTF, FileGroupTypeEnum.XML, FileGroupTypeEnum.HTML, FileGroupTypeEnum.CSS, FileGroupTypeEnum.JS, FileGroupTypeEnum.EML, FileGroupTypeEnum.DBX, FileGroupTypeEnum.PST, FileGroupTypeEnum.XLS_DOC, FileGroupTypeEnum.XLSX_DOCX, FileGroupTypeEnum.VSD,
                FileGroupTypeEnum.MDB, FileGroupTypeEnum.WPS, FileGroupTypeEnum.WPD, FileGroupTypeEnum.EPS, FileGroupTypeEnum.PDF, FileGroupTypeEnum.QDF, FileGroupTypeEnum.PWL, FileGroupTypeEnum.ZIP, FileGroupTypeEnum.RAR, FileGroupTypeEnum.JSP, FileGroupTypeEnum.JAVA, FileGroupTypeEnum.CLASS,
                FileGroupTypeEnum.JAR, FileGroupTypeEnum.MF, FileGroupTypeEnum.EXE, FileGroupTypeEnum.CHM };

        FileGroupTypeEnum[] videos = { FileGroupTypeEnum.AVI, FileGroupTypeEnum.RAM, FileGroupTypeEnum.RM, FileGroupTypeEnum.MPG, FileGroupTypeEnum.MOV, FileGroupTypeEnum.ASF, FileGroupTypeEnum.MP4, FileGroupTypeEnum.FLV, FileGroupTypeEnum.MID };

        FileGroupTypeEnum[] tottents = { FileGroupTypeEnum.TORRENT };

        FileGroupTypeEnum[] audios = { FileGroupTypeEnum.WAV, FileGroupTypeEnum.MP3 };

        FileGroupTypeEnum[] others = {};

        // 图片
        for (FileGroupTypeEnum FileTypeEnum : pics) {
            if (FileTypeEnum.equals(value)) {
                type = 1;
            }
        }
        // 文档
        for (FileGroupTypeEnum FileTypeEnum : docs) {
            if (FileTypeEnum.equals(value)) {
                type = 2;
            }
        }
        // 视频
        for (FileGroupTypeEnum FileTypeEnum : videos) {
            if (FileTypeEnum.equals(value)) {
                type = 3;
            }
        }
        // 种子
        for (FileGroupTypeEnum FileTypeEnum : tottents) {
            if (FileTypeEnum.equals(value)) {
                type = 4;
            }
        }
        // 音乐
        for (FileGroupTypeEnum FileTypeEnum : audios) {
            if (FileTypeEnum.equals(value)) {
                type = 5;
            }
        }
        return type;
    }
}