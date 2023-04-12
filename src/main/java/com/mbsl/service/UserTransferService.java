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
public interface UserTransferService {

    String transferUser(User transferUser);

    List<User> getTransferUsers();

    User getListTransferById(User transferUser);

    String updateTransferUser(User transferUser);

    String deleteTransferUser(User transferUser);

    List<User> getAllBranchOrDept(User user);

    User getToButtonListTransferById(User transferUser);

    User getAppInfoT(User transferUser);

}
