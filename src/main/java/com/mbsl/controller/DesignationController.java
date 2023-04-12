/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.google.gson.JsonArray;
import com.mbsl.dao.impl.DesignationDaoImpl;
import com.mbsl.model.Designation;
import com.mbsl.model.User;
import com.mbsl.service.DesignationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBSL2523
 */
@RestController
public class DesignationController implements IDesignationController {

    @Autowired
    private DesignationService designationService;

    @Override
    public ResponseEntity<String> createDesig(@RequestBody Designation createDesignation) {

        return new ResponseEntity<>(designationService.createDesig(createDesignation), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Designation>> getAllDesignations() {

        return new ResponseEntity<>(designationService.getAllDesignations(), HttpStatus.OK);
    }

    @Override
    public String getAllDesignationsTest() {


        JsonArray result=designationService.getAllDesignationsTest();      
        return result.toString();
    }

    @Override
    public ResponseEntity<String> deleteDesignation(@RequestBody Designation createDesignation) {

        return new ResponseEntity<>(designationService.deleteDesignation(createDesignation), HttpStatus.OK);

    }
}
