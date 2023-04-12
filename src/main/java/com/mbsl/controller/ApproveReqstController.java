/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import com.mbsl.service.ApproveReqstService;
import java.util.List;
import javax.servlet.http.HttpSession;
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
public class ApproveReqstController implements IApproveReqstController {

    @Autowired
    private ApproveReqstService approveReqstService;

    @Override
    public ResponseEntity<List<User>> getApproveRqsts(HttpSession session) {

        return new ResponseEntity<>(approveReqstService.getApproveRqsts(session), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> getListByRqstId(@RequestBody User approveRqst) {

        return new ResponseEntity<>(approveReqstService.getListByRqstId(approveRqst), HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<User> test(String RqstID, String RqstType) {
//
//        return new ResponseEntity<>(approveReqstService.test(RqstID, RqstType), HttpStatus.OK);
//    }
    @Override
    public ResponseEntity<String> approvedMessage(@RequestBody User approveRqst, HttpSession session) {

        return new ResponseEntity<>(approveReqstService.approvedMessage(approveRqst, session), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> rejecctMessage(@RequestBody User approveRqst, HttpSession session) {

        return new ResponseEntity<>(approveReqstService.rejecctMessage(approveRqst, session), HttpStatus.OK);

    }

}
