/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.Profile;
import com.mbsl.model.User;
import com.mbsl.service.HrUpdateService;
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
public class HrUpdateController implements IHrUpdateController{

@Autowired
    private HrUpdateService hrUpdateService;

    @Override
    public ResponseEntity<String> createHrList(@RequestBody User approveList) {
    
        return new ResponseEntity<>(hrUpdateService.createHrList(approveList), HttpStatus.OK);
//          return new ResponseEntity<>(userTransfersService.userTransfers(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> getList() {

        return new ResponseEntity<>(hrUpdateService.getList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteList(@RequestBody User approveList) {

        return new ResponseEntity<>(hrUpdateService.deleteList(approveList), HttpStatus.OK);

    }

@Override
    public ResponseEntity<User> getListHrById(@RequestBody User approveList) {

        return new ResponseEntity<>(hrUpdateService.getListHrById(approveList), HttpStatus.OK);
    }
    
@Override
    public ResponseEntity<List<Profile>> getBProfile() {

        return new ResponseEntity<>(hrUpdateService.getBProfile(), HttpStatus.OK);
    }
}
