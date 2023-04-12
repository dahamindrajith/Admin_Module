/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.service;

import com.mbsl.model.User;
import java.util.List;

/**
 *
 * @author MBSL2523
 */
public interface UserInactivationService {

    String userInact(User userInactivation);

    List<User> getInactUsers();

    User getListUserIactById(User userInactivation);

    String updateUserIact(User userInactivation);

    String deleteInactUser(User userInactivation);

    User getToSelectButtonById(User userInactivation);

    User getAppInfoI(User userInactivation);

}
