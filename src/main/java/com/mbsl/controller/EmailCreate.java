/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import com.mbsl.service.EmailCreateService;
import com.mbsl.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBSL2523
 */
@RestController
public class EmailCreate implements IEmailCreate {

    @Autowired
    private EmailCreateService emailCreateService;

    @Override
    public ResponseEntity<List<User>> getEmailToCreate() {

        return new ResponseEntity<>(emailCreateService.getEmailToCreate(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getDataByRqstId(User mailCreate) {

        return new ResponseEntity<>(emailCreateService.getDataByRqstId(mailCreate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> cofrmSubmition(User mailCreate) {

        return new ResponseEntity<>(emailCreateService.cofrmSubmition(mailCreate), HttpStatus.OK);
    }

}
