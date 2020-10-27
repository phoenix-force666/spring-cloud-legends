package com.legends.cloud.web.controller;

import com.legends.cloud.common.base.ComResp;
import com.legends.cloud.common.enums.CommonEnumCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhanghai on 2018/10/18.
 */
@RestController
@Slf4j
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ErrorHandlerController implements ErrorController {
    /**
     * 出异常后进入该方法，交由下面的方法处理
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Object error(HttpServletRequest request, HttpServletResponse response){
//        Integer status = (Integer)request.getAttribute("javax.servlet.error.status_code");
        Integer status=response.getStatus();
        if(status==404){
            return new ComResp.Builder().error(CommonEnumCode.RESOURCE_NOTFOUND).build();
        }else if(status==400){
            return new ComResp.Builder().error(CommonEnumCode.INVALID_PARAMETER).build();
        }else if(status==500){
            return new ComResp.Builder().error(CommonEnumCode.SYS_ERR).build();
        }else{
            return new ComResp.Builder().error(CommonEnumCode.UNKNOWN_ERR).build();
        }
    }
}