/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import com.mbsl.service.PasswordGeneratorService;
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
public class PasswordGenerator implements IPasswordGenerator{

@Autowired
    private PasswordGeneratorService passwordGeneratorService;

  @Override
    public String generatePassword() {

        return passwordGeneratorService.generatePassword();
    }
    
}
