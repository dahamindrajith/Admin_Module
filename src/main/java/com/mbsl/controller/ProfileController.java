/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.controller;

import com.mbsl.model.Profile;
import com.mbsl.model.User;
import com.mbsl.service.ProfileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MBSL2491
 */
@RestController
public class ProfileController implements IProfileController {

    @Autowired
    private ProfileService profileService;

    @Override
    public ResponseEntity<String> createProfile(@RequestBody Profile profileCreate) {

        return new ResponseEntity<>(profileService.createProfile(profileCreate), HttpStatus.OK);

    }

@Override
    public ResponseEntity<List<Profile>> getTableLoad() {

        return new ResponseEntity<>(profileService.getTableLoad(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Profile>> getAllProfiles(@RequestBody Profile profileCreate) {

        return new ResponseEntity<>(profileService.getAllProfiles(profileCreate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateProfiles(@RequestBody Profile profileCreate) {

        return new ResponseEntity<>(profileService.updateProfiles(profileCreate), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> deleteProfiles(@RequestBody Profile profileCreate) {

        return new ResponseEntity<>(profileService.deleteProfiles(profileCreate), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Profile>> getSignatureProfile() {

        return new ResponseEntity<>(profileService.getSignatureProfile(), HttpStatus.OK);
    }

 @Override
    public ResponseEntity<List<Profile>> getCCList(@RequestBody Profile profileCreate) {

        return new ResponseEntity<>(profileService.getCCList(profileCreate), HttpStatus.OK);
    }
 
 @Override
 public ResponseEntity<Profile> getListByProfile(@RequestBody Profile profileCreate) {

     return new ResponseEntity<>(profileService.getListByProfile(profileCreate), HttpStatus.OK);
 }

 @Override
 public ResponseEntity<List<Profile>> getAllProfilesByBoD(@RequestBody User user) {

     return new ResponseEntity<>(profileService.getAllProfilesByBoD(user), HttpStatus.OK);
 }

}
