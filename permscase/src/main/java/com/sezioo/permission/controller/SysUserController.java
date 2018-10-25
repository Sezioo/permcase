package com.sezioo.permission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sezioo.permission.beans.PageQuery;
import com.sezioo.permission.beans.PageResult;
import com.sezioo.permission.common.JsonData;
import com.sezioo.permission.model.SysUser;
import com.sezioo.permission.param.SysDeptParam;
import com.sezioo.permission.param.SysUserParam;
import com.sezioo.permission.service.SysUserService;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {
	
	@Autowired
	private SysUserService userService;
	
	@RequestMapping("/save.json")
	@ResponseBody
	public JsonData save(SysUserParam param) {
		userService.save(param);
		return JsonData.success();
	}
	
	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData update(SysUserParam param) {
		userService.update(param);
		return JsonData.success();
	}
	
	@RequestMapping("/page.json")
	@ResponseBody
	public JsonData page(int deptId,PageQuery pageQuery) {
		PageResult<SysUser> pageResult = userService.listUserByDeptId(deptId, pageQuery);
		return JsonData.success(pageResult);
	}
}
