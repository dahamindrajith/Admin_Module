/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.HrUpdateDao;
import com.mbsl.model.Profile;
import com.mbsl.model.User;
import com.mbsl.service.HrUpdateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class HrUpdateServiceImpl implements HrUpdateService{

@Autowired
    private HrUpdateDao hrUpdateDao;

    @Override
    public String createHrList(User approveList) {
        return hrUpdateDao.createHrList(approveList);
    }

    @Override
    public List<User> getList() {
        return hrUpdateDao.getList();
    }

    @Override
    public String deleteList(User approveList) {
        return hrUpdateDao.deleteList(approveList);
    }

@Override
    public User getListHrById(User approveList) {
        return hrUpdateDao.getListHrById(approveList);
    }

@Override
    public List<Profile> getBProfile() {
        return hrUpdateDao.getBProfile();
    }

    
}
