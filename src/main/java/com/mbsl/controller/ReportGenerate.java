/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.User;
import com.mbsl.service.DonloadRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author MBSL2523
 */
public class ReportGenerate implements IReportGenerate {

    @Autowired
    private DonloadRepoService donloadRepoService;

    @Override
    public ResponseEntity<User> donloadRepo(@RequestBody User repo) {

        return new ResponseEntity<>(donloadRepoService.donloadRepo(repo), HttpStatus.OK);

    }

}
