package top.legendscloud.file.annotation;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 上传文件不能为空<br/>
 * 允许上传所有后缀格式的文件
 * 文件后缀名不区分大小写
 * 文件最大不超过springboot配置的文件大小
 * 文件最小不小于0 kb
 * @Author herion
 * @Description //TODO
 * @Date 11:33 2019/9/2
 * @Param
 * @return
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = MultipartFileValidator.class)
public @interface ValidFile {

    String DEFAULT_MAXSIZE = "-1";

    /**
     * AliasFor("endsWith")
     */
    String[] value() default {};

    /**
     * 支持的文件后缀类型,默认全部,AliasFor("value")
     */
    String[] endsWith() default {};

    /**
     * 文件后缀是否区分大小写
     */
    boolean ignoreCase() default true;

    /**
     * 上传的文件是否允许为空
     */
    boolean allowEmpty() default false;

    /**
     * Max file size. Values can use the suffixes "MB" or "KB" to indicate megabytes or
     * kilobytes respectively.<br/>
     * 默认不限制但必须小于等于SpringMVC中文件上传配置
     */
    String maxSize() default DEFAULT_MAXSIZE;

    /**
     * Min file size. Values can use the suffixes "MB" or "KB" to indicate megabytes or
     * kilobytes respectively. default byte
     */
    String minSize() default "0";

    String message() default "The uploaded file is not verified.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
