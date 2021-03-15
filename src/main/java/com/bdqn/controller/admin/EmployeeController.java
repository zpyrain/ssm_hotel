package com.bdqn.controller.admin;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Employee;
import com.bdqn.service.EmployeeService;
import com.bdqn.utils.DataGridViewResult;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.EmployeeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {


    @Resource
    private EmployeeService employeeService;


    /**
     * 员工登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session){
        Map<String,Object> map = new HashMap<String,Object>();
        //调用员工登录的方法
        Employee employee = employeeService.login(username, password);
        //判断对象是否为空，不为空表示登录成功
        if(employee!=null){
            //保存当前登录用户
            session.setAttribute(SystemConstant.LOGINUSER,employee);
            map.put(SystemConstant.SUCCESS,true);//成功
        }else{
            map.put(SystemConstant.SUCCESS,false);//失败
            map.put(SystemConstant.MESSAGE,"账号密码错误，登录失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 员工信息
     * @param employeeVo
     * @return
     */
    @RequestMapping("/list")
    public DataGridViewResult list(EmployeeVo employeeVo){
        //设置分页信息
        PageHelper.startPage(employeeVo.getPage(),employeeVo.getLimit());
        //调用查询的方法
        List<Employee> employeeList = employeeService.findEmployeeList(employeeVo);
        //创建分页查询对象
        PageInfo<Employee> pageInfo = new PageInfo<Employee>(employeeList);
        //返回数据
        return new DataGridViewResult(pageInfo.getTotal(),pageInfo.getList());
    }
    /**
     * 添加员工
     * @param employee
     * @param session
     * @return
     */
    @RequestMapping("/addEmployee")
    public String addEmployee(Employee employee,HttpSession session){
        Map<String,Object> map = new HashMap<String,Object>();
        //获取当前登录用户
        Employee loginUser = (Employee) session.getAttribute(SystemConstant.LOGINUSER);
        //设置创建人
        employee.setCreatedBy(loginUser.getId());
        //调用新增员工的方法
        if(employeeService.addEmployee(employee)>0) {
            System.out.print("添加成功");
            map.put(SystemConstant.SUCCESS, true);
            map.put(SystemConstant.MESSAGE, "添加成功");
        }else{
            System.out.print("添加失败");
            map.put(SystemConstant.SUCCESS, false);
            map.put(SystemConstant.MESSAGE, "添加失败");
        }
        return JSON.toJSONString(map);
    }
    /**
     * 修改员工
     * @param employee
     * @param session
     * @return
     */
    @RequestMapping("/updateEmployee")
    public String updateEmployee(Employee employee,HttpSession session){
        Map<String,Object> map = new HashMap<String,Object>();
        //获取当前登录用户
        Employee loginUser = (Employee) session.getAttribute(SystemConstant.LOGINUSER);
        //设置创建人
        employee.setModifyBy(loginUser.getId());
        //调用新增员工的方法
        if(employeeService.updateEmployee(employee)>0) {
            map.put(SystemConstant.SUCCESS, true);
            map.put(SystemConstant.MESSAGE, "修改成功");
        }else{
            map.put(SystemConstant.SUCCESS, false);
            map.put(SystemConstant.MESSAGE, "修改失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public String deleteById(int id){
         Map<String,Object> map =new HashMap<String,Object>();
         //删除调用员工的方法
        if(employeeService.deleteById(id)>0){
            map.put(SystemConstant.SUCCESS, true);
            map.put(SystemConstant.MESSAGE, "删除成功");
        }else{
            map.put(SystemConstant.SUCCESS, false);
            map.put(SystemConstant.MESSAGE, "删除失败");
        }
        return JSON.toJSONString(map);
    }
    /**
     * 重置密码
     * @param id
     * @return
     */
    @RequestMapping("/resetPwd")
    public String resetPwd(int id){
        Map<String,Object> map =new HashMap<String,Object>();
        //调用充值密码的方法
        if(employeeService.resetPwd(id)>0){
            map.put(SystemConstant.SUCCESS, true);
            map.put(SystemConstant.MESSAGE, "密码重置成功");
        }else{
            map.put(SystemConstant.SUCCESS, false);
            map.put(SystemConstant.MESSAGE, "密码重置失败");
        }
        return JSON.toJSONString(map);
    }
    @RequestMapping("/saveEmployeeRole")
    public String saveEmployeeRole(String roleIds,Integer empId){
        Map<String, Object> map = new HashMap<>();
        //调用保存员工角色关系的方法
        if(employeeService.saveEmployeeRole(roleIds,empId)){
            map.put(SystemConstant.MESSAGE,"角色分配成功");
        }else{
            map.put(SystemConstant.MESSAGE,"角色分配失败");
        }

        return JSON.toJSONString(map);
    }
}
