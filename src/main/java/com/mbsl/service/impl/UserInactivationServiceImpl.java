/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.UserInactivationDao;
import com.mbsl.model.User;
import com.mbsl.service.UserInactivationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class UserInactivationServiceImpl implements UserInactivationService {

    @Autowired
    private UserInactivationDao userInactivationDao;

    @Override
    public String userInact(User userInactivation) {

        return userInactivationDao.userInact(userInactivation);
    }

    @Override
    public List<User> getInactUsers() {
        return userInactivationDao.getInactUsers();
    }

    @Override
    public User getListUserIactById(User userInactivation) {
        return userInactivationDao.getListUserIactById(userInactivation);
    }

    @Override
    public String updateUserIact(User userInactivation) {
        return userInactivationDao.updateUserIact(userInactivation);
    }

    @Override
    public String deleteInactUser(User userInactivation) {
        return userInactivationDao.deleteInactUser(userInactivation);
    }

    @Override
    public User getToSelectButtonById(User userInactivation) {
        return userInactivationDao.getToSelectButtonById(userInactivation);
    }

    @Override
    public User getAppInfoI(User userInactivation) {
        return userInactivationDao.getAppInfoI(userInactivation);
    }

}
