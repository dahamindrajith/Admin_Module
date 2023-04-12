/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.mbsl.Classes.passwordGenerator;
import com.mbsl.dao.PasswordGeneratorDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MBSL2523
 */
@Repository
public class PasswordGeneratorDaoImpl implements PasswordGeneratorDao {

    @Override
    public String generatePassword() {

passwordGenerator obj=new passwordGenerator();
        System.out.println("Done");
return obj.generatePassword();
    }

}
