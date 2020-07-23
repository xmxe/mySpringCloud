package com.xmxe.service;

import com.xmxe.dao.LoginDao;
import com.xmxe.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    private final LoginDao loginDao;

    @Autowired
    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public List<User> getUserByUsername(String username) {
        return loginDao.getUserByUsername(username);
    }
    public List<String> getPermissionsByUsername(String username) {
        return loginDao.getPermissionsByUsername(username);
    }

    public List<String> getRoleByUsername(String username) {
        return loginDao.getRoleByUsername(username);
    }
}
