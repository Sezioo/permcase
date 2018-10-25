package com.sezioo.permission.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.sezioo.permission.dao.SysDeptMapper;
import com.sezioo.permission.dto.DeptLevelDto;
import com.sezioo.permission.model.SysDept;
import com.sezioo.permission.util.LevelUtil;

@Service
public class SysTreeService {
	
	@Resource
	private SysDeptMapper deptMapper;
	
	public List<DeptLevelDto> deptTree(){
		List<SysDept> deptList = deptMapper.findAll();
		List<DeptLevelDto> dtoList = Lists.newArrayList();
		for(SysDept dept : deptList) {
			dtoList.add(DeptLevelDto.adapt(dept));
		}
		
		return deptToTree(dtoList);
	}
	
	public List<DeptLevelDto> deptToTree(List<DeptLevelDto> deptList){
		if(CollectionUtils.isEmpty(deptList)) {
			return null;
		}
		Multimap<String,DeptLevelDto> deptMultimap = ArrayListMultimap.create();
		List<DeptLevelDto> rootDeptList = Lists.newArrayList();
		for(DeptLevelDto deptLevelDto : deptList) {
			deptMultimap.put(deptLevelDto.getLevel(), deptLevelDto);
			if(LevelUtil.ROOT.equals(deptLevelDto.getLevel())) {
				rootDeptList.add(deptLevelDto);
			}
		}
		Collections.sort(rootDeptList, deptSeqComparator);
		transformToDeptTree(rootDeptList,deptMultimap,LevelUtil.ROOT);
		return rootDeptList;
		
	}
	
	public void transformToDeptTree(List<DeptLevelDto> deptLevelList,Multimap<String,DeptLevelDto> deptMultimap,String level) {
		for(DeptLevelDto deptDto : deptLevelList) {
			String nextLevel = LevelUtil.caculateLevel(level, deptDto.getId());
			List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>)deptMultimap.get(nextLevel);
			if(CollectionUtils.isNotEmpty(tempDeptList)) {
				Collections.sort(tempDeptList, deptSeqComparator);
				deptDto.setDeptList(tempDeptList);
				transformToDeptTree(tempDeptList,deptMultimap,nextLevel);
			}
			
		}
	}
	
	public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
		
		public int compare(DeptLevelDto o1, DeptLevelDto o2) {
			return o1.getSeq() - o2.getSeq();
		}
	};
}
