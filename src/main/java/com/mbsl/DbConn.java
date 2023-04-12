/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author MBSL2523
 */
public class DbConn {
    
public Connection getConn(){
  Connection con=null;
        try {
            String dbPath="jdbc:mysql://10.1.1.73:3306/velocity20?autoReconnect=true&sessionVariables=interactive_timeout=2147483,wait_timeout=2147483";
            String userName="appuser";
            String password="My5Q1@MBSL_2021P@ss";
            Class.forName("com.mysql.cj.jdbc.Driver");  
            con=DriverManager.getConnection(  
            dbPath,userName,password);  
        } catch (Exception e) {
            e.printStackTrace();
        }
    return con;
    }

}
