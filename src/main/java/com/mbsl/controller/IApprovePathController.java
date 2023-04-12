/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.ApprovalPath;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author MBSL2523
 */
@RequestMapping("approvalpath")
@CrossOrigin(origins = {"*"})
public interface IApprovePathController {

    @PostMapping("")
    ResponseEntity<String> createApprovel(@RequestBody ApprovalPath approvePath);

    @GetMapping("/get_all_approlal_path/{value}")
    ResponseEntity<List<ApprovalPath>> getAllApprovelPaths(@PathVariable("value") String value);

    @DeleteMapping("/delete_approvalpath/{pathNo}/{approvedLvl}/{rqstType}")
    ResponseEntity<String> deleteApprovelPaths(@RequestBody ApprovalPath approvePath, @PathVariable("pathNo") String pathNo, @PathVariable("approvedLvl") String approvedLvl, @PathVariable("rqstType") String rqstType);

    @GetMapping("/get_cc_list")
    ResponseEntity<List<ApprovalPath>> getCCList();

}
