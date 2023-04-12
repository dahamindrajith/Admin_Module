/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MBSL2523
 */
public class StatusList {

    private List<String> userCreation = new ArrayList<String>();
    private List<String> userTransfer = new ArrayList<String>();
    private List<String> userInactivation = new ArrayList<String>();
    private List<String> tempAccess = new ArrayList<String>();
    private List<String> regionList = new ArrayList<String>();
    private String roleCheck;
    private String regOrNot;
    private String hod;
    private List<String> pendingList = new ArrayList<String>();

    public StatusList() {
    }

    public List<String> getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(List<String> userCreation) {
        this.userCreation = userCreation;
    }

    public List<String> getUserTransfer() {
        return userTransfer;
    }

    public void setUserTransfer(List<String> userTransfer) {
        this.userTransfer = userTransfer;
    }

    public List<String> getUserInactivation() {
        return userInactivation;
    }

    public void setUserInactivation(List<String> userInactivation) {
        this.userInactivation = userInactivation;
    }

    public List<String> getTempAccess() {
        return tempAccess;
    }

    public void setTempAccess(List<String> tempAccess) {
        this.tempAccess = tempAccess;
    }

    public String getRoleCheck() {
        return roleCheck;
    }

    public void setRoleCheck(String roleCheck) {
        this.roleCheck = roleCheck;
    }

    public List<String> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<String> regionList) {
        this.regionList = regionList;
    }

    public String getRegOrNot() {
        return regOrNot;
    }

    public void setRegOrNot(String regOrNot) {
        this.regOrNot = regOrNot;
    }

    public String getHod() {
        return hod;
    }

    public void setHod(String hod) {
        this.hod = hod;
    }

    public List<String> getPendingList() {

        return pendingList;
    }

    public void setPendingList(List<String> pendingList) {

        this.pendingList = pendingList;
    }

}
