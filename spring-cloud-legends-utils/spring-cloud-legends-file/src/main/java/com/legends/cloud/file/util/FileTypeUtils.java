package com.legends.cloud.file.util;


import com.legends.cloud.file.enums.FileTypeEnum;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 通过文件魔数来判断文件类型
 * 可以最大量避免通过后缀名来判断文件类型的漏洞
 *
 * @author 000125
 *
 */
public class FileTypeUtils {

    /**
     * 获取图片文件实际类型,若不是图片则返回null]
     * @param file
     * @return fileType
     */
    public final static String getImageFileType(File file) {
        ImageInputStream iis =null;
        if (isImage(file)) {
            try {
                iis = ImageIO.createImageInputStream(file);
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                if (!iter.hasNext()) {
                    return null;
                }
                ImageReader reader = iter.next();

                return reader.getFormatName();
            } catch (IOException e) {
                return null;
            } catch (Exception e) {
                return null;
            }finally {
                if(iis!=null){
                    try {
                        iis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取文件类型,包括图片,若格式不是已配置的,则返回null
     * @param file
     * @return fileType
     */
    public final static String getFileByFile(File file) {
        InputStream is=null;
        String filetype = null;
        byte[] b = new byte[50];
        try {
            is = new FileInputStream(file);
            is.read(b);
            filetype = getFileTypeByStream(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filetype;
    }

    /**
     * 通过数据流（二进制数据）判断文件类型
     * @param b
     * @return fileType
     */
    public final static String getFileTypeByStream(byte[] b) {
        String magicNumberCode = String.valueOf(ByteUtil.bytesToHexString(b));

        if (null!=magicNumberCode && !"".equals(magicNumberCode)) {
            return FileTypeEnum.getByMagicNumberCode(magicNumberCode.toUpperCase()).getFileTypeName();

        }
        return FileTypeEnum.NOT_EXITS_ENUM.getFileTypeName();
    }

    /**
     * isImage,判断文件是否为图片
     * @param file
     * @return true 是 | false 否
     */
    public static final boolean isImage(File file){
        boolean flag = false;
        try {
            BufferedImage bufreader = ImageIO.read(file);
            int width = bufreader.getWidth();
            int height = bufreader.getHeight();
            if(width==0 || height==0){
                flag = false;
            }else {
                flag = true;
            }
        } catch (IOException e) {
            flag = false;
        }catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 通过文件路径判断文件类型
     * @param path
     * @return
     * @throws IOException
     */
    public static FileTypeEnum getFileTypeByPath(String path) {
        // 获取文件头
        String magicNumberCode = null;
        try {
            magicNumberCode = getFileHeader(path);
        } catch (Exception e) {
            e.printStackTrace();
            return FileTypeEnum.NOT_EXITS_ENUM;
        }

        if (null!=magicNumberCode && !"".equals(magicNumberCode)) {
            return FileTypeEnum.getByMagicNumberCode(magicNumberCode.toUpperCase());

        }

        return FileTypeEnum.NOT_EXITS_ENUM;
    }


    /**
     * 通过文件路径获取文件头（即文件魔数）
     * @param path
     * @return
     * @throws IOException
     */
    public static String getFileHeader(String path) throws Exception {
        byte[] b = new byte[28];
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(path);
            inputStream.read(b, 0, 28);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return ByteUtil.bytesToHexString(b);
    }


}