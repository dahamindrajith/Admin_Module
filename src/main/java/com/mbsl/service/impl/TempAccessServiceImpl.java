/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.TempAccessDao;
import com.mbsl.model.User;
import com.mbsl.service.TempAccessService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class TempAccessServiceImpl implements TempAccessService {

    @Autowired
    private TempAccessDao tempAccessDao;

    @Override
    public String tempAccessGrant(User tempAccess) {
        return tempAccessDao.tempAccessGrant(tempAccess);
    }

    @Override
    public List<User> getAllTempAccess() {
        return tempAccessDao.getAllTempAccess();
    }

    @Override
    public User getListTempAccessById(User tempAccess) {
        return tempAccessDao.getListTempAccessById(tempAccess);
    }

    @Override
    public String updateTempAccess(User tempAccess) {
        return tempAccessDao.updateTempAccess(tempAccess);
    }

    @Override
    public String deleteTempAccess(User tempAccess) {
        return tempAccessDao.deleteTempAccess(tempAccess);
    }

    @Override
    public List<User> getAllBranchOrDept(User user) {
        return tempAccessDao.getAllBranchOrDept(user);
    }

    @Override
    public User getToSelectButtonTempById(User tempAccess) {
        return tempAccessDao.getToSelectButtonTempById(tempAccess);
    }

    @Override
    public String removeTempAccess(String epfNo) {
        return tempAccessDao.removeTempAccess(epfNo);
    }

    @Override
    public User getAppInfoTe(User tempAccess) {
        return tempAccessDao.getAppInfoTe(tempAccess);
    }

    @Override
    public String removeTempMnually(User tempAccess) {
        return tempAccessDao.removeTempMnually(tempAccess);
    }

}
