/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author MBSL2523
 */
@RequestMapping("approverqst")
@CrossOrigin(origins = {"*"})
public interface IApproveReqstController {

    @GetMapping("/approvel_rqst")
    ResponseEntity<List<User>> getApproveRqsts(HttpSession session);

    @PostMapping("/get_list_by_rqstid")
    ResponseEntity<User> getListByRqstId(@RequestBody User approveRqst);

//    @GetMapping("/admin_srv/{RqstID}/{RqstType}")
//    ResponseEntity<User>test(@PathVariable("RqstID") String RqstID, @PathVariable("RqstType") String RqstType);
    @PutMapping("/approve_done")
    ResponseEntity<String> approvedMessage(@RequestBody User approveRqst, HttpSession session);

    @PutMapping("/reject_done")
    ResponseEntity<String> rejecctMessage(@RequestBody User approveRqst, HttpSession session);
}
