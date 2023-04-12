/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.EnvChange;
import com.mbsl.DbConn;
import com.mbsl.Properties;
import com.mbsl.dao.ProfileDao;
import com.mbsl.model.Designation;
import com.mbsl.model.Profile;
import com.mbsl.model.User;
import com.mbsl.util.SpecialCharactorManager;
import com.mbsl.velocity.as400.environment.AS400Libraries;
import com.mbsl.velocity.dal.as400.AS400DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MBSL2491
 */
@Repository
public class ProfileDaoImpl implements ProfileDao {

    private static final Logger LOGGER = LogManager.getLogger(ProfileDaoImpl.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Autowired
    Properties properties;

    EnvChange obj = new EnvChange();
    String envr = obj.envirenment();

    @Override
    public String createProfile(Profile profileCreate) {
        System.out.println("2491");
        String vlu = "";

        String cCode = "";

        if (profileCreate.getClick().trim().equals("3")) {

            cCode = "Region";
        } else {

            String fullCode = profileCreate.getCC().trim();
            String[] parts = fullCode.split("-");
            cCode = parts[0];
        }

        String availability = checkAvailability(profileCreate.getProfileName(), cCode, profileCreate.getClick());
        String availabilityVeloGroup = checkVeloGroup(profileCreate.getVelGroup());

        try {
            if (availability.equals("0") && availabilityVeloGroup.equals("0")) {
//                String sql = "INSERT INTO admin_config_profile(profile,sig_profile,branch,cc,level,tel_type, velocity_group,status) VALUES (?,?,?,?,?,?,?,?)";
//                int result = jdbcTemplate.update(sql, profileCreate.getProfileName(), profileCreate.getSigProfile().trim(), profileCreate.getClick(), profileCreate.getCC().trim(), profileCreate.getLevel().trim(), profileCreate.getTillType().trim(), profileCreate.getVelGroup(), "A");

                StringBuffer query = new StringBuffer().append("SELECT SCUSER FROM I101MSMSP.SCP001 WHERE SCUSER='" + profileCreate.getSigProfile() + "'");

                try (ResultSet rs2 = AS400DBConnection.search(query.toString())) {
                    vlu = "0";
                    if (rs2.next()) {

                        String sigProfile = rs2.getString(1);

                        AS400Libraries libs = new AS400Libraries();
                        AS400DBConnection.setLibraries(libs.getLibraries(envr));

                        String queryAs400 = new StringBuffer("INSERT INTO MSVMAP00 (SVVGRP, SVSGRP, SVGLUS) values ('")
                                .append(SpecialCharactorManager.removeSingleQuote(profileCreate.getVelGroup())).append("','")
                                .append(SpecialCharactorManager.removeSingleQuote(sigProfile)).append("','")
                                .append(SpecialCharactorManager.removeSingleQuote("A")).append("'")
                                .append(")").toString();

                        if (AS400DBConnection.changeData(queryAs400) == 1) {
                            System.out.println("AS400 Done---------" + profileCreate.getClick().trim());

                            System.out.println("--------------------" + profileCreate.getCC());

                            String sql = "INSERT INTO admin_config_profile(profile,sig_profile,branch,cc,level,tel_type, velocity_group,status) VALUES (?,?,?,?,?,?,?,?)";
                            int result = jdbcTemplate.update(sql, profileCreate.getProfileName(), profileCreate.getSigProfile().trim(), profileCreate.getClick(), cCode, profileCreate.getLevel().trim(), profileCreate.getTillType().trim(), profileCreate.getVelGroup(), "A");

                            if (result > 0) {
                                System.out.println("A sql row has been inserted." + profileCreate.getClass());
                                vlu = "1";
                            }
                        } else {
                            System.out.println("AS400 Fail---------");
                        }

                    }
                } catch (Exception e) {
                    LOGGER.error("error : ", e);
                }

                LOGGER.info("sucess");
            }
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }
        if (vlu.equals("1")) {
            return messageSource.getMessage("profile.create.success", null, Locale.US);
        } else if (availability.equals("1")) {
            return messageSource.getMessage("profile.create.error", null, Locale.US);
        } else if (vlu.equals("0")) {
            return messageSource.getMessage("profile.create.error1", null, Locale.US);
        } else if (availabilityVeloGroup.equals("1")) {
            return messageSource.getMessage("profile.create.error3", null, Locale.US);
        } else {
            return messageSource.getMessage("profile.create.error", null, Locale.US);
        }

    }

    private String checkAvailability(String profName, String costCenter, String bod) {
        DbConn conn = new DbConn();
        String setType = "'" + profName + "'";
        String setType1 = "'" + costCenter.trim() + "'";
        String value = "0";
        System.out.println("bod--" + bod.trim());
        if (bod.trim().equals("2")) {
            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("SELECT profile FROM admin_config_profile  where status='A' and profile=" + setType);
                System.out.println("Qrydone ");
                if (rs.next()) {

                    value = "1";

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (bod.trim().equals("1")) {

            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("SELECT profile FROM admin_config_profile  where status='A' and profile=" + setType + " AND cc=" + setType1);
                System.out.println("Qrydone ");
                if (rs.next()) {

                    value = "1";

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }
        return value;

    }

    private String checkVeloGroup(String veloGroup) {
        DbConn conn = new DbConn();
        String setType = "'" + veloGroup + "'";
        String resp = "0";

        StringBuffer query = new StringBuffer().append("SELECT SVSGRP FROM MSVMAP00 WHERE SVVGRP=" + setType + " AND SVGLUS='A'");

        try (ResultSet rs2 = AS400DBConnection.search(query.toString())) {

            if (rs2.next()) {

                resp = "1";

            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        return resp;

    }

    @Override
    public List<Profile> getAllProfiles(Profile profileCreate) {

        DbConn conn = new DbConn();
        List<Profile> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        String setType = "'" + profileCreate.getClick().trim() + "'";

        if (profileCreate.getClick().trim().equals("1")) {
            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("select profile, sig_profile, branch, cc, level, tel_type FROM admin_config_profile where Status='A' and branch='2'");
                while (rs.next()) {

                    Profile profile = new Profile();
                    profile.setProfileName(rs.getString(1));

                    userList.add(profile);

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (profileCreate.getClick().trim().equals("3")) {

            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("SELECT profile FROM admin_config_profile WHERE branch='3' AND status='A' ");
                while (rs.next()) {

                    Profile profile = new Profile();
                    profile.setProfileName(rs.getString(1));

                    userList.add(profile);

                    System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + userList.get(0).getProfileName());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else {

            try {

                Connection connection = conn.getConn();
                ResultSet rs = connection.createStatement().executeQuery("SELECT admin_config_profile.profile FROM admin_config_profile INNER JOIN admin_dept ON admin_config_profile .cc=admin_dept.dept_cc where admin_config_profile.status='A' and admin_dept.dept_cc=" + setType);
                while (rs.next()) {

                    Profile profile = new Profile();
                    profile.setProfileName(rs.getString(1));

                    userList.add(profile);

                    System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + userList.get(0).getProfileName());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }
        return userList;
    }

    @Override
    public List<Profile> getTableLoad() {

        DbConn conn = new DbConn();
        List<Profile> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("select profile, sig_profile, branch, cc, level, tel_type FROM admin_config_profile where Status='A'");
            while (rs.next()) {

                String value = rs.getString(1);
                String value1 = rs.getString(2);
                String value2 = rs.getString(3);
                String value3 = rs.getString(4);
                String value4 = rs.getString(5);
                String value5 = rs.getString(6);

                if (value3.equals("")) {
                    value3 = "Branch";
                } else if (value3.equals(LOGGER)) {

                }

                Profile profile = new Profile();
                profile.setProfileName(rs.getString(1));
                profile.setCC(value3);

                userList.add(profile);

                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + userList.get(0).getProfileName());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return userList;
    }

    @Override
    public String updateProfiles(Profile profileCreate) {
        System.out.println(profileCreate.getProfileName() + "Done");
        System.out.println("Ok");

//        String availability = checkAvailability(profileCreate.getProfileName(), profileCreate.getCC(), profileCreate.getClick());
        String cc = profileCreate.getCC().trim();
        String bod = profileCreate.getClick().trim();
        System.out.println("bod--------" + bod);
        if (bod.equals("2")) {
            try {
                String sql = "update admin_config_profile set profile=? , sig_profile=? , branch=? , cc=?, level=?, tel_type=? where profile=? and status=?";

                int result = jdbcTemplate.update(sql, profileCreate.getProfileName(), profileCreate.getSigProfile().trim(), profileCreate.getClick(), profileCreate.getCC().trim(), profileCreate.getLevel().trim(), profileCreate.getTillType().trim(), profileCreate.getProfileName().trim(), "A");

                if (result > 0) {
                    System.out.println("A sql row has been inserted.");
                }

                System.out.println(result);
                LOGGER.info("sucess");

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (bod.equals("1")) {

            try {
                String sql = "update admin_config_profile set profile=? , sig_profile=? , branch=? , cc=?, level=?, tel_type=? where profile=? and cc=? and status=?";

                int result = jdbcTemplate.update(sql, profileCreate.getProfileName(), profileCreate.getSigProfile().trim(), profileCreate.getClick(), profileCreate.getCC().trim(), profileCreate.getLevel().trim(), profileCreate.getTillType().trim(), profileCreate.getProfileName().trim(), cc.trim(), "A");

                if (result > 0) {
                    System.out.println("A sql row has been inserted.");
                }

                System.out.println(result);
                LOGGER.info("sucess");

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }

        return messageSource.getMessage("profile.updated.success", null, Locale.US);
    }

    @Override
    public String deleteProfiles(Profile profileCreate) {
        DbConn conn = new DbConn();
        String velGroup = "";
        System.out.println("22222222222222222222222222222222" + profileCreate.getProfileName());

        if (profileCreate.getCC().equals("Branch")) {
            try {

                Connection connection = dataSource.getConnection();
                String sql = "SELECT velocity_group from admin_config_profile where profile=? AND status='A' AND cc=''";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, profileCreate.getProfileName());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    velGroup = rs.getString(1);

                    String sql2 = "update admin_config_profile set status=? where profile=? and status=? and cc=?";

                    int result2 = jdbcTemplate.update(sql2, "I", profileCreate.getProfileName(), "A","");
                    System.out.println("-------------------" + result2 + velGroup);
                    if (result2 > 0) {
                        int st2 = 0;

                        String queryAs400Add = new StringBuffer("UPDATE MSVMAP00 SET SVGLUS='").append("I")
                                .append("' WHERE SVVGRP='").append(velGroup)
                                .append("' AND SVGLUS='").append("A")
                                .append("'").toString();

                        st2 = AS400DBConnection.changeData(queryAs400Add);
                        System.out.println("A new row has been inserted.");
                    }
                }

                LOGGER.info("sucess");
            } catch (Exception e) {
                LOGGER.error("error : ", e);
            }
        } else if (profileCreate.getCC().equals("Region")) {

            try {

                Connection connection = dataSource.getConnection();
                String sql = "SELECT velocity_group from admin_config_profile where profile=? AND status='A' AND cc='Region'";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, profileCreate.getProfileName());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    velGroup = rs.getString(1);

                    String sql2 = "update admin_config_profile set status=? where profile=? and status=? and cc=?";

                    int result2 = jdbcTemplate.update(sql2, "I", profileCreate.getProfileName(), "A","Region");
                    System.out.println("-------------------" + result2 + velGroup);
                    if (result2 > 0) {
                        int st2 = 0;

                        String queryAs400Add = new StringBuffer("UPDATE MSVMAP00 SET SVGLUS='").append("I")
                                .append("' WHERE SVVGRP='").append(velGroup)
                                .append("' AND SVGLUS='").append("A")
                                .append("'").toString();

                        st2 = AS400DBConnection.changeData(queryAs400Add);
                        System.out.println("A new row has been inserted.");
                    }
                }

                LOGGER.info("sucess");
            } catch (Exception e) {
                LOGGER.error("error : ", e);
            }
        } else {

            try {
                System.out.println("--------------" + profileCreate.getCC());
                Connection connection = dataSource.getConnection();
                String sql = "SELECT velocity_group from admin_config_profile where profile=? AND status='A' AND cc=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, profileCreate.getProfileName());
                ps.setString(2, profileCreate.getCC());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    velGroup = rs.getString(1);
                    String sql2 = "update admin_config_profile set status=? where profile=? and status=? and cc=?";

                    int result2 = jdbcTemplate.update(sql2, "I", profileCreate.getProfileName(), "A", profileCreate.getCC());
                    System.out.println("-------------------" + result2 + velGroup);
                    if (result2 > 0) {
                        int st2 = 0;

                        String queryAs400Add = new StringBuffer("UPDATE MSVMAP00 SET SVGLUS=").append("I")
                                .append("' WHERE SVVGRP='").append(velGroup)
                                .append("' AND SVGLUS='").append("A")
                                .append("'").toString();

                        st2 = AS400DBConnection.changeData(queryAs400Add);
                        System.out.println("A new row has been inserted.");
                    }
                }
                LOGGER.info("sucess");
            } catch (Exception e) {
                LOGGER.error("error : ", e);
            }

        }
        return messageSource.getMessage("profile.delete.success", null, Locale.US);
    }

    @Override
    public List<Profile> getSignatureProfile() {

        List<Profile> sigList = new ArrayList();
        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));

        StringBuffer query = new StringBuffer().append("select SCUSER from SCP001 where SCSTAT!='I'");

        try (ResultSet rs = AS400DBConnection.search(query.toString())) {

            while (rs.next()) {
                Profile sList = new Profile();

                sList.setSigProfile(rs.getString(1).trim());
                sigList.add(sList);

            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);

        }
        System.out.println("@return--" + sigList.get(0));
        return sigList;
    }

    @Override
    public List<Profile> getCCList(Profile profileCreate) {

        System.out.println("-------------" + profileCreate.getCC());
        List<Profile> ccList = new ArrayList();
        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));
        String sql = " ";
        if (profileCreate.getCC().trim().equals("1")) {
            sql = "AND CFCNTR BETWEEN 1000 AND 3000 AND CFCNTR NOT IN(1022,1023,1024,1025,1026,1027)";
        } else if (profileCreate.getCC().trim().equals("2")) {
            sql = "AND CFCNTR BETWEEN 100 AND 900";
        } else if (profileCreate.getCC().trim().equals("3")) {
            sql = "AND CFCNTR IN (1022,1023,1024,1025,1026,1027)";
        }
        System.out.println("sql------" + sql);
        StringBuffer query = new StringBuffer().append("select CFCNTR, CFTNME from CFP420 where CFSTAT!='C'" + sql + " ORDER BY CFCNTR ASC");

        try (ResultSet rs = AS400DBConnection.search(query.toString())) {

            while (rs.next()) {
                Profile list = new Profile();

                String ccenter = rs.getString(1);
                String bod = rs.getString(2);

                list.setCC(ccenter + "-" + "     " + bod);

                ccList.add(list);

            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);

        }
        System.out.println("@return--" + ccList.get(0));
        return ccList;
    }

    @Override
    public Profile getListByProfile(Profile profileCreate) {
        List<Profile> userList = new ArrayList();
        Profile profil = new Profile();
        String accessModel;
        JsonArray ja = new JsonArray();

        if (profileCreate.getCC().equals("Branch")) {
            try {
                Connection connection = dataSource.getConnection();
                String sql = "SELECT profile, sig_profile, branch, cc, level, tel_type, velocity_group from admin_config_profile where profile=? and status='A'";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, profileCreate.getProfileName());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    profil.setProfileName(rs.getString(1));
                    profil.setSigProfile(rs.getString(2));
                    profil.setClick(rs.getString(3));
                    profil.setCC(rs.getString(4));
                    profil.setLevel(rs.getString(5));
                    profil.setTillTypel(rs.getString(6));
                    profil.setVelGroup(rs.getString(7));

                    userList.add(profil);

                    System.out.println("branch" + userList.get(0).getProfileName());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else {

            try {
                Connection connection = dataSource.getConnection();
                String sql = "SELECT profile, sig_profile, branch, cc, level, tel_type, velocity_group from admin_config_profile where profile=? and cc=? and status='A'";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, profileCreate.getProfileName());
                ps.setString(2, profileCreate.getCC());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    profil.setProfileName(rs.getString(1));
                    profil.setSigProfile(rs.getString(2));
                    profil.setClick(rs.getString(3));
                    profil.setCC(rs.getString(4));
                    profil.setLevel(rs.getString(5));
                    profil.setTillTypel(rs.getString(6));
                    profil.setVelGroup(rs.getString(7));

                    userList.add(profil);

                    System.out.println("bbbbbbbbbbb" + userList.get(0).getProfileName());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }
        return profil;
    }

    @Override
    public List<Profile> getAllProfilesByBoD(User user) {
        List<Profile> userList = new ArrayList();

        String accessModel;
        JsonArray ja = new JsonArray();
        String value = "";

        if (user.getClick().trim().equals("Department")) {
            value = "1";
        } else if (user.getClick().trim().equals("Branch")) {
            value = "2";
        } else {
            value = "3";
        }

        System.out.println("output------" + user.getClick() + "-------" + user.getBranchorDept());
        System.out.println("value-------" + value);
        if (value == "1") {

            try {
                Connection connection = dataSource.getConnection();
//                String sql = "SELECT admin_branch.br_code, admin_config_profile.profile, admin_config_profile.sig_profile, admin_config_profile.branch, admin_config_profile.cc, admin_config_profile.level, admin_config_profile.tel_type FROM admin_config_profile INNER JOIN admin_branch ON admin_config_profile .cc=admin_branch.br_code where admin_config_profile.status='A' and admin_config_profile.branch=? and admin_branch.br_name=?";
                String sql = "SELECT admin_dept.dept_cc, admin_config_profile.profile, admin_config_profile.sig_profile, admin_config_profile.branch, admin_config_profile.cc, admin_config_profile.level, admin_config_profile.tel_type FROM admin_config_profile INNER JOIN admin_dept ON admin_config_profile .cc=admin_dept.dept_cc where admin_config_profile.status='A' and admin_config_profile.branch=? and admin_dept.dept_name=? ORDER BY admin_config_profile.profile";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, value);
                ps.setString(2, user.getBranchorDept().trim());
                ResultSet rs = ps.executeQuery();
                System.out.println("Sql--" + sql);
                System.out.println("results--" + rs);
                while (rs.next()) {

                    Profile profil = new Profile();
                    profil.setCC(rs.getString(1));
                    profil.setProfileName(rs.getString(2));
                    profil.setSigProfile(rs.getString(3));
                    profil.setClick(rs.getString(4));
                    profil.setLevel(rs.getString(5));
                    profil.setTillTypel(rs.getString(6));

                    userList.add(profil);

                    System.out.println("bbbbbbbbbbb" + userList.get(0).getProfileName());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (value == "2") {

            try {
                Connection connection = dataSource.getConnection();
                String sql = "SELECT profile, sig_profile, branch, cc, level, tel_type from admin_config_profile where status='A' and branch='2' ORDER BY profile";
                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.setString(1, bod);
//                ps.setString(1, bodName);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Profile profil = new Profile();
                    profil.setProfileName(rs.getString(1));
                    profil.setSigProfile(rs.getString(2));
                    profil.setClick(rs.getString(3));
                    profil.setCC(rs.getString(4));
                    profil.setLevel(rs.getString(5));
                    profil.setTillTypel(rs.getString(6));

                    userList.add(profil);

                    System.out.println("bbbbbbbbbbb" + userList.get(0).getProfileName());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else {

            try {
                Connection connection = dataSource.getConnection();
                String sql = "SELECT profile from admin_region_office_access";
                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.setString(1, bod);
//                ps.setString(1, bodName);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    Profile profil = new Profile();
                    profil.setProfileName(rs.getString(1));

                    userList.add(profil);

                    System.out.println("bbbbbbbbbbb" + userList.get(0).getProfileName());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }
        return userList;
    }

//@Override
//    public String deleteDesignation(Designation createDesignation, String designation) {
//        System.out.println("22222222222222222222222222222222" + designation);
//
//        try {
//            String sql2 = "update admin_config_desig set Status=? where designation=?";
//
//            int result2 = jdbcTemplate.update(sql2, "I", designation);
//
//            if (result2 > 0) {
//                System.out.println("A new row has been inserted.");
//            }
//
//            LOGGER.info("sucess");
//        } catch (Exception e) {
//            LOGGER.error("error : ", e);
//        }
//
//        return messageSource.getMessage("user.create.success", null, Locale.US);
//    }
}
