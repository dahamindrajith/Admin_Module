/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import com.mbsl.service.UserTransferService;
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
public class UserTransferController implements IUserTransferController {

    @Autowired
    private UserTransferService userTransferService;

    @Override
    public ResponseEntity<String> userTransfer(@RequestBody User transferUser) {

        return new ResponseEntity<>(userTransferService.transferUser(transferUser), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<User>> getAllTransferUser() {

        return new ResponseEntity<>(userTransferService.getTransferUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getListTransferById(@RequestBody User transferUser) {

        return new ResponseEntity<>(userTransferService.getListTransferById(transferUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateTransferUser(@RequestBody User transferUser) {

        return new ResponseEntity<>(userTransferService.updateTransferUser(transferUser), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteTransferUser(@RequestBody User transferUser) {

        return new ResponseEntity<>(userTransferService.deleteTransferUser(transferUser), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<User>> getAllBranchOrDept(@RequestBody User user) {

        return new ResponseEntity<>(userTransferService.getAllBranchOrDept(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getToButtonListTransferById(@RequestBody User transferUser) {

        return new ResponseEntity<>(userTransferService.getToButtonListTransferById(transferUser), HttpStatus.OK);
    }

@Override
    public ResponseEntity<User> getAppInfoT(@RequestBody User transferUser) {

        return new ResponseEntity<>(userTransferService.getAppInfoT(transferUser), HttpStatus.OK);
    }

}
