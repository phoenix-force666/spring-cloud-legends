package com.legends.cloud.file.util;

import com.legends.cloud.file.enums.FileTypeEnum;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;


public class FileTypeUtilsTest {
    
    private final static String DOC_PATH="xx.doc";
    private final static String IMG_PATH="xx.png";

    /**
     * @Author herion
     * @Description 获取图片文件类型
     * @Date 16:13 2019/8/31
     * @Param []
     * @return void
     **/
    @Test
    public void testGetImageFileType() throws Exception {
        String type =FileTypeUtils.getImageFileType(new File(IMG_PATH));
        System.out.println(type);
    }
    
    /**
     * 获取文件类型
     * @Author herion
     * @Description
     * @Date 16:13 2019/8/31
     * @Param []
     * @return void
     **/
    @Test
    public void testGetFileByFile() throws Exception {
        String type =FileTypeUtils.getFileByFile(new File(DOC_PATH));
        System.out.println(type);
    }
    
    /**
     * @Author herion
     * @Description 传入二进制数据获取文件类型
     * @Date 16:12 2019/8/31
     * @Param []
     * @return void
     **/
    @Test
    public void testGetFileTypeByStream() throws Exception {
        byte[] b = new byte[50];
        InputStream is=null;
        try {
            is = new FileInputStream(new File(DOC_PATH));
            is.read(b);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=is){
                is.close();
            }
        }
        String type=FileTypeUtils.getFileTypeByStream(b);
        System.out.println(type);
    }
    
    /**
     * 判断是否是图片
     * @Author herion
     * @Description
     * @Date 16:12 2019/8/31
     * @Param []
     * @return void
     **/
    @Test
    public void testIsImage() throws Exception {
       boolean isImage=FileTypeUtils.isImage(new File(DOC_PATH) );
        System.out.println(isImage);
        assertFalse(isImage);
    }

    /**
     * 通过文件路径判断文件类型
     * @Author herion
     * @Description
     * @Date 16:11 2019/8/31
     * @Param []
     * @return void
     **/
    @Test
    public void testGetFileTypeByPath() throws Exception {
        FileTypeEnum fileTypeEnums=FileTypeUtils.getFileTypeByPath(DOC_PATH);
        System.out.println(fileTypeEnums.getFileTypeName());

    }
}