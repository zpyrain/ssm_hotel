package com.bdqn.dao;

import com.bdqn.entity.Role;
import com.bdqn.vo.RoleVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    /**
     * 查询角色列表
     * @param roleVo
     * @return
     */
    List<Role> findRoleList(RoleVo roleVo);

    /**
     *添加角色
     * @param role
     * @return
     */
    int addRole(Role role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 添加角色菜单关系数据
     * @param roleId
     * @param menuId
     */
    @Insert("insert into sys_role_menu(mid,rid) values(#{menuId},#{roleId})")
    void addRoleMenu(@Param("roleId") Integer roleId, @Param("menuId")String menuId);

    /**
     * 删除原有关系
     * @param roleId
     */
    @Delete("delete from sys_role_menu where rid =#{roleId}")
    void deleteRoleMenu(Integer roleId);
}
