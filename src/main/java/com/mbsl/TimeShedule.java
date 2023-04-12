/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author MBSL2523
 */
@Component
public class TimeShedule {
  
AccessAutomation obj=new AccessAutomation();

@Scheduled(fixedRate = 5000)
public void callTimeThread(){

    obj.autoAccess();

}

}
