/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import com.mbsl.service.UserInactivationService;
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
public class UserInactivationController implements IUserInactivationController {

    @Autowired
    private UserInactivationService userInactivationService;

    @Override
    public ResponseEntity<String> userInact(@RequestBody User userInactivation) {

        return new ResponseEntity<>(userInactivationService.userInact(userInactivation), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<User>> getAllInactUsers() {

        return new ResponseEntity<>(userInactivationService.getInactUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getListUserIactById(@RequestBody User userInactivation) {

        return new ResponseEntity<>(userInactivationService.getListUserIactById(userInactivation), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateUserIact(@RequestBody User userInactivation) {

        return new ResponseEntity<>(userInactivationService.updateUserIact(userInactivation), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteInactUser(@RequestBody User userInactivation) {

        return new ResponseEntity<>(userInactivationService.deleteInactUser(userInactivation), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<User> getToSelectButtonById(@RequestBody User userInactivation) {

        return new ResponseEntity<>(userInactivationService.getToSelectButtonById(userInactivation), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getAppInfoI(@RequestBody User userInactivation) {

        return new ResponseEntity<>(userInactivationService.getAppInfoI(userInactivation), HttpStatus.OK);
    }

}
