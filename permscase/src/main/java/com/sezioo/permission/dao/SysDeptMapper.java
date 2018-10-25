package com.sezioo.permission.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sezioo.permission.model.SysDept;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);
    
    List<SysDept> findAll();
    
    List<SysDept> findDeptListByLevelPrefix(@Param("levelPrefix") String levelPrefix);
    
    int batchUpdateChildDept(@Param("deptList") List<SysDept> deptList);
    
    int countDeptByParentIdAndName(@Param("name") String name,@Param("parentId") Integer parentId,@Param("id") Integer id);
}