package com.sezioo.permission.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.sezioo.permission.exception.ParamException;
import com.sezioo.permission.exception.PermissionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		String url = request.getRequestURI();
		ModelAndView mv;
		String defaultResult = "System error";
		//数据请求 .json,页面请求.page
		if(url.endsWith(".json")) {
			if(ex instanceof PermissionException || ex instanceof ParamException) {
				JsonData jsonData = JsonData.fail(ex.getMessage());
				mv = new ModelAndView("jsonView", jsonData.toMap());
			}else {
				log.error("unknown json exception " + url, ex);
				JsonData jsonData = JsonData.fail(defaultResult);
				mv = new ModelAndView("jsonView", jsonData.toMap());
			}
		}else if(url.endsWith(".page")) {
			log.error("unknown page exception " + url, ex);
			JsonData jsonData = JsonData.fail(defaultResult);
			mv = new ModelAndView("exception", jsonData.toMap());
		}else {
			log.error("unknown exception " + url, ex);
			JsonData jsonData = JsonData.fail(defaultResult);
			mv = new ModelAndView("jsonView", jsonData.toMap());
		}
		return mv;
	}
	
}
