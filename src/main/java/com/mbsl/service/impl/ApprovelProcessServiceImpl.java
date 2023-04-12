/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.ApprovelProcessDao;
import com.mbsl.model.ApprovelProcess;
import com.mbsl.service.ApprovelProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class ApprovelProcessServiceImpl implements ApprovelProcessService{

@Autowired
    private ApprovelProcessDao approvelProcessDao;

   @Override
    public String approveConfirm(ApprovelProcess approvelProcess) {
        return approvelProcessDao.approveConfirm(approvelProcess);
    }
    
}
