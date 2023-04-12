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
public interface TempAccessDao {


String tempAccessGrant(User tempAccess);

public List<User> getAllTempAccess();

public User getListTempAccessById(User tempAccess);

String updateTempAccess(User tempAccess);

String deleteTempAccess(User tempAccess);

public List<User> getAllBranchOrDept(User user);

public User getToSelectButtonTempById(User tempAccess);

String removeTempAccess(String epfNo);

public User getAppInfoTe(User tempAccess);

String removeTempMnually(User tempAccess);
    
}
