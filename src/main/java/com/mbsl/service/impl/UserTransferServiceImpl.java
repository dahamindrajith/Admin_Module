/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.UserTransferDao;
import com.mbsl.model.User;
import com.mbsl.service.UserTransferService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class UserTransferServiceImpl implements UserTransferService {

    @Autowired
    private UserTransferDao userTransferDao;

    @Override
    public String transferUser(User transferUser) {

        return userTransferDao.transferUser(transferUser);
    }

    @Override
    public List<User> getTransferUsers() {
        return userTransferDao.getTransferUsers();
    }

    @Override
    public User getListTransferById(User transferUser) {
        return userTransferDao.getListTransferById(transferUser);
    }

    @Override
    public String updateTransferUser(User transferUser) {
        return userTransferDao.updateTransferUser(transferUser);
    }

    @Override
    public String deleteTransferUser(User transferUser) {
        return userTransferDao.deleteTransferUser(transferUser);
    }

    @Override
    public List<User> getAllBranchOrDept(User user) {
        return userTransferDao.getAllBranchOrDept(user);
    }

    @Override
    public User getToButtonListTransferById(User transferUser) {
        return userTransferDao.getToButtonListTransferById(transferUser);
    }

@Override
    public User getAppInfoT(User transferUser) {
        return userTransferDao.getAppInfoT(transferUser);
    }

}
