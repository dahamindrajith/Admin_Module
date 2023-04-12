/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.Teller;
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
 * @author MBSL2491
 */
@RequestMapping("teller")
@CrossOrigin(origins = {"*"})
public interface ITellerController {

    @PostMapping("")
    ResponseEntity<String> createTeller(@RequestBody Teller tellerCreate);

    @GetMapping()
    ResponseEntity<List<Teller>> getAllTellers();

    @PutMapping("/update_tellers")
    ResponseEntity<String> updateTellers(@RequestBody Teller tellerCreate);

    @DeleteMapping("/delete_tellers")
    ResponseEntity<String> deleteTellers(@RequestBody Teller tellerCreate);

    @GetMapping("/get_branch_dept")
    ResponseEntity<List<Teller>> getAllBranchOrDept();

    @PostMapping("/get_till_by_branch")
    ResponseEntity<List<Teller>> getTill(@RequestBody Teller tellerCreate);

    @PostMapping("/get_teller_by_branch")
    ResponseEntity<Teller> getTellerByBranch(@RequestBody Teller tellerCreate);

    @PostMapping("/get_teller_by_ubranch")
    ResponseEntity<List<Teller>> getTellerByUBranch(@RequestBody Teller tellerCreate);

}
