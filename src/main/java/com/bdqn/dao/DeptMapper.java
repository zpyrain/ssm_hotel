package com.bdqn.dao;

import com.bdqn.entity.Dept;
import com.bdqn.vo.DeptVo;

import java.util.List;

public interface DeptMapper {

    /**
     * 查询部门列表
     * @param deptVo
     * @return
     */
    List<Dept> findDeptListByPage(DeptVo deptVo);
}
