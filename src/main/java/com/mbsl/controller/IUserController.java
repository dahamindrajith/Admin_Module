/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

import com.google.gson.JsonArray;
import com.mbsl.model.User;
import com.mbsl.model.StatusList;
import java.util.List;
import javax.websocket.server.PathParam;
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
 * @author MBSL2395
 */
@RequestMapping("users")
@CrossOrigin(origins = {"*"})
public interface IUserController {

    @PostMapping("")
    ResponseEntity<String> createUser(@RequestBody User user);

    @GetMapping()
    ResponseEntity<List<User>> getAllUsers();

    @PutMapping("/update_user")
    ResponseEntity<String> updateUser(@RequestBody User user);

    @DeleteMapping("/delete_user")
    ResponseEntity<String> deleteUser(@RequestBody User user);

    @PostMapping("/get_list_by_epf")
    ResponseEntity<User> getListById(@RequestBody User user);

    @PostMapping("/get_branch_bept")
    ResponseEntity<List<User>> getAllBranchOrDept(@RequestBody User user);

    @PostMapping("/get_approve_info")
    ResponseEntity<User> getAppInfoC(@RequestBody User user);

}
