/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.google.gson.JsonArray;
import com.mbsl.dao.UserDao;
import com.mbsl.model.User;
import com.mbsl.model.StatusList;
import com.mbsl.service.UserService;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2395
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public String updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public String deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    @Override
    public User getListById(User user) {
        return userDao.getListById(user);
    }

    @Override
    public List<User> getAllBranchOrDept(User user) {
        return userDao.getAllBranchOrDept(user);
    }

    @Override
    public User getAppInfoC(User user) {
        return userDao.getAppInfoC(user);
    }

}
