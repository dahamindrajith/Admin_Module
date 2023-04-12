/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.Designation;
import com.mbsl.model.Profile;
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
 * @author MBSL2491
 */
@RequestMapping("profile")
@CrossOrigin(origins = {"*"})
public interface IProfileController {

    @PostMapping("")
    ResponseEntity<String> createProfile(@RequestBody Profile profileCreate);

    @GetMapping("/table_load")
    ResponseEntity<List<Profile>> getTableLoad();

    @PostMapping("/get_all_prof")
    ResponseEntity<List<Profile>> getAllProfiles(@RequestBody Profile profileCreate);

    @PutMapping("/update_profile")
    ResponseEntity<String> updateProfiles(@RequestBody Profile profileCreate);

    @DeleteMapping("/delete_Profile")
    ResponseEntity<String> deleteProfiles(@RequestBody Profile profileCreate);

    @GetMapping("/sig_prof_list")
    ResponseEntity<List<Profile>> getSignatureProfile();

    @PostMapping("/get_cc_list")
    ResponseEntity<List<Profile>> getCCList(@RequestBody Profile profileCreate);

    @PostMapping("/get_list_by_profile")
    ResponseEntity<Profile> getListByProfile(@RequestBody Profile profileCreate);

    @PostMapping("/get_prof_bod")
    ResponseEntity<List<Profile>> getAllProfilesByBoD(@RequestBody User user);

}
