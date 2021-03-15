package com.bdqn.service.impl;

import com.bdqn.dao.EmployeeMapper;
import com.bdqn.entity.Employee;
import com.bdqn.service.EmployeeService;
import com.bdqn.utils.PasswordUtil;
import com.bdqn.utils.SystemConstant;
import com.bdqn.utils.UUIDUtils;
import com.bdqn.vo.EmployeeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<Employee> findEmployeeList(EmployeeVo employeeVo) {
        return employeeMapper.findEmployeeList(employeeVo);
    }

    @Override
    public int addEmployee(Employee employee) {
        employee.setSalt(UUIDUtils.randomUUID());//加密盐值
        employee.setCreateDate(new Date());//创建时间
        employee.setLoginPwd(PasswordUtil.md5(SystemConstant.DEFAULT_LOGIN_PWD,employee.getSalt(),SystemConstant.PASSWORD_COUNT));//密码
        return employeeMapper.addEmployee(employee);
    }

    @Override
    public int updateEmployee(Employee employee) {
        employee.setModifyDate(new Date());//创建时间
        return employeeMapper.updateEmployee(employee);
    }

    @Override
    public int deleteById(Integer id) {
        employeeMapper.deleteEmployeeAndRole(id);
        return employeeMapper.deleteById(id);
    }

    @Override
    public int resetPwd(int id) {
        Employee employee = new Employee();
        employee.setSalt(UUIDUtils.randomUUID());//必须先设置盐值，再给密码重新加密
        employee.setLoginPwd(PasswordUtil.md5(SystemConstant.DEFAULT_LOGIN_PWD,employee.getSalt(),SystemConstant.PASSWORD_COUNT));
        employee.setId(id);
        return employeeMapper.updateEmployee(employee);
    }

    @Override
    public boolean saveEmployeeRole(String roleIds, Integer empId) {
        try {
            //先删除员工角色关系表的数据
            employeeMapper.deleteEmployeeAndRole(empId);
            //在保存员工角色数据
            String [] idStr = roleIds.split(",");
            for(int i=0;i<idStr.length;i++){
                employeeMapper.addEmployeeRole(idStr[i],empId);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
