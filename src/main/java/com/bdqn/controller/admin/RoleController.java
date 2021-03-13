package com.bdqn.controller.admin;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Menu;
import com.bdqn.entity.Role;
import com.bdqn.service.EmployeeService;
import com.bdqn.service.MenuService;
import com.bdqn.service.RoleService;
import com.bdqn.utils.DataGridViewResult;
import com.bdqn.utils.SystemConstant;
import com.bdqn.utils.TreeNode;
import com.bdqn.vo.RoleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private MenuService menuService;

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


//    @RequestMapping("/initMenuTree")
//    public DataGridViewResult initMenuTree(){
//        //调用查询菜单列表的方法
//        List<Menu> menuList = menuMapper.findMenuList();
//        //调用根据角色ID查询该角色已经拥有的菜单ID的方法
//
//        //创建集合创建保存树节点信息
//        List<TreeNode> treeNodes = new ArrayList<>();
//        for (Menu menu : menuList) {
//            //定义变量，表示菜单是否展开
//            Boolean spread = (menu.getSpread()==null||menu.getSpread()==1)?true:false;
//            treeNodes.add(new TreeNode(menu.getId(),menu.getPid(),menu.getTitle(),spread));
//        }
//        //将数据返回到页面
//        return new DataGridViewResult(treeNodes);
//    }
    /**
     * 初始化菜单树
     * @return
     */
    @RequestMapping("/initMenuTree")
    public DataGridViewResult initMenuTree(Integer roleId){
        //调用查询菜单列表的方法
        List<Menu> menuList = menuService.findMenuList();
        //调用根据角色ID查询该角色已经拥有的菜单ID的方法
        List<Integer> currentRoleMenuIds = menuService.findMenuIdListByRoleId(roleId);
        //定义集合，保存菜单信息
        List<Menu> currentMenus = new ArrayList<Menu>();
        //判断集合是否存在数据
        if(currentRoleMenuIds!=null &&currentRoleMenuIds.size()>0){
             //根据菜单ID查询该菜单的信息
            currentMenus =menuService.findMenuByMenuId(currentRoleMenuIds);
        }

        //创建集合创建保存树节点信息
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Menu menu : menuList) {
            //定义变量，表示是否选中
            String checkArr ="0";//0表示复选框不选中，1表示选中复选框
            //内层循环遍历当前角色拥有的权限菜单
            //循环比较的原因：比较两个集合中的数据是否有相同的，有相同的表示当前角色拥有这个权限
            for (Menu currentMenu:currentMenus) {
                //如果ID相等，表示当前角色有这个菜单，有这个菜单则需要将复选框选中
                if(menu.getId()==currentMenu.getId()){
                    checkArr="1";//选中
                    break;
                }

            }
            //定义变量，表示菜单是否展开
            Boolean spread = (menu.getSpread()==null||menu.getSpread()==1)?true:false;
            treeNodes.add(new TreeNode(menu.getId(),menu.getPid(),menu.getTitle(),spread,checkArr));
        }
        //将数据返回到页面
        return new DataGridViewResult(treeNodes);
    }

    /**
     * 分配菜单
     * @param ids
     * @param roleId
     * @return
     */
    @RequestMapping("/saveRoleMenu")
    public String saveRoleMenu(String ids,Integer roleId){
        Map<String,Object> map = new HashMap<String,Object>();
        //调用保存角色菜单关系的方法
        if(roleService.saveRoleMenu(ids,roleId)>0){
            map.put("message","菜单分配成功");
        }else{
            map.put("message","菜单分配失败");
        }
        return JSON.toJSONString(map);
    }
}
