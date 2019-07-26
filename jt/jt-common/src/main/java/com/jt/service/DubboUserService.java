package com.jt.service;


import com.jt.pojo.User;

/**
 * 定义中立的第三方接口
 */
public interface DubboUserService {
    /**
     * 完成用户入库
     * @param user
     */
    void saveUser(User user);

    String doLogin(User user);
}
