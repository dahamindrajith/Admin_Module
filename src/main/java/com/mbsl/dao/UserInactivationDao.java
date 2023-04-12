/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.mbsl.model.User;
import java.util.List;

/**
 *
 * @author MBSL2523
 */
public interface UserInactivationDao {

    String userInact(User userInactivation);

    public List<User> getInactUsers();

    public User getListUserIactById(User userInactivation);

    String updateUserIact(User userInactivation);

    String deleteInactUser(User userInactivation);
    
    public User getToSelectButtonById(User userInactivation);

public User getAppInfoI(User userInactivation);

}
