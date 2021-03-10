package com.bdqn.controller.admin;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Employee;
import com.bdqn.entity.Menu;
import com.bdqn.service.MenuService;
import com.bdqn.utils.MenuNode;
import com.bdqn.utils.SystemConstant;
import com.bdqn.utils.TreeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/menu")
public class MenuController {
    //注入MenuService
    @Resource
    private MenuService menuService;


    /**
     * 加载首页左侧菜单导航
     * @param session
     * @return
     */
    @RequestMapping("/loadMenuList")
    public String loadMenuList(HttpSession session){
        //创建Map集合，保存MenuInfo菜单信息
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        //创建Map集合，保存homeInfo信息
        Map<String,Object> homeInfo = new LinkedHashMap<String,Object>();
        //创建Map集合，保存logoInfo信息
        Map<String,Object> logoInfo = new LinkedHashMap<String,Object>();
        //调用查询所有菜单列表的方法
        List<Menu> menuList = menuService.findMenuList();//该方法无论是哪个角色的用户登录，都能够查询所有的功能模块
        //获取当前登录员工
        Employee employee = (Employee) session.getAttribute(SystemConstant.LOGINUSER);
        //根据当前用户的角色动态显示菜单列表
       // List<Menu> menuList = menuService.findMenuListByEmployeeId(employee.getId());
        //创建集合，保存菜单关系
        List<MenuNode> menuNodeList = new ArrayList<MenuNode>();
        //循环遍历菜单列表,目的是创建菜单之间层级关系
        for (Menu m : menuList) {
            //创建菜单节点对象
            MenuNode menuNode = new MenuNode();
            menuNode.setHref(m.getHref());//链接地址
            menuNode.setIcon(m.getIcon());//菜单图标
            menuNode.setId(m.getId());//菜单编号
            menuNode.setPid(m.getPid());//父级菜单编号
            menuNode.setSpread(m.getSpread());//是否展开
            menuNode.setTarget(m.getTarget());//打开方式
            menuNode.setTitle(m.getTitle());//菜单名称
            //将对象添加到集合
            menuNodeList.add(menuNode);
        }
        //保存HomeInfo信息
        homeInfo.put("title","首页");
        homeInfo.put("href","/admin/desktop");
        //保存logoInfo信息
        logoInfo.put("title","酒店管理系统");//logo标题
        logoInfo.put("image","/statics/layui/images/logo.png");//logo图片
        logoInfo.put("href","/admin/home.html");//首页地址
        //将菜单信息添加到MenuInfo集合中
        map.put("menuInfo", TreeUtil.toTree(menuNodeList,0));
        map.put("homeInfo",homeInfo);
        map.put("logoInfo",logoInfo);

        return JSON.toJSONString(map);
    }
}
