package com.legends.cloud.file.aspect;

import com.legends.cloud.file.annotation.ValidFile;
import com.legends.cloud.file.exception.FileValidationException;
import com.legends.cloud.file.util.FileTypeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author herion
 * @version V1.0
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date ${date}
 */
@Aspect
public class FileTypeAspect {

        private static final Logger LOG = LoggerFactory.getLogger(FileTypeAspect.class);

        @Pointcut("@annotation(com.legends.cloud.file.annotation.ValidFile)")
        public void addAdvice(){
            LOG.info("addAdvice");
        }

        @Before("addAdvice()")
        public void before(JoinPoint joinPoint){
            Object[] args = joinPoint.getArgs();
            MultipartFile multipartFile=(MultipartFile)args[0];

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            ValidFile annotation = method.getAnnotation(ValidFile.class);
            this.initialize(annotation);
             boolean flag=this.isValid(multipartFile);
            LOG.info("flag:{}",flag);
        }

    @Autowired
    private MultipartProperties multipartProperties;

    private long maxSize = -1;
    private long minSize = 0;

    private ValidFile fileValid;
    private ArrayList<String> extension = new ArrayList<>();

    public void initialize(ValidFile constraintAnnotation) {
        this.fileValid = constraintAnnotation;
        //支持的文件扩展名集合
        Collections.addAll(extension, fileValid.value());
        Collections.addAll(extension, fileValid.endsWith());
        //文件上传的最大值
        if (constraintAnnotation.maxSize().equals(ValidFile.DEFAULT_MAXSIZE)) {
            //默认最大值采用Spring中配置的单文件大小，默认KB
            this.maxSize =multipartProperties.getMaxFileSize().toKilobytes();
        } else {
            this.maxSize = parseSize(constraintAnnotation.maxSize());
        }
        //文件上传的最小值
        this.minSize = parseSize(constraintAnnotation.minSize());
    }

    private long parseSize(String size) {
        Assert.hasLength(size, "Size must not be empty");
        size = size.toUpperCase();
        if (size.endsWith("KB")) {
            return Long.valueOf(size.substring(0, size.length() - 2)) * 1024;
        }
        if (size.endsWith("MB")) {
            return Long.valueOf(size.substring(0, size.length() - 2)) * 1024 * 1024;
        }
        return Long.valueOf(size);
    }


    public boolean isValid(MultipartFile multipartFile) {
        String fieldName = multipartFile.getName();
        //上传的文件是空的情况
        if (multipartFile.isEmpty()) {
            if (fileValid.allowEmpty()) {
                return true;
            }else{
                LOG.info("上传文件不能为空,参数名:{}",fieldName);
                throw new FileValidationException("FV99","上传文件不能为空,参数名："+fieldName);
            }
        }
        //上传的文件不是空的情况,验证其他条件是否成立
        //获取文件名,如果上传文件后缀名不区分大小写则统一转成小写
        String originalFilename = multipartFile.getOriginalFilename();

        //文件校验
        try {
            originalFilename= FileTypeUtils.getFileTypeByStream(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileValid.ignoreCase()) {
            originalFilename = originalFilename.toLowerCase();
        }
        if (extension.size() > 0 && extension.stream().noneMatch(originalFilename::endsWith)) {
            LOG.info("上传文件类型不符合要求,参数名:{}",fieldName);
            throw new FileValidationException("FV99","上传文件类型不符合要求,参数名:："+fieldName);
        }
        //上传文件字节数
        long size = multipartFile.getSize();
        if (size < this.minSize) {
            LOG.info("上传文件不能小于指定最小值,参数名:{}",fieldName);
            throw new FileValidationException("FV99","上传文件不能小于指定最小值,参数名:："+fieldName);
        }
        if (size > this.maxSize) {
            LOG.info("上传文件不能大于指定最大值,参数名:{}",fieldName);
            throw new FileValidationException("FV99","上传文件不能大于指定最大值,参数名:："+fieldName);
        }
        return true;
    }
}