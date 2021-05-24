package top.legendscloud.cache.config;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Author herion
 * @Description
 * @Date  2019/5/20
 * @Param 
 * @return 
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheExpire {
    /**
     * expire time, default 60s
     */
    @AliasFor("expire")
    long value() default 60L;

    /**
     * expire time, default 60s
     */
    @AliasFor("value")
    long expire() default 60L;

}