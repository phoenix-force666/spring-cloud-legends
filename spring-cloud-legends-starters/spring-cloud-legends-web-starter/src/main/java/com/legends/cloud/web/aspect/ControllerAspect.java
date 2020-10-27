package com.legends.cloud.web.aspect;

import com.alibaba.fastjson.JSON;
import com.legends.cloud.common.base.ComReq;
import com.legends.cloud.common.enums.CommonEnumCode;
import com.legends.cloud.common.exception.ValidationException;
import com.legends.cloud.common.utils.StringUtil;
import com.legends.cloud.web.constant.SysConstant;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

//    @Autowired
//    private PrometheusMetricsBiz prometheusMetricsBiz;

    @Pointcut("@within(org.springframework.stereotype.Controller) && execution(public * *(..))")
    private void controllerPointcut() {
    }


    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && execution(public * *(..))")
    private void restControllerPointcut() {
    }
    @Before("restControllerPointcut() || controllerPointcut()")
    public void callBeforexxx(JoinPoint joinPoint) {
        //在被调用的方法之前执行这里的代码
//        log.info("在被调用的方法之前执行这里的代码");
    }


    @Around("restControllerPointcut() || controllerPointcut()")
    public Object restControllerPointcut(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        prometheusMetricsBiz.preHandle(request,attributes.getResponse());
        long beginTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        String description;
        if (annotation != null) {
            description = annotation.value();
        } else {
            description = "未定义接口";
        }


        //获取被拦截的方法名
        String methodName = method.getName();
        Object[] args = point.getArgs();
        Parameter[] parameters = method.getParameters();

        boolean isLogs=true;
        LogsInfo logsInfoAnnotation = method.getAnnotation(LogsInfo.class);
        if(Objects.nonNull(logsInfoAnnotation)){
            if(!logsInfoAnnotation.isOutPut()) {
                isLogs=false;
            }
        }
        String logContent = writeLogInfo(parameters, args,request);

        if(Objects.nonNull(logsInfoAnnotation) && StringUtil.isNotNullOrEmpty(logContent)){
//            String [] removeRges=logsInfoAnnotation.removeRge();
            //TODO
//            if(removeRges.length>0){
//
//            }

            if(logContent.length()>logsInfoAnnotation.maxSize()){
                isLogs=false;
            }
        }


        log.info("《《===== RequestURI：{},方法：{}({}) =====》》",request.getRequestURI(), methodName, description);

        if(isLogs){
            log.info("请求参数：{}", logContent);
        }else{
            log.info("请求参数大于：{}，忽略输出！", logsInfoAnnotation.maxSize());
        }


        Object object=point.proceed(args);

        if(!( method.getReturnType().equals(Callable.class))){
//            prometheusMetricsBiz.afterCompletion(request,attributes.getResponse());
            long costMs = System.currentTimeMillis() - beginTime;
            String resultLogs=JSON.toJSONString(object);

            if(Objects.nonNull(logsInfoAnnotation) && StringUtil.isNotNullOrEmpty(resultLogs) && resultLogs.length() > logsInfoAnnotation.maxSize()) {
                isLogs = false;
            }

            if(isLogs){
                log.info("响应信息：{}", resultLogs);
            }else{
                log.info("响应信息大于：{}，忽略输出！", logsInfoAnnotation.maxSize());
            }

            log.debug("》》===== 方法{}请求结束，耗时：{}ms =====《《", methodName, costMs);
        }else{
            request.setAttribute(SysConstant.START_TIME, beginTime);
            request.setAttribute(SysConstant.IS_LOGS, isLogs);
        }

        return object;
    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "e")
    public void controllerHandle(JoinPoint point, Exception e) {
        log.info("cut exception：{}", e.getMessage());
    }

    private String writeLogInfo(Parameter[] paramsArgsName, Object[] paramsArgsValue,HttpServletRequest request) {
        if (ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
            return "该方法没有参数";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paramsArgsName.length; i++) {
            //参数名
            String name = paramsArgsName[i].getName();
            //参数值
            Object value = paramsArgsValue[i];
            if (isPrimite(value.getClass())) {
                buffer.append(name + " = ");
            }

            if(value instanceof HttpServletRequest || value instanceof HttpServletResponse){
                continue;
            }else{
                buffer.append(JSON.toJSON(value) + ",");
            }

			if (value.getClass().equals(ComReq.class)) {
				request.setAttribute(SysConstant.COM_REQ, value);
                continue;
			}else  if (value.getClass().equals(BindingResult.class)) {
                BindingResult errors = (BindingResult) value;
                log.info(errors.getAllErrors().toString());
                if (errors.hasErrors()){
                    throw new ValidationException(CommonEnumCode.INVALID_REQUIRED_PARAMETERS_BLANK.getCode(),errors.getFieldError().getDefaultMessage());
                }
                continue;
            }
        }
        return buffer.toString();
    }

    private boolean isPrimite(Class<?> clazz) {
        return
                (clazz.equals(String.class) ||
                        clazz.equals(Integer.class) ||
                        clazz.equals(Byte.class) ||
                        clazz.equals(Long.class) ||
                        clazz.equals(Double.class) ||
                        clazz.equals(Float.class) ||
                        clazz.equals(Character.class) ||
                        clazz.equals(Short.class) ||
                        clazz.equals(BigDecimal.class) ||
                        clazz.equals(BigInteger.class) ||
                        clazz.equals(Boolean.class) ||
                        clazz.equals(Date.class) ||
                        clazz.isPrimitive()
                );
    }

}
