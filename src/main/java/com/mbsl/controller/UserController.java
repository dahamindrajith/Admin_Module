/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.google.gson.JsonArray;
import com.mbsl.model.User;
import com.mbsl.model.StatusList;
import com.mbsl.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBSL2395
 */
@RestController
public class UserController implements IUserController {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<String> createUser(@RequestBody User user) {

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
//          return new ResponseEntity<>(userTransfersService.userTransfers(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {

        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateUser(@RequestBody User user) {

        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteUser(@RequestBody User user) {

        return new ResponseEntity<>(userService.deleteUser(user), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<User> getListById(User user) {
     

        return new ResponseEntity<>(userService.getListById(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<User>> getAllBranchOrDept(User user) {

       return new ResponseEntity<>(userService.getAllBranchOrDept(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getAppInfoC(User user) {
     

        return new ResponseEntity<>(userService.getAppInfoC(user), HttpStatus.OK);
    }

}
