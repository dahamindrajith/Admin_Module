/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.Classes.passwordGenerator;
import com.mbsl.dao.PasswordGeneratorDao;
import com.mbsl.model.User;
import com.mbsl.service.PasswordGeneratorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService{

@Autowired
    private PasswordGeneratorDao passwordGeneratorDao;

    @Override
    public String generatePassword() {
        return passwordGeneratorDao.generatePassword();
    }
    
}
