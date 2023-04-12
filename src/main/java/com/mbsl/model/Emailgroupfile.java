/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.model;

/**
 *
 * @author MBSL2523
 */
public class Emailgroupfile implements java.io.Serializable{

private Integer id;
     private String bankCode;
     private String emaigroupid;
     private String emailgroup;
     private Integer active;

    public Emailgroupfile() {
    }

    public Emailgroupfile(String bankCode, String emaigroupid, String emailgroup, Integer active) {
       this.bankCode = bankCode;
       this.emaigroupid = emaigroupid;
       this.emailgroup = emailgroup;
       this.active = active;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getBankCode() {
        return this.bankCode;
    }
    
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getEmaigroupid() {
        return this.emaigroupid;
    }
    
    public void setEmaigroupid(String emaigroupid) {
        this.emaigroupid = emaigroupid;
    }
    public String getEmailgroup() {
        return this.emailgroup;
    }
    
    public void setEmailgroup(String emailgroup) {
        this.emailgroup = emailgroup;
    }
    public Integer getActive() {
        return this.active;
    }
    
    public void setActive(Integer active) {
        this.active = active;
    }
    
}
