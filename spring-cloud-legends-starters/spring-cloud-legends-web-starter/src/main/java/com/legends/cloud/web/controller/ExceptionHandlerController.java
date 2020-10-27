package com.legends.cloud.web.controller;

import com.alibaba.fastjson.JSON;
import com.legends.cloud.common.base.ComReq;
import com.legends.cloud.common.base.ComResp;
import com.legends.cloud.common.enums.CommonEnumCode;
import com.legends.cloud.common.exception.*;
import com.legends.cloud.web.constant.SysConstant;
import com.legends.cloud.web.utils.idworker.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
@SuppressWarnings("all")
@Configuration
public class ExceptionHandlerController {

	@Value("${spring.profiles.active}")
	private String active;

	@ExceptionHandler({ BizException.class})
	@ResponseBody
	private ComResp processBizException(HttpServletRequest request, BizException e) {
		ComReq comReq = (ComReq) request.getAttribute(SysConstant.COM_REM);
		log.error("BizException 异常", e);
		String requestNo="";
		if(null!=comReq){
			requestNo=comReq.getReqNo();
		}else{
			comReq=new ComReq.Builder().reqNo(SysConstant.BE+IdWorker.getId()).build();
		}
		asyncSendEmail(request.getRequestURL().toString(),getEmessage(e),requestNo);
		ComResp res = new ComResp.Builder().fromReq(comReq).code(e.getCode()).msg(e.getMsg()).build();
		log.info("BizException 异常响应报文：{}", JSON.toJSONString(res));
		return res;
	}


	@ExceptionHandler({ LegendsException.class })
	@ResponseBody
	private ComResp processLegendsException(HttpServletRequest request, LegendsException e) {
		ComReq comReq = (ComReq) request.getAttribute(SysConstant.COM_REM);
		log.error("LegendsException 异常", e);
		String requestNo="";
		if(null!=comReq){
			requestNo=comReq.getReqNo();
		}else{
			comReq=new ComReq.Builder().reqNo(SysConstant.JE+IdWorker.getId()).build();
		}
		asyncSendEmail(request.getRequestURL().toString(),getEmessage(e),requestNo);
		ComResp res = new ComResp.Builder().fromReq(comReq).code(e.getCode()).msg(e.getMsg()).build();
		log.info("LegendsException 响应报文：{}", JSON.toJSONString(res));
		return res;
	}


	@ExceptionHandler({ AuthException.class })
	@ResponseBody
	private ComResp processAuthException(HttpServletRequest request, AuthException e) {
		ComReq comReq = (ComReq) request.getAttribute(SysConstant.COM_REM);
		log.error("AuthException 异常", e);
		String requestNo="";
		if(null!=comReq){
			requestNo=comReq.getReqNo();
		}else{
			comReq=new ComReq.Builder().reqNo(SysConstant.AE+IdWorker.getId()).build();
		}
		asyncSendEmail(request.getRequestURL().toString(),getEmessage(e),requestNo);
		ComResp res = new ComResp.Builder().fromReq(comReq).code(e.getCode()).msg(e.getMsg()).build();
		log.info("AuthException 异常响应报文：{}", JSON.toJSONString(res));
		return res;
	}


	@ExceptionHandler({ ValidationException.class })
	@ResponseBody
	private ComResp processValidationException(HttpServletRequest request, ValidationException e) {
		ComReq comReq = (ComReq) request.getAttribute(SysConstant.COM_REM);
		log.error("ValidationException 异常", e);
		String requestNo="";
		if(null!=comReq){
			requestNo=comReq.getReqNo();
		}else{
			comReq=new ComReq.Builder().reqNo(SysConstant.VE+IdWorker.getId()).build();
		}
		asyncSendEmail(request.getRequestURL().toString(),getEmessage(e),requestNo);
		ComResp res = new ComResp.Builder().fromReq(comReq).code(e.getCode()).msg(e.getMsg()).build();
		log.info("ValidationException 异常响应报文：{}", JSON.toJSONString(res));
		return res;
	}

	@ExceptionHandler({ RemoteException.class })
	@ResponseBody
	private ComResp processRemoteException(HttpServletRequest request, RemoteException e) {
		ComReq comReq = (ComReq) request.getAttribute(SysConstant.COM_REM);
		log.error("RemoteException 异常", e);
		String requestNo="";
		if(null!=comReq){
			requestNo=comReq.getReqNo();
		}else{
			comReq=new ComReq.Builder().reqNo(SysConstant.RE+IdWorker.getId()).build();
		}
		asyncSendEmail(request.getRequestURL().toString(),getEmessage(e),requestNo);
		ComResp res = new ComResp.Builder().fromReq(comReq).code(e.getCode()).msg(e.getMsg()).build();
		log.info("RemoteException 异常响应报文：{}", JSON.toJSONString(res));
		return res;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	private ComResp processException(HttpServletRequest request, Exception e) {
		ComReq comReq = (ComReq) request.getAttribute(SysConstant.COM_REM);
		log.error("系统异常", e);
		String requestNo="";
		if(null!=comReq){
			requestNo=comReq.getReqNo();
		}else{
			comReq=new ComReq.Builder().reqNo(SysConstant.EE+IdWorker.getId()).build();
		}
		asyncSendEmail(request.getRequestURL().toString(),getEmessage(e),requestNo);
		return new ComResp.Builder().fromReq(comReq).error(CommonEnumCode.UNKNOWN_ERR).build();
	}

	public static String getEmessage(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}



	/**
	 * 对方法参数校验异常处理方法(仅对于表单提交有效，对于以json格式提交将会失效)
	 * 如果是表单类型的提交，则spring会采用表单数据的处理类进行处理（进行参数校验错误时会抛出BindException异常）
	 */
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public Object handlerBindException(HttpServletRequest request,BindException exception) {
		return handlerNotValidException(request,exception);
	}

	/**
	 * 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理)
	 * json格式提交时，spring会采用json数据的数据转换器进行处理（进行参数校验时错误是抛出MethodArgumentNotValidException异常）
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Object handlerArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
		return handlerNotValidException(request,exception);
	}

	public Object handlerNotValidException(HttpServletRequest request,Exception e) {
		log.debug("begin resolve argument exception");
		BindingResult result;
		if (e instanceof BindException) {
			BindException exception = (BindException) e;
			result = exception.getBindingResult();
		} else {
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
			result = exception.getBindingResult();
		}
		Map<String, Object> maps;
		String msg=CommonEnumCode.INVALID_REQUIRED_PARAMETERS_BLANK.getMsg();
		StringBuffer sb=new StringBuffer(msg);
		sb.append("->");
		if (result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			maps = new HashMap<>(fieldErrors.size());
			fieldErrors.forEach(error -> {
				sb.append(" | ").append(error.getField()).append(":").append(error.getDefaultMessage());
			});
		}

		ComReq comReq = (ComReq) result.getTarget();

		if(active.equals("dev") || active.equals("test") || active.equals("sit")){
			msg=sb.toString();
		}
		return new ComResp.Builder<>().fromReq(Objects.isNull(comReq)?new ComReq.Builder<>().build():comReq).code(CommonEnumCode.INVALID_REQUIRED_PARAMETERS_BLANK.getCode()).msg(msg).build();

	}



	
    public void asyncSendEmail(String requestUrl,String message,String requestNo) {
		log.error("requestNo:{},请求：{},message：{}",requestNo,requestUrl,message);
//		EmailError emailError=new EmailError();
//		emailError.setUrl(requestUrl);
//		emailError.setNote(message);
//		emailError.setRequestNo(requestNo);
//		EmailErrorQueue.getInstance().push(emailError);
    }
}
