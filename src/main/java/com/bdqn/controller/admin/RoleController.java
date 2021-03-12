package com.bdqn.controller.admin;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Role;
import com.bdqn.service.EmployeeService;
import com.bdqn.service.RoleService;
import com.bdqn.utils.DataGridViewResult;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.RoleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/role")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private EmployeeService employeeService;

    /**
     * 查询角色列表
     * @param roleVo
     * @return
     */
    @RequestMapping("/list")
    public DataGridViewResult list(RoleVo roleVo){
        //设置分页信息
        PageHelper.startPage(roleVo.getPage(),roleVo.getLimit());
        //调用查询角色列表的方法
        List<Role> roleList =roleService.findRoleList(roleVo);
        //创建分页信息对象
        PageInfo<Role> pageInfo =new PageInfo<Role>(roleList);
        //返回数据(参数一是：总数量  参数二：角色列表)
        return new DataGridViewResult(pageInfo.getTotal(),pageInfo.getList());

    }
    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole(Role role){
        Map<String,Object> map = new HashMap<String,Object>();
        //调用添加角色的方法
        if(roleService.addRole(role)>0){
            map.put(SystemConstant.SUCCESS,true);//成功
            map.put(SystemConstant.MESSAGE,"添加成功");
        }else{
            map.put(SystemConstant.SUCCESS,false);//失败
            map.put(SystemConstant.MESSAGE,"添加失败");
        }
        //将map集合以JSON格式返回
        return JSON.toJSONString(map);
    }
    /**
     * 修改角色
     * @param role
     * @return
     */
    @RequestMapping("/updateRole")
    public String updateRole(Role role){
        Map<String,Object> map = new HashMap<String,Object>();
        //调用修改角色的方法
        if(roleService.updateRole(role)>0){
            map.put(SystemConstant.SUCCESS,true);//成功
            map.put(SystemConstant.MESSAGE,"修改成功");
        }else{
            map.put(SystemConstant.SUCCESS,false);//失败
            map.put(SystemConstant.MESSAGE,"修改失败");
        }
        //将map集合以JSON格式返回
        return JSON.toJSONString(map);
    }
    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public String deleteById(Integer id){
        Map<String,Object> map = new HashMap<String,Object>();
        //调用删除角色的方法
        if(roleService.deleteById(id)>0){
            map.put(SystemConstant.SUCCESS,true);//存在
            map.put(SystemConstant.MESSAGE,"删除成功");
        }else{
            map.put(SystemConstant.SUCCESS,false);//不存在
            map.put(SystemConstant.MESSAGE,"删除失败");
        }
        //将map集合以JSON格式返回
        return JSON.toJSONString(map);
    }
    @RequestMapping("/checkRoleHasEmployee")
    public String checkDeptHasEmployee(Integer id){
        Map<String,Object> map = new HashMap<String,Object>();
        //调用根据角色编号查询员工数量的方法
        if(employeeService.getEmployeeCountByRoleId(id)>0){
            map.put(SystemConstant.EXIST,true);//存在
            map.put(SystemConstant.MESSAGE,"该角色下存在员工信息，无法删除");
        }else{
            map.put(SystemConstant.EXIST,false);//不存在
        }
        //将map集合以JSON格式返回
        return JSON.toJSONString(map);
    }
}
