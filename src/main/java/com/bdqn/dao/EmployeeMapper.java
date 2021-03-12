package com.bdqn.dao;

import com.bdqn.entity.Employee;

public interface EmployeeMapper {

    /**
     * 根据登录账号查询员工信息
     * @param loginName
     * @return
     */
    Employee findEmployeeByLoginName(String loginName);

    /**
     * 根据部门编号查询该部门下的员工数量
     * @param deptId
     * @return
     */
    int getEmployeeCountByDeptId(Integer deptId);
    /**
     * 根据角色编号查询员工数量
     * @param roleId
     * @return
     */
    int getEmployeeCountByRoleId(Integer roleId);



}
