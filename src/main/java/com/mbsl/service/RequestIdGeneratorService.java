/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mbsl.service;

import com.mbsl.model.User;

/**
 *
 * @author MBSL2523
 */
public interface RequestIdGeneratorService {
    
 public String generateRequestId(User user);

}
