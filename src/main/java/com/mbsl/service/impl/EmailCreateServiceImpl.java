/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.EmailCreateDao;
import com.mbsl.model.User;
import com.mbsl.service.EmailCreateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class EmailCreateServiceImpl implements EmailCreateService{

@Autowired
    private EmailCreateDao emailCreateDao;

@Override
    public List<User> getEmailToCreate() {
        return emailCreateDao.getEmailToCreate();
    }

@Override
    public User getDataByRqstId(User mailCreate) {
        return emailCreateDao.getDataByRqstId(mailCreate);
    }

@Override
    public String cofrmSubmition(User mailCreate) {
        return emailCreateDao.cofrmSubmition(mailCreate);
    }
    
}
