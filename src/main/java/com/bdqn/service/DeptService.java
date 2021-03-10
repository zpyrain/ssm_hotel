package com.bdqn.service;

import com.bdqn.entity.Dept;
import com.bdqn.vo.DeptVo;

import java.util.List;

public interface DeptService {
    /**
     * 查询部门列表
     * @param deptVo
     * @return
     */
    List<Dept> findDeptListByPage(DeptVo deptVo);

}
