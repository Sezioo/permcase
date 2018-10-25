package com.sezioo.permission.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sezioo.permission.model.SysUser;
import com.sezioo.permission.service.SysUserService;
import com.sezioo.permission.util.MD5Util;

@Controller
public class UserController {

	@Autowired
	private SysUserService userService;
	
	@RequestMapping("/login.page")
	public void login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ret = request.getParameter("ret");
		SysUser user = userService.findByKeyword(username);
		String errorMsg = "";
		if(StringUtils.isBlank(username)) {
			errorMsg = "用户名不能为空";
		}else if(StringUtils.isBlank(password)) {
			errorMsg = "密码不能为空";
		}else if(user == null) {
			errorMsg = "用户不存在";
		}else if(!MD5Util.encrypt(password).equals(user.getPassword())) {
			errorMsg = "用户名或密码错误";
		}else if (user.getStatus() != 1) {
			errorMsg = "用户被冻结，请联系管理员";
		}else {
			//login success
			request.getSession().setAttribute("user", user);
			if(StringUtils.isNotBlank(ret)) {
				response.sendRedirect(ret);
			}else {
				response.sendRedirect("/admin/index.page");
			}
		}
		
		request.setAttribute("error", errorMsg);
		if(StringUtils.isNotBlank(ret)) {
			request.setAttribute("ret", ret);
		}
		request.setAttribute("username", username);
		String path = "signin.jsp";
		request.getRequestDispatcher(path).forward(request, response);
	}
}
