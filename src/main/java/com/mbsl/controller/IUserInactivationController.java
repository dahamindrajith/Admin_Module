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
@RequestMapping("inactivate")
@CrossOrigin(origins = {"*"})
public interface IUserInactivationController {

    @PostMapping("/user_inactivate")
    ResponseEntity<String> userInact(@RequestBody User userInactivation);

    @GetMapping("/inactivate_users")
    ResponseEntity<List<User>> getAllInactUsers();

    @PostMapping("/get_list_user_inact_by_epf")
    ResponseEntity<User> getListUserIactById(@RequestBody User userInactivation);

    @PutMapping("/update_user_inact")
    ResponseEntity<String> updateUserIact(@RequestBody User userInactivation);

    @DeleteMapping("/delete_inact_user")
    ResponseEntity<String> deleteInactUser(@RequestBody User userInactivation);

    @PostMapping("/get_list_to_select_btn_inact_by_epf")
    ResponseEntity<User> getToSelectButtonById(@RequestBody User userInactivation);

    @PostMapping("/get_approve_info_I")
    ResponseEntity<User> getAppInfoI(@RequestBody User userInactivation);
}
