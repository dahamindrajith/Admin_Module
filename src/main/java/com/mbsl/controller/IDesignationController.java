/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

import com.google.gson.JsonArray;
import com.mbsl.model.Designation;
import com.mbsl.model.User;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("designation")
@CrossOrigin(origins = {"*"})
public interface IDesignationController {

    @PostMapping("")
    ResponseEntity<String> createDesig(@RequestBody Designation createDesignation);

    @GetMapping()
    ResponseEntity<List<Designation>> getAllDesignations();

    @GetMapping("/admin_class")
    public String getAllDesignationsTest();

    @DeleteMapping("/delete_designation")
    ResponseEntity<String> deleteDesignation(@RequestBody Designation createDesignation);
}
