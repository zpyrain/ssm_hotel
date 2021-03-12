package com.bdqn.dao;

import com.bdqn.entity.Menu;

import java.util.List;

public interface MenuMapper {
    /**
     * 查询所有菜单列表
     * @return
     */
    List<Menu> findMenuList();

    /**
     * 根据角色ID查询该角色拥有的菜单ID集合
     * @param roleid
     * @return
     */
    List<Integer> findMenuIdListByRoleId(int roleid);

    /**
     * 根据菜单编号查询菜单信息
     * @param currentRoleMenuIds
     * @return
     */
    List<Menu> findMenuByMenuId(List<Integer> currentRoleMenuIds);
}
