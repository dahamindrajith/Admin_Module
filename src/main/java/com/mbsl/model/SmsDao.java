/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.model;

/**
 *
 * @author MBSL2523
 */
public class SmsDao {

    private String mobileNo;
    private String message;

    public SmsDao(String mobileNo, String message) {
        this.mobileNo = mobileNo;
        this.message = message;
    }

    public SmsDao() {
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
