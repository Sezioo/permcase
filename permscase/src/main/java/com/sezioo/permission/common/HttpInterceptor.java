package com.sezioo.permission.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sezioo.permission.beans.LocalHolder;
import com.sezioo.permission.util.JsonMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter{
	private static final String START_TIME = "request_start_time";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map parameterMap = request.getParameterMap();
		String requestURI = request.getRequestURI();
		long currentTimeMillis = System.currentTimeMillis();
		request.setAttribute(START_TIME, currentTimeMillis);
		log.info("request {} start. {}", requestURI,JsonMapper.obj2String(parameterMap));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LocalHolder.remove();
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		long start = (long) request.getAttribute(START_TIME);
		LocalHolder.remove();
		log.info("requestURI {} end.  cost {}  ms", request.getRequestURI(),currentTimeMillis-start);
		super.afterCompletion(request, response, handler, ex);
	}
	
}
