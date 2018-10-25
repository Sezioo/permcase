package com.sezioo.permission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sezioo.permission.beans.PageQuery;
import com.sezioo.permission.model.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

	SysUser findByKeyword(@Param("keyword") String keyword);
	
	int countByMail(@Param("mail") String mail,@Param("id") Integer id);
	
	int countByTelephone(@Param("telephone") String telephone,@Param("id") Integer id);
	
	int countByDeptId(@Param("deptId") int deptId);
	
	List<SysUser> listByDeptId(@Param("deptId") int deptId,@Param("page") PageQuery page);
}