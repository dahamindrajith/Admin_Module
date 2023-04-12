/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author MBSL2523
 */
public class liveTimeAndDate {

    public String liveTimeDate() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now).toString());

        String td = dtf.format(now).toString();

        return td;
    }

}
