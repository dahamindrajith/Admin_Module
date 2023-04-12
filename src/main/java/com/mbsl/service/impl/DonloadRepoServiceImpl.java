/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.DonloadRepoDao;
import com.mbsl.model.User;
import com.mbsl.service.DonloadRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class DonloadRepoServiceImpl implements DonloadRepoService{

@Autowired
    private DonloadRepoDao donloadRepoDao;

@Override
    public User donloadRepo(User repo) {
        return donloadRepoDao.donloadRepo(repo);
    }
    
}
