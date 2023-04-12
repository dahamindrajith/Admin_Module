/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.ApprovePathDao;
import com.mbsl.model.ApprovalPath;
import com.mbsl.service.ApprovePathService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class ApprovePathServiceImpl implements ApprovePathService {

    @Autowired
    private ApprovePathDao approvePathDao;

    @Override
    public String createApprovel(ApprovalPath approvePath) {
        return approvePathDao.createApprovel(approvePath);
    }

    @Override
    public List<ApprovalPath> getAllApprovelPaths(String value) {
        return approvePathDao.getAllApprovelPaths(value);
    }

    @Override
    public String deleteApprovelPaths(ApprovalPath approvePath, String pathNo, String approvedLvl, String rqstType) {
        return approvePathDao.deleteApprovelPaths(approvePath, pathNo, approvedLvl, rqstType);
    }

    @Override
    public List<ApprovalPath> getCCList() {
        return approvePathDao.getCCList();
    }
}
