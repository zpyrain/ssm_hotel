package com.bdqn.controller.admin;


import com.alibaba.fastjson.JSON;
import com.bdqn.entity.Dept;
import com.bdqn.service.DeptService;
import com.bdqn.service.EmployeeService;
import com.bdqn.utils.DataGridViewResult;
import com.bdqn.utils.SystemConstant;
import com.bdqn.vo.DeptVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/dept")
public class DeptController {



    @Resource
    private DeptService deptService;

    @Resource
    private EmployeeService employeeService;


    /**
     * 查询部门列表
     * @param deptVo
     * @return
     */
    @RequestMapping("/list")
    public DataGridViewResult list(DeptVo deptVo){
        //设置分页信息
        PageHelper.startPage(deptVo.getPage(),deptVo.getLimit());
        //调用分页查询的方法
        List<Dept> deptList = deptService.findDeptListByPage(deptVo);
        //创建分页对象
        PageInfo<Dept> pageInfo = new PageInfo<Dept>(deptList);
        //返回数据
        return new DataGridViewResult(pageInfo.getTotal(),pageInfo.getList());
    }

}
