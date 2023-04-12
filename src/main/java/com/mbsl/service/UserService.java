/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.service;

import com.google.gson.JsonArray;
import com.mbsl.model.User;
import com.mbsl.model.StatusList;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author MBSL2395
 */
public interface UserService {

    String createUser(User user);

    List<User> getUsers();

    String updateUser(User user);

    String deleteUser(User user);

    User getListById(User user);

    List<User> getAllBranchOrDept(User user);

    User getAppInfoC(User user);
}
