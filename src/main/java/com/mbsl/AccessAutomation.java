/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl;

import com.mbsl.dao.impl.TempAccessDaoImpl;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author MBSL2523
 */
public class AccessAutomation {
    
    public void autoAccess() {
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dtff = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        
        String time = dtf.format(now);
        String date = dtff.format(now);
        
        if (time.equals("12:45")) {
            
            TempAccessDaoImpl obj = new TempAccessDaoImpl();
            String removeAcc = obj.removeTempAccess(date);
            System.out.println("TempResp");
            if (removeAcc.equals("1")) {



            }
            
        }
        
    }
    
}
