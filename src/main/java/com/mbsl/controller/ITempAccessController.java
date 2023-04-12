/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

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
@RequestMapping("tempacc")
@CrossOrigin(origins = {"*"})
public interface ITempAccessController {

    @PutMapping("/grant_temp_access")
    ResponseEntity<String> tempAccessGrant(@RequestBody User tempAccess);

    @GetMapping("/get_all_temp_access")
    ResponseEntity<List<User>> getAllTempAccess();

    @PostMapping("/get_list_TempAccess_by_epf")
    ResponseEntity<User> getListTempAccessById(@RequestBody User tempAccess);

    @PutMapping("/update_TempAccess")
    ResponseEntity<String> updateTempAccess(@RequestBody User tempAccess);

    @DeleteMapping("/delete_TempAccess")
    ResponseEntity<String> deleteTempAccess(@RequestBody User tempAccess);

    @PostMapping("/get_branch_bept")
    ResponseEntity<List<User>> getAllBranchOrDept(@RequestBody User user);

    @PostMapping("/get_list_to_select_btn_temp_by_epf")
    ResponseEntity<User> getToSelectButtonTempById(@RequestBody User tempAccess);

    @PutMapping("/remove_TempAccess/{epfNo}")
    ResponseEntity<String> removeTempAccess(@PathVariable("epfNo") String epfNo);

    @PostMapping("/get_approve_info_te")
    ResponseEntity<User> getAppInfoTe(@RequestBody User tempAccess);

    @PutMapping("/remove_manualtemp")
    ResponseEntity<String> removeTempMnually(@RequestBody User tempAccess);

}
