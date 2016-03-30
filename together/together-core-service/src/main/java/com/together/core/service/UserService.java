/**
 * Alipay.com Inc. Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.together.core.service;

import com.together.common.dal.UserDao;
import com.together.common.dal.UserDaoImpl;

/**
 * 
 * @author Pengju.zpj
 * @version $Id: UserService.java, v 0.1 2016年2月24日 上午10:30:37 Pengju.zpj Exp $
 */
public class UserService {

    public String getUserInfo() {
        UserDao userDao = new UserDaoImpl();
        return userDao.getUserInfo();
    }
}
