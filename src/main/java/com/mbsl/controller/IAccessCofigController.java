/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.Access;
import java.util.List;
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
@RequestMapping("accessconfig")
@CrossOrigin(origins = {"*"})
public interface IAccessCofigController {

    @PostMapping("")
    ResponseEntity<String> accessConfig(@RequestBody Access userAccess);

    @PostMapping("/get_acce_by_id")
    ResponseEntity<Access> getAccessListByID(@RequestBody Access userAccess);

    @PostMapping("/get_access_list")
    ResponseEntity<List<Access>> getAccessLiset(@RequestBody Access userAccess);

    @PutMapping("/updaet_assess_config")
    ResponseEntity<String> updateaccessConfig(@RequestBody Access userAccess);

    @GetMapping("/get_dept")
    ResponseEntity<List<Access>> getAllDepartment();

    @GetMapping("/get_reg")
    ResponseEntity<List<Access>> getAllRegion();

}
