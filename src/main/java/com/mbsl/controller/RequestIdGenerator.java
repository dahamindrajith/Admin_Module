/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import com.mbsl.service.RequestIdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBSL2523
 */
@RestController
public class RequestIdGenerator implements IRequestIdGenerator{

@Autowired
    private RequestIdGeneratorService requestIdGeneratorService;

@Override
    public String generateRequestId( @RequestBody User user) {

        return requestIdGeneratorService.generateRequestId(user);
    }
    
}
