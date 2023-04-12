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
public interface EmailCreateDao {

public List<User> getEmailToCreate();

public User getDataByRqstId(User mailCreate);

public String cofrmSubmition(User mailCreate);
    
}
