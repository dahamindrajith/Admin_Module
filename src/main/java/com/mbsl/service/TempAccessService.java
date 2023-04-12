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
public interface TempAccessService {

    String tempAccessGrant(User tempAccess);

    List<User> getAllTempAccess();

    User getListTempAccessById(User tempAccess);

    String updateTempAccess(User tempAccess);

    String deleteTempAccess(User tempAccess);

    List<User> getAllBranchOrDept(User user);

    User getToSelectButtonTempById(User tempAccess);

    String removeTempAccess(String epfNo);

    User getAppInfoTe(User tempAccess);

    String removeTempMnually(User tempAccess);

}
