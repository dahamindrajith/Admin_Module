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
@RequestMapping("transfer")
@CrossOrigin(origins = {"*"})
public interface IUserTransferController {

    @PostMapping("/user_transfer")
    ResponseEntity<String> userTransfer(@RequestBody User transferUser);

    @GetMapping("/transfer_users")
    ResponseEntity<List<User>> getAllTransferUser();

    @PostMapping("/get_list_transfer_by_epf")
    ResponseEntity<User> getListTransferById(@RequestBody User transferUser);

    @PutMapping("/update_Transfer_user")
    ResponseEntity<String> updateTransferUser(@RequestBody User transferUser);

    @DeleteMapping("/delete_Transfer_user")
    ResponseEntity<String> deleteTransferUser(@RequestBody User transferUser);

    @PostMapping("/get_branch_bept")
    ResponseEntity<List<User>> getAllBranchOrDept(@RequestBody User user);

    @PostMapping("/get_list_to_btn_transfer_by_epf")
    ResponseEntity<User> getToButtonListTransferById(@RequestBody User transferUser);

    @PostMapping("/get_approve_info_t")
    ResponseEntity<User> getAppInfoT(@RequestBody User transferUser);

}
