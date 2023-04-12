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
public interface EmailCreateService {

    List<User> getEmailToCreate();

    User getDataByRqstId(User mailCreate);

String cofrmSubmition(User mailCreate);

}
