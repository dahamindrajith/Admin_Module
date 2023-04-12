/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.ApprovalPath;
import com.mbsl.service.ApprovePathService;
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
public class ApprovePathController implements IApprovePathController {

    @Autowired
    private ApprovePathService approvePathService;

    @Override
    public ResponseEntity<String> createApprovel(@RequestBody ApprovalPath approvePath) {

        return new ResponseEntity<>(approvePathService.createApprovel(approvePath), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<ApprovalPath>> getAllApprovelPaths(String value) {

        return new ResponseEntity<>(approvePathService.getAllApprovelPaths(value), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteApprovelPaths(@RequestBody ApprovalPath approvePath, String pathNo, String approvedLvl, String rqstType) {
        System.out.println("Test");
        return new ResponseEntity<>(approvePathService.deleteApprovelPaths(approvePath, pathNo, approvedLvl, rqstType), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<ApprovalPath>> getCCList() {

        return new ResponseEntity<>(approvePathService.getCCList(), HttpStatus.OK);
    }

}
