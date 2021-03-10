package com.bdqn.service;

import com.bdqn.entity.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 查询所有菜单列表
     * @return
     */
    List<Menu> findMenuList();

}
