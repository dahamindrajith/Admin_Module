/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.service;

import com.mbsl.model.Profile;
import com.mbsl.model.User;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2491
 */
@Service
public interface ProfileService {

    String createProfile(Profile profileCreate);

    List<Profile> getTableLoad();

    List<Profile> getAllProfiles(Profile profileCreate);

    String updateProfiles(Profile profileCreate);

    String deleteProfiles(Profile profileCreate);

    List<Profile> getSignatureProfile();

    List<Profile> getCCList(Profile profileCreate);

    Profile getListByProfile(Profile profileCreate);

    List<Profile> getAllProfilesByBoD(User user);

}
