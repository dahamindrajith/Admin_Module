/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.Access;
import com.mbsl.service.AccessCofigService;
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
public class AccessCofigController implements IAccessCofigController {

    @Autowired
    private AccessCofigService accessCofigService;

    @Override
    public ResponseEntity<String> accessConfig(@RequestBody Access userAccess) {

        return new ResponseEntity<>(accessCofigService.accessConfig(userAccess), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Access> getAccessListByID(@RequestBody Access userAccess) {

        return new ResponseEntity<>(accessCofigService.getAccessListByID(userAccess), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Access>> getAccessLiset(@RequestBody Access userAccess) {

        return new ResponseEntity<>(accessCofigService.getAccessLiset(userAccess), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateaccessConfig(@RequestBody Access userAccess) {

        return new ResponseEntity<>(accessCofigService.updateaccessConfig(userAccess), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Access>> getAllDepartment() {

        return new ResponseEntity<>(accessCofigService.getAllDepartment(), HttpStatus.OK);
    }

@Override
    public ResponseEntity<List<Access>> getAllRegion() {

        return new ResponseEntity<>(accessCofigService.getAllRegion(), HttpStatus.OK);
    }

}
