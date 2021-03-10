package com.bdqn.dao;

import com.bdqn.entity.Employee;

public interface EmployeeMapper {

    /**
     * 根据登录账号查询员工信息
     * @param loginName
     * @return
     */
    Employee findEmployeeByLoginName(String loginName);

}
