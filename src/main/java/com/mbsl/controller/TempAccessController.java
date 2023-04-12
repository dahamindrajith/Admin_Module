/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import com.mbsl.service.TempAccessService;
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
public class TempAccessController implements ITempAccessController {

    @Autowired
    private TempAccessService tempAccessService;

    @Override
    public ResponseEntity<String> tempAccessGrant(@RequestBody User tempAccess) {

        return new ResponseEntity<>(tempAccessService.tempAccessGrant(tempAccess), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<User>> getAllTempAccess() {
        System.out.println("Test Test TEst");
        return new ResponseEntity<>(tempAccessService.getAllTempAccess(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getListTempAccessById(@RequestBody User tempAccess) {

        return new ResponseEntity<>(tempAccessService.getListTempAccessById(tempAccess), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateTempAccess(@RequestBody User tempAccess) {

        return new ResponseEntity<>(tempAccessService.updateTempAccess(tempAccess), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteTempAccess(@RequestBody User tempAccess) {

        return new ResponseEntity<>(tempAccessService.deleteTempAccess(tempAccess), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<User>> getAllBranchOrDept(@RequestBody User user) {

        return new ResponseEntity<>(tempAccessService.getAllBranchOrDept(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getToSelectButtonTempById(@RequestBody User tempAccess) {

        return new ResponseEntity<>(tempAccessService.getToSelectButtonTempById(tempAccess), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> removeTempAccess(String epfNo) {

        return new ResponseEntity<>(tempAccessService.removeTempAccess(epfNo), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<User> getAppInfoTe(@RequestBody User tempAccess) {

        return new ResponseEntity<>(tempAccessService.getAppInfoTe(tempAccess), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> removeTempMnually(@RequestBody User tempAccess) {

        return new ResponseEntity<>(tempAccessService.removeTempMnually(tempAccess), HttpStatus.OK);

    }
}
