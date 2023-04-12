/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.Teller;
import com.mbsl.service.TellerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBSL2491
 */
@RestController
public class TellerController implements ITellerController {

    @Autowired
    private TellerService tellerService;

    @Override
    public ResponseEntity<String> createTeller(@RequestBody Teller tellerCreate) {

        return new ResponseEntity<>(tellerService.createTeller(tellerCreate), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Teller>> getAllTellers() {

        return new ResponseEntity<>(tellerService.getAllTellers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateTellers(@RequestBody Teller tellerCreate) {

        return new ResponseEntity<>(tellerService.updateTellers(tellerCreate), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteTellers(@RequestBody Teller tellerCreate) {

        return new ResponseEntity<>(tellerService.deleteTellers(tellerCreate), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Teller>> getAllBranchOrDept() {

        return new ResponseEntity<>(tellerService.getAllBranchOrDept(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Teller>> getTill(@RequestBody Teller tellerCreate) {

        return new ResponseEntity<>(tellerService.getTill(tellerCreate), HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<Teller> getTellerByBranch(@RequestBody Teller tellerCreate) {

        return new ResponseEntity<>(tellerService.getTellerByBranch(tellerCreate), HttpStatus.OK);
    }

@Override
    public ResponseEntity<List<Teller>> getTellerByUBranch(@RequestBody Teller tellerCreate) {

        return new ResponseEntity<>(tellerService.getTellerByUBranch(tellerCreate), HttpStatus.OK);
    }

}
