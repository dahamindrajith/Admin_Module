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
public interface UserTransferDao {

    String transferUser(User transferUser);

    public List<User> getTransferUsers();

    public User getListTransferById(User transferUser);

    String updateTransferUser(User transferUser);

    String deleteTransferUser(User transferUser);

    public List<User> getAllBranchOrDept(User user);

    public User getToButtonListTransferById(User transferUser);

    public User getAppInfoT(User transferUser);
}
