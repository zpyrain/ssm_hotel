package com.bdqn.service.impl;

import com.bdqn.dao.RoleMapper;
import com.bdqn.entity.Role;
import com.bdqn.service.RoleService;
import com.bdqn.vo.RoleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<Role> findRoleList(RoleVo roleVo) {
        return roleMapper.findRoleList(roleVo);
    }

    @Override
    public int addRole(Role role) {
        return roleMapper.addRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public int deleteById(Integer id) {
        return roleMapper.deleteById(id);
    }

    @Override
    public int saveRoleMenu(String ids, Integer roleId) {
        try {
            //删除原有的菜单关系
            roleMapper.deleteRoleMenu(roleId);//根据角色ID删除
            //将ids拆分成数组
            String [] idsStr =ids.split(",");
            //循环数组
            for(int i=0;i<idsStr.length;i++){
                //调用保存菜单角色关系的方法
                roleMapper.addRoleMenu(roleId,idsStr[i]);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> findRoleListByMap() {
        return roleMapper.findRoleListByMap();
    }

    @Override
    public List<Integer> findEmployeeRoleByEmployeeId(Integer employeeId) {
        return roleMapper.findEmployeeRoleByEmployeeId(employeeId);
    }
}
