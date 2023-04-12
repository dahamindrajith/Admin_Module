/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.google.gson.JsonArray;
import com.mbsl.dao.DesignationDao;
import com.mbsl.model.Designation;
import com.mbsl.service.DesignationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2523
 */
@Service
public class DesignationServiceImpl implements DesignationService{

    @Autowired
    private DesignationDao designationDao;

@Override
    public String createDesig(Designation createDesignation) {
    
        return designationDao.createDesig(createDesignation);
    }

@Override
    public List<Designation> getAllDesignations() {
        return designationDao.getAllDesignations();
    }

@Override
    public JsonArray getAllDesignationsTest() {
        return designationDao.getAllDesignationsTest();
    }

@Override
    public String deleteDesignation(Designation createDesignation) {
        return designationDao.deleteDesignation(createDesignation);
    }

}
