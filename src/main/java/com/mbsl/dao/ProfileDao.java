/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.mbsl.model.Profile;
import com.mbsl.model.User;

import java.util.List;

/**
 *
 * @author MBSL2491
 */
public interface ProfileDao {

    String createProfile(Profile profileCreate);

    public List<Profile> getTableLoad();

    public List<Profile> getAllProfiles(Profile profileCreate);

    String updateProfiles(Profile profileCreate);

    String deleteProfiles(Profile profileCreate);

    public List<Profile> getSignatureProfile();

    public List<Profile> getCCList(Profile profileCreate);

    public Profile getListByProfile(Profile profileCreate);

    public List<Profile> getAllProfilesByBoD(User user);

}
