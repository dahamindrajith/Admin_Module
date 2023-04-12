/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.service.impl;

import com.mbsl.dao.ProfileDao;
import com.mbsl.model.Profile;
import com.mbsl.model.User;
import com.mbsl.service.ProfileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MBSL2491
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao profileDao;

    @Override
    public String createProfile(Profile profileCreate) {

        return profileDao.createProfile(profileCreate);
    }

    @Override
    public List<Profile> getTableLoad() {
        return profileDao.getTableLoad();
    }

    @Override
    public List<Profile> getAllProfiles(Profile profileCreate) {
        return profileDao.getAllProfiles(profileCreate);
    }

    @Override
    public String updateProfiles(Profile profileCreate) {
        return profileDao.updateProfiles(profileCreate);
    }

    @Override
    public String deleteProfiles(Profile profileCreate) {
        return profileDao.deleteProfiles(profileCreate);
    }

    @Override
    public List<Profile> getSignatureProfile() {
        return profileDao.getSignatureProfile();
    }

    @Override
    public List<Profile> getCCList(Profile profileCreate) {
        return profileDao.getCCList(profileCreate);
    }

    @Override
    public Profile getListByProfile(Profile profileCreate) {
        return profileDao.getListByProfile(profileCreate);
    }

    @Override
    public List<Profile> getAllProfilesByBoD(User user) {
        return profileDao.getAllProfilesByBoD(user);
    }

}
