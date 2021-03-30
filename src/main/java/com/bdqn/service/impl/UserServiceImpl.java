package com.bdqn.service.impl;

import com.bdqn.dao.UserMapper;
import com.bdqn.entity.User;
import com.bdqn.service.UserService;
import com.bdqn.utils.PasswordUtil;
import com.bdqn.utils.SystemConstant;
import com.bdqn.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    /**
     * 添加用户
     * @param user
     * @return
     */
    public int addUser(User user) {
        //自动生成盐值
        user.setSalt(UUIDUtils.randomUUID());//shiro安全验证框架
        //密码加密
        user.setPassword(PasswordUtil.md5(user.getPassword(),user.getSalt(), SystemConstant.PASSWORD_COUNT));
        return userMapper.addUser(user);
    }

    @Override
    public User findUserByName(String loginName) {
        return userMapper.findUserByName(loginName);
    }

    @Override
    public User login(String loginName, String password) {
        //调用查询用户信息的方法
        User loginUser = userMapper.findUserByName(loginName);
        if(loginUser!=null){
            //密码加密
            String newPassword =PasswordUtil.md5(password,loginUser.getSalt(),SystemConstant.PASSWORD_COUNT);
            //比较密码是否相等
            if(loginUser.getPassword().equals(newPassword)){
                return loginUser;
            }

        }
        return null;
    }
}
