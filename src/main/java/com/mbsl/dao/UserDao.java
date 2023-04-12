/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.dao;

import com.google.gson.JsonArray;
import com.mbsl.model.User;
import com.mbsl.model.StatusList;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author MBSL2395
 */
public interface UserDao {

    String createUser(User user);

    public List<User> getUsers();

    String updateUser(User user);

    String deleteUser(User user);

    public User getListById(User user);

    public List<User> getAllBranchOrDept(User user);

    public User getAppInfoC(User user);

}
