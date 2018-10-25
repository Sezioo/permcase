package com.sezioo.permission.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.sezioo.permission.beans.PageQuery;
import com.sezioo.permission.beans.PageResult;
import com.sezioo.permission.dao.SysUserMapper;
import com.sezioo.permission.exception.ParamException;
import com.sezioo.permission.model.SysUser;
import com.sezioo.permission.param.SysUserParam;
import com.sezioo.permission.util.BeanValidator;
import com.sezioo.permission.util.MD5Util;

@Service
public class SysUserService {
	
	@Resource
	private SysUserMapper userMapper;
	
	public void save(SysUserParam param) {
		BeanValidator.check(param);
		if(isMailExists(param.getMail(), param.getId())) {
			throw new ParamException("邮箱已经存在") ;
		}
		if(isTelephoneExists(param.getTelephone(), param.getId())) {
			throw new ParamException("电话已经存在");
		}
		String password = "123456";//TODO:
		String encryptedpassword = MD5Util.encrypt(password);
		SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail()).deptId(param.getDeptId())
				.status(param.getStatus()).remark(param.getRemark()).password(encryptedpassword).build();
		//TODO:发送邮件
		
		user.setOperator("system");//TODO:
		user.setOperateIp("127.0.0.1");//TODO:
		user.setOperateTime(new Date());
		userMapper.insertSelective(user);
	}
	
	public void update(SysUserParam param) {
		BeanValidator.check(param);
		if(isMailExists(param.getMail(), param.getId())) {
			throw new ParamException("邮箱已经存在") ;
		}
		if(isTelephoneExists(param.getTelephone(), param.getId())) {
			throw new ParamException("电话已经存在");
		}
		SysUser before = userMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(before, "用户不存在");
		SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail()).deptId(param.getDeptId())
				.status(param.getStatus()).remark(param.getRemark()).build();
		userMapper.updateByPrimaryKeySelective(after);
		
	}
	
	public SysUser findByKeyword(String keyword) {
		return userMapper.findByKeyword(keyword);
	}
	
	public boolean isMailExists(String mail,Integer id) {
		return userMapper.countByMail(mail, id) > 0;
	}
	
	public boolean isTelephoneExists(String telephone,Integer id) {
		return userMapper.countByTelephone(telephone, id) > 0;
	}
	
	public PageResult<SysUser> listUserByDeptId(int deptId,PageQuery pageQuery){
		BeanValidator.check(pageQuery);
		int total = userMapper.countByDeptId(deptId);
		if(total>0) {
			List<SysUser> list = userMapper.listByDeptId(deptId, pageQuery);
			PageResult<SysUser> pageResult = PageResult.<SysUser>builder().total(total).data(list).build();
			return pageResult;
		}
		
		return null;
	}
}
