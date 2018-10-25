package com.sezioo.permission.controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sezioo.permission.common.ApplicationContextHelper;
import com.sezioo.permission.common.JsonData;
import com.sezioo.permission.dao.SysAclModuleMapper;
import com.sezioo.permission.exception.ParamException;
import com.sezioo.permission.exception.PermissionException;
import com.sezioo.permission.model.SysAclModule;
import com.sezioo.permission.param.TestVo;
import com.sezioo.permission.util.BeanValidator;
import com.sezioo.permission.util.JsonMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {
	
	@RequestMapping("/hello.json")
	@ResponseBody
	public JsonData hello() {
		throw new PermissionException("hello permission error");
//		return JsonData.success("hello permission");
	}
	
	@RequestMapping("/validate.json")
	@ResponseBody
	public JsonData valildate(TestVo vo) throws ParamException {
		BeanValidator.check(vo);
		SysAclModuleMapper sysAclModuleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
		log.info(JsonMapper.obj2String(sysAclModuleMapper));
		SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(1);
		log.info(JsonMapper.obj2String(sysAclModule));
		return JsonData.success("hello permission");
	}
}
