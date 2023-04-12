/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.Classes;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author MBSL2523
 */
public class requestGenerator {

//    private static final AtomicInteger count = new AtomicInteger(0);
    public String requestGeneratorr(String last, String path) {
        String code = "";

        if (path.equals("1")) {
            //Creation
            code = "#UAAF";
        } else if (path.equals("2")) {
            //Transfer
            code = "#UATR";
        } else if (path.equals("3")) {
            //inactivate
            code = "#UATM";
        } else if (path.equals("4")) {
            //Temp_Access
            code = "#";
        }

        System.out.println(last);
        AtomicInteger count = new AtomicInteger(Integer.valueOf(last));

        Boolean isFilled = false;
        int jobID = count.incrementAndGet();
        System.out.println("AA" + jobID + "     " + Integer.valueOf(last));
        return String.valueOf(code + jobID);
    }

}
