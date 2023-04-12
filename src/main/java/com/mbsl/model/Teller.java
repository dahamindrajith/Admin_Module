/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.model;

/**
 *
 * @author MBSL2491
 */
public class Teller {

private String branch;

    public Teller(String branch) {
        this.branch = branch;
    }

    public Teller() {
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }


private String till;

   /**
     * @return the till
     */

    public String getTill() {
        return till;
    }
 /**
     * @param till the till to set
     */

    public void setTill(String till) {
        this.till = till;
    }

private String tillType;

   /**
     * @return the tillType
     */

    public String getTillType() {
        return tillType;
    }
 /**
     * @param tillType the tillType to set
     */

    public void setTillType(String tillType) {
        this.tillType = tillType;
    }

    
}
