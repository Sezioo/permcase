package com.sezioo.permission.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sezioo.permission.common.JsonData;
import com.sezioo.permission.dto.DeptLevelDto;
import com.sezioo.permission.param.SysDeptParam;
import com.sezioo.permission.service.SysDeptService;
import com.sezioo.permission.service.SysTreeService;

@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {
	@Resource
	private SysDeptService sysDeptService;
	
	@Resource
	private SysTreeService sysTreeService;
	
	@RequestMapping("/save.json")
	@ResponseBody
	public JsonData save(SysDeptParam param) {
		sysDeptService.save(param);
		return JsonData.success();
	}
	
	@RequestMapping("/tree.json")
	@ResponseBody
	public JsonData tree() {
		List<DeptLevelDto> deptTree = sysTreeService.deptTree();
		return JsonData.success(deptTree);
	}
	
	@RequestMapping("/update.json")
	@ResponseBody
	public JsonData update(SysDeptParam param) {
		sysDeptService.update(param);
		return JsonData.success();
	}
	
	@RequestMapping("/dept.page")
	public ModelAndView page() {
		return new ModelAndView("dept");
	}
}
