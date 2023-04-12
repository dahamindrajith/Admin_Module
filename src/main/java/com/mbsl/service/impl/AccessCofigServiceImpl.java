/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.AccessCofigDao;
import com.mbsl.model.Access;
import com.mbsl.service.AccessCofigService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class AccessCofigServiceImpl implements AccessCofigService {

    @Autowired
    private AccessCofigDao accessCofigDao;

    @Override
    public String accessConfig(Access userAccess) {
        return accessCofigDao.accessConfig(userAccess);
    }

    @Override
    public Access getAccessListByID(Access userAccess) {
        return accessCofigDao.getAccessListByID(userAccess);
    }

    @Override
    public List<Access> getAccessLiset(Access userAccess) {
        return accessCofigDao.getAccessLiset(userAccess);
    }

    @Override
    public String updateaccessConfig(Access userAccess) {
        return accessCofigDao.updateaccessConfig(userAccess);
    }

    @Override
    public List<Access> getAllDepartment() {
        return accessCofigDao.getAllDepartment();
    }

@Override
    public List<Access> getAllRegion() {
        return accessCofigDao.getAllRegion();
    }

}
