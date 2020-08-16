package com.lz.service;

import com.lz.entity.User;

/**
 * 用户操作接口
 *
 * @author jc
 */
public interface UserService {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

}
