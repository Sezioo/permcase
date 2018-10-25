package com.sezioo.permission.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.sezioo.permission.dao.SysDeptMapper;
import com.sezioo.permission.exception.ParamException;
import com.sezioo.permission.model.SysDept;
import com.sezioo.permission.param.SysDeptParam;
import com.sezioo.permission.util.BeanValidator;
import com.sezioo.permission.util.LevelUtil;

@Service
public class SysDeptService {
	@Resource
	private SysDeptMapper deptMapper;
	
	public void save(SysDeptParam param) {
		BeanValidator.check(param);
		if(isExists(param.getParentId(),param.getName(),param.getId() )) {
			throw new ParamException("同层级下的部门名称不能相同") ;
		}
		SysDept sysDept = SysDept.builder().name(param.getName()).parentId(param.getParentId()).remark(param.getRemark()).seq(param.getSeq()).build();
		String level = LevelUtil.caculateLevel(getLevel(param.getParentId()), param.getParentId());
		sysDept.setLevel(level);
		sysDept.setOperator("system");//TODO:
		sysDept.setOperateIp("127.0.0.1");//TODO:
		sysDept.setOperateTime(new Date());
		deptMapper.insert(sysDept);
	}
	
	public void update(SysDeptParam param) {
		BeanValidator.check(param);
		if(isExists(param.getParentId(), param.getName(), param.getId())) {
			throw new ParamException("同层级下的部门名称不能相同");
		}
		SysDept sysDeptOld = deptMapper.selectByPrimaryKey(param.getId());
		Preconditions.checkNotNull(sysDeptOld, "待更新的部门不存在");
		
		SysDept sysDeptNew = SysDept.builder().id(param.getId()).parentId(param.getParentId()).remark(param.getRemark()).seq(param.getSeq()).name(param.getName()).build();
		sysDeptNew.setLevel(LevelUtil.caculateLevel(getLevel(param.getParentId()), param.getParentId()));//TODO:
		sysDeptNew.setOperator("system-update");//TODO:
		sysDeptNew.setOperateIp("127.0.0.1");//TODO:
		sysDeptNew.setOperateTime(new Date());
		
		updateDept(sysDeptOld,sysDeptNew);
	}
	
	@Transactional
	public void updateDept(SysDept sysDeptOld,SysDept sysDeptNew) {
		if (!sysDeptOld.getLevel().equals(sysDeptNew.getLevel())) {
			updateDeptLevel(sysDeptOld.getLevel(), sysDeptNew.getLevel());	
		}
		deptMapper.updateByPrimaryKeySelective(sysDeptNew);
	}
	
	public void updateDeptLevel(String oldLevel,String newLevel) {
		String levelPrefix = oldLevel + ".%";
		List<SysDept> deptList = deptMapper.findDeptListByLevelPrefix(levelPrefix);
		if (CollectionUtils.isNotEmpty(deptList)) {
			for(SysDept sysDept : deptList) {
				String levelSuffix = oldLevel.substring(oldLevel.length());
				String level = newLevel + levelSuffix;
				sysDept.setLevel(level);
			}
			deptMapper.batchUpdateChildDept(deptList);
		}
	}
	
	public boolean isExists(Integer parentId,String deptName,Integer deptId) {
		return deptMapper.countDeptByParentIdAndName(deptName, parentId, deptId)>0;
	}
	
	public String getLevel(Integer deptId) {
		SysDept sysDept = deptMapper.selectByPrimaryKey(deptId);
		if(sysDept == null) {
			return null;
		}
		return sysDept.getLevel();
	}
}
