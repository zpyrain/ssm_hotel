package com.bdqn.service;

import com.bdqn.entity.Employee;

public interface EmployeeService {
    /**
     * 员工登录
     * @param loginName
     * @param loginPwd
     * @return
     */
    Employee login(String loginName,String loginPwd);
    /**
     * 根据部门编号查询该部门下的员工数量
     * @param deptId
     * @return
     */
    int getEmployeeCountByDeptId(Integer deptId);
    /**
     * 根据角色编号查询该部门下的员工数量
     * @param roleId
     * @return
     */
    int getEmployeeCountByRoleId(Integer roleId);

}
