package com.bdqn.controller;

import com.alibaba.fastjson.JSON;
import com.bdqn.entity.User;
import com.bdqn.service.UserService;
import com.bdqn.utils.SystemConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller//有非无刷新的方法
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    /**
     * 注册
     * @param user
     * @return
     *
     * @ResponseBody的作用其实是将java对象转为json格式的数据。
     * @responseBody注解的作用是将controller的方法返回的对象
     * 通过适当的转换器转换为指定的格式之后，写入到response对象的
     * body区，通常用来返回JSON数据或者是XML数据。
     */
    @ResponseBody//无刷新的方法
    @RequestMapping("/register")
    public String register(User user){
        //创建Map集合，保存结果信息
        Map<String,Object> map = new HashMap<String,Object>();
        //调用注册的方法
        if(userService.addUser(user)>0){
            map.put(SystemConstant.SUCCESS,true);
            map.put(SystemConstant.MESSAGE,"恭喜你,注册成功!");
        }else{
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"很遗憾,注册失败,请重新尝试!");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 检查用户名是否存在
     * @param loginName
     * @return
     */
    @ResponseBody//无刷新的方法
    @RequestMapping("/checkName")
    public String checkName(String loginName){
        //创建Map集合，保存结果信息
        Map<String,Object> map = new HashMap<String,Object>();
        //调用根据用户名查询用户信息的方法
        if(userService.findUserByName(loginName)!=null){
            map.put(SystemConstant.EXIST,true);
            map.put(SystemConstant.MESSAGE,"用户名已被使用，请重新输入!");
        }else{
            map.put(SystemConstant.EXIST,false);
        }
        return JSON.toJSONString(map);
    }
    /**
     * 登录
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        //创建Map集合，保存结果信息
        Map<String,Object> map = new HashMap<String,Object>();
        //调用登录的方法
        User loginUser = userService.login(loginName, password);
        //判断对象是否为空
        if(loginUser!=null){
            map.put(SystemConstant.SUCCESS,true);
            loginUser.setPassword(null);//清空
            //保存用户信息到会话中
            session.setAttribute(SystemConstant.FRONT_LOGIN_USER,loginUser);
        }else{
            map.put(SystemConstant.SUCCESS,false);
            map.put(SystemConstant.MESSAGE,"用户名或密码错误，请重新输入！");
        }
        return JSON.toJSONString(map);
    }

}
