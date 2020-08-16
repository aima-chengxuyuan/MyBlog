package com.lz.service.impl;

import com.lz.entity.User;
import com.lz.mapper.UserMapper;
import com.lz.service.UserService;
import com.lz.util.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户操作实现类
 *
 * @author jc
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User login(String username, String password) {
        return userMapper.login(username, MD5Utils.code(password));
    }
}
