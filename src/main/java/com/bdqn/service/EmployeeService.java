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
}
