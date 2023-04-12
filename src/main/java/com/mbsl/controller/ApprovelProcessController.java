/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.ApprovelProcess;
import com.mbsl.service.ApprovelProcessService;
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
public class ApprovelProcessController implements IApprovelProcessController{

@Autowired
    private ApprovelProcessService approvelProcessService;


@Override
    public ResponseEntity<String> approveConfirm(@RequestBody ApprovelProcess approvelProcess) {

        return new ResponseEntity<>(approvelProcessService.approveConfirm(approvelProcess), HttpStatus.OK);
//          return new ResponseEntity<>(userTransfersService.userTransfers(user), HttpStatus.OK);
    }
    
}
