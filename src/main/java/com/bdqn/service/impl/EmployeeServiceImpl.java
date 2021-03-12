package com.bdqn.service.impl;

import com.bdqn.dao.EmployeeMapper;
import com.bdqn.entity.Employee;
import com.bdqn.service.EmployeeService;
import com.bdqn.utils.PasswordUtil;
import com.bdqn.utils.SystemConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     * @param loginName
     * @param loginPwd
     * @return
     */
    @Override
    public Employee login(String loginName, String loginPwd) {
        //调用根据账号查询员工的方法
        Employee employee=employeeMapper.findEmployeeByLoginName(loginName);
        if(employee!=null){
        //将密码加密处理
            String newPassword = PasswordUtil.md5(loginPwd,employee.getSalt(), SystemConstant.PASSWORD_COUNT);
            if(employee.getLoginPwd().equals(newPassword)){
                return employee;//登陆成功
            }
        }
        return null;
    }

    @Override
    public int getEmployeeCountByDeptId(Integer deptId) {
        return employeeMapper.getEmployeeCountByDeptId(deptId);
    }

    @Override
    public int getEmployeeCountByRoleId(Integer roleId) {
        return employeeMapper.getEmployeeCountByRoleId(roleId);
    }
}
