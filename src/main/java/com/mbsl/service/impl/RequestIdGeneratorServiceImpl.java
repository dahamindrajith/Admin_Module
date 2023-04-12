/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.RequestIdGeneratorDao;
import com.mbsl.model.User;
import com.mbsl.service.RequestIdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class RequestIdGeneratorServiceImpl implements RequestIdGeneratorService{

@Autowired
    private RequestIdGeneratorDao requestIdGeneratorDao;

    @Override
    public String generateRequestId(User user) {
        return requestIdGeneratorDao.generateRequestId(user);
    }
    
}
