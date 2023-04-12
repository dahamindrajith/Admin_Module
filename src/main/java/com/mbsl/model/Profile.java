/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.model;

/**
 *
 * @author MBSL2491
 */
public class Profile {

    private String profileName;

    public Profile(String profileName) {
        this.profileName = profileName;
    }

    public Profile() {
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    private String sigProfile;

    /**
     * @return the sigProfile
     */
    public String getSigProfile() {
        return sigProfile;
    }

    /**
     * @param sigProfile the sigProfile to set
     */

    public void setSigProfile(String sigProfile) {
        this.sigProfile = sigProfile;
    }

    private String cc;

    /**
     * @return the cc
     */
    public String getCC() {
        return cc;
    }

    /**
     * @param cc the cc to set
     */

    public void setCC(String cc) {
        this.cc = cc;
    }

    private String level;

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */

    public void setLevel(String level) {
        this.level = level;
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

    public void setTillTypel(String tillType) {
        this.tillType = tillType;
    }

    private String status;

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */

    public void setStatus(String status) {
        this.status = status;
    }

    private String click;

    /**
     * @return the click
     */
    public String getClick() {
        return click;
    }

    /**
     * @param click the click to set
     */

    public void setClick(String click) {
        this.click = click;
    }

    private String velGroup;

    /**
     * @return the velGroup
     */
    public String getVelGroup() {
        return velGroup;
    }

    /**
     * @param velGroup the velGroup to set
     */

    public void setVelGroup(String velGroup) {
        this.velGroup = velGroup;
    }

}
