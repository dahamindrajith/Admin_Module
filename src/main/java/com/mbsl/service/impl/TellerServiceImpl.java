/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.TellerDao;
import com.mbsl.model.Teller;
import com.mbsl.service.TellerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2491
 */
@Service
public class TellerServiceImpl implements TellerService {

    @Autowired
    private TellerDao tellerDao;

    @Override
    public String createTeller(Teller tellerCreate) {

        return tellerDao.createTeller(tellerCreate);
    }

    @Override
    public List<Teller> getAllTellers() {
        return tellerDao.getAllTellers();
    }

    @Override
    public String updateTellers(Teller tellerCreate) {
        return tellerDao.updateTellers(tellerCreate);
    }

    @Override
    public String deleteTellers(Teller tellerCreate) {
        return tellerDao.deleteTellers(tellerCreate);
    }

    @Override
    public List<Teller> getAllBranchOrDept() {
        return tellerDao.getAllBranchOrDept();
    }

    @Override
    public List<Teller> getTill(Teller tellerCreate) {
        return tellerDao.getTill(tellerCreate);
    }

    @Override
    public Teller getTellerByBranch(Teller tellerCreate) {
        return tellerDao.getTellerByBranch(tellerCreate);
    }

    @Override
    public List<Teller> getTellerByUBranch(Teller tellerCreate) {
        return tellerDao.getTellerByUBranch(tellerCreate);
    }
}
