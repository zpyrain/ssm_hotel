package com.bdqn.service.impl;

import com.bdqn.dao.DeptMapper;
import com.bdqn.entity.Dept;
import com.bdqn.service.DeptService;
import com.bdqn.vo.DeptVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    /**
     * 查询部门列表
     *
     * @param deptVo
     * @return
     */
    public List<Dept> findDeptListByPage(DeptVo deptVo) {
        return deptMapper.findDeptListByPage(deptVo);
    }

    /**
     * 添加部门
     * @param dept
     * @return
     */
    @Override
    public int addDept(Dept dept) {
        //保存创建时间
        dept.setCreateDate(new Date());
        return deptMapper.addDept(dept);
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @Override
    public int updateDept(Dept dept) {
        return deptMapper.updateDept(dept);
    }

    @Override
    public int deleteById(Integer id) {
        return deptMapper.deleteById(id);
    }

    @Override
    public List<Dept> findDeptList() {
        return deptMapper.findDeptList();
    }
}
