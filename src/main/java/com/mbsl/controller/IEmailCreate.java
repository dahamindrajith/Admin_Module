/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author MBSL2523
 */
@RequestMapping("emlctrt")
@CrossOrigin(origins = {"*"})
public interface IEmailCreate {

    @GetMapping("/get_users")
    ResponseEntity<List<User>> getEmailToCreate();

    @PostMapping("/rqst_bydate")
    ResponseEntity<User> getDataByRqstId(@RequestBody User mailCreate);

    @PostMapping("/confrm_sub")
    ResponseEntity<String> cofrmSubmition(@RequestBody User mailCreate);

}
