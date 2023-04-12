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
 * @author MBSL2523
 */
public interface HrUpdateDao {

    String createHrList(User approveList);

    public List<User> getList();

    String deleteList(User approveList);

public User getListHrById(User approveList);

public List<Profile> getBProfile();
    
}
