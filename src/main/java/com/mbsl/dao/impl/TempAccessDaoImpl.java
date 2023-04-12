/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.AS400Programme;
import com.mbsl.Classes.ConfigEmail;
import com.mbsl.Classes.EnvChange;
import com.mbsl.Classes.GetSignatureUSers;
import com.mbsl.Classes.RequestIdGenerator;
import com.mbsl.Classes.hrSpecialApprove;
import com.mbsl.DbConn;
import com.mbsl.Properties;
import com.mbsl.dao.TempAccessDao;
import com.mbsl.model.User;
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
 * @author MBSL2523
 */
@Repository
public class TempAccessDaoImpl implements TempAccessDao {

    private static final Logger LOGGER = LogManager.getLogger(TempAccessDaoImpl.class);

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
    public String tempAccessGrant(User tempAccess) {
        System.out.println(tempAccess.getEpfNo() + "Done");
        System.out.println(tempAccess.getEndDate() + "Done");
        DbConn conn = new DbConn();
        List<User> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        String availabilityHr = checkAvailabilityHr(tempAccess.getEpfNo(), tempAccess.getNewProfile());
        String bod = "";

        if (tempAccess.getClick().trim().equals("Department")) {
            bod = "4050";
        } else if (tempAccess.getClick().trim().equals("Branch")) {
            bod = "4150";
        } else if (tempAccess.getClick().trim().equals("Region")) {
            bod = "4450";
        }

        String availability = checkAvailability(tempAccess.getEpfNo());

        try {
            if (availability.equals("2")) {

                RequestIdGenerator objRqst = new RequestIdGenerator();
                String RqstID = objRqst.generateRequestId("4");
                System.out.println("Date check--------------------------" + tempAccess.getEndDate().substring(0, 10) + "         " + tempAccess.getEndDate());
                Connection connection = conn.getConn();

                String sql = "INSERT INTO admin_temp_access(rqst_id, user_id, new_br, new_profile, new_desig, new_till, date_from, date_to, branch_or_dept, req_type, progress_stat, progress_code, expire) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                int result = jdbcTemplate.update(sql, RqstID, tempAccess.getEpfNo(), tempAccess.getNewBranchOrDept().trim(), tempAccess.getNewProfile().trim(), tempAccess.getNewDesignation().trim(), tempAccess.getNewTill().trim(), tempAccess.getStartDate().substring(0, 10), tempAccess.getEndDate().substring(0, 10), tempAccess.getClick().trim(), "Temperory Access", "Pending", bod, "Y");

                if (result > 0) {
                    System.out.println("A sql row has been inserted." + tempAccess);

                    if (availabilityHr.equals("1")) {

                        ResultSet rs = connection.createStatement().executeQuery("SELECT new_user_name FROM admin_user_creation_det  WHERE status='A' AND user_id='" + tempAccess.getEpfNo() + "'");

                        if (rs.next()) {
                            System.out.println("inside");
                            String fullName = rs.getString(1);

                            ResultSet rs1 = connection.createStatement().executeQuery("SELECT new_br FROM admin_main_table  WHERE status='A' AND progres_stus='Approved' AND user_id='" + tempAccess.getEpfNo() + "'");

                            if (rs1.next()) {

                                String oldBranch = rs1.getString(1);

                                hrSpecialApprove obj = new hrSpecialApprove();
                                obj.hrApprovel(tempAccess.getClick().trim().trim(), tempAccess.getNewBranchOrDept().trim(), tempAccess.getNewProfile().trim(), tempAccess.getEpfNo().trim(), fullName, "User Transfer", tempAccess.getRqstId(), oldBranch, tempAccess.getNewDesignation().trim(), tempAccess.getNewTill().trim());

                                ConfigEmail obj1 = new ConfigEmail();
                                String cmnMail = obj1.comonMail(RqstID, "Temporary Access", "Pending", tempAccess.getClick().trim(), tempAccess.getNewBranchOrDept().trim());
                            }
                        }
                    }

                    ConfigEmail obj1 = new ConfigEmail();
                    String cmnMail = obj1.comonMail(RqstID, "Temporary Access", "Pending", tempAccess.getClick().trim(), tempAccess.getNewBranchOrDept().trim());

                }

                LOGGER.info("sucess");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        if (availability.equals("1")) {
            return messageSource.getMessage("user.tempaccess.alreadyrquested", null, Locale.US);
        } else if (availability.equals("2")) {
            return messageSource.getMessage("user.tempaccess.success", null, Locale.US);
        } else {
            return messageSource.getMessage("user.tempaccess.notavailable", null, Locale.US);
        }

    }

    private String checkAvailability(String epf) {
        DbConn conn = new DbConn();
        String setType = "'" + epf + "'";
        String value = "0";
        int idCount = 0;

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT user_id FROM admin_main_table  where progres_stus_code IN ('401','901','1301') AND user_id=" + setType);
            System.out.println("Qrydone ");
            if (rs.next()) {

                ResultSet rs2 = connection.createStatement().executeQuery("SELECT user_id FROM admin_temp_access where user_id=" + setType);
                ResultSet rs1 = connection.createStatement().executeQuery("SELECT user_id FROM admin_temp_access where progress_code IN ('25','4100','4400','4600') AND user_id=" + setType);
                ResultSet rs3 = connection.createStatement().executeQuery("SELECT COUNT(user_id) AS c FROM admin_temp_access  WHERE progress_code IN ('4050','4150','4450') AND user_id=" + setType);
                if (rs3.next()) {
                    idCount = rs3.getInt(1);
                }

                if (rs1.next()) {

                    if (idCount > 0) {
                        value = "1";
                    } else {
                        value = "2";
                    }

                } else if (!rs2.next()) {
                    value = "2";
                } else {
                    value = "1";
                }

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        return value;

    }

    private String checkAvailabilityHr(String epf, String profile) {
        DbConn conn = new DbConn();
        String setType = "'" + epf + "'";
        String value = "0";

        try {
            Connection connection = conn.getConn();
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT prev_prof, prev_br_dep FROM admin_main_table WHERE status='A' AND user_id=" + setType + " AND progres_stus_code!=25 AND progres_stus='Approved'");
            if (rs1.next()) {

                String oldProfile = rs1.getString(1);
                String branchOrDept = rs1.getString(2);

                ResultSet rs = connection.createStatement().executeQuery("SELECT user_id FROM admin_hr_approved_table WHERE status='A' AND user_id=" + setType + " AND approved_profile='" + profile.trim() + "' AND profile='" + oldProfile + "' AND branch_or_dept='" + branchOrDept + "'");
                System.out.println("Qrydone " + rs + "         " + setType + "            " + profile.trim());
                if (rs.next()) {

                    value = "1";
                }
            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        System.out.println("value trans---" + value);
        return value;

    }

    @Override
    public List<User> getAllTempAccess() {
        DbConn conn = new DbConn();
        List<User> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();

        System.out.println("Get Temp Done");
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT admin_temp_access.rqst_id, admin_temp_access.user_id, admin_temp_access.new_br, admin_temp_access.new_profile, admin_temp_access.new_desig, admin_temp_access.date_from, admin_temp_access.date_to, admin_temp_access.progress_stat, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_temp_access.new_till FROM admin_temp_access INNER JOIN admin_main_table ON admin_temp_access.user_id=admin_main_table.user_id where admin_temp_access.progress_code!=25 AND admin_temp_access.expire='Y'");
            while (rs.next()) {

                User user = new User();
                user.setRqstId(rs.getString(1));
                user.setEpfNo(rs.getString(2));
                user.setNewBranchOrDept(rs.getString(3));
                user.setNewProfile(rs.getString(4));
                user.setNewDesignation(rs.getString(5));
                user.setStartDate(rs.getString(6));
                user.setEndDate(rs.getString(7));
                user.setStatus(rs.getString(8));
                user.setBranchorDept(rs.getString(9));
                user.setProfile(rs.getString(10));
                user.setDesignation(rs.getString(11));
                user.setCurrentTill(rs.getString(12));
                user.setNewTill(rs.getString(13));

                userList.add(user);

                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + userList.get(0).getEpfNo());

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
    public User getListTempAccessById(User tempAccess) {
        System.out.println("getListTempAccessById Done");
        List<User> userList = new ArrayList();
        User user = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT admin_temp_access.rqst_id, admin_temp_access.user_id, admin_temp_access.new_br, admin_temp_access.new_profile, admin_temp_access.new_desig, admin_temp_access.new_till, admin_temp_access.date_from, admin_temp_access.date_to, admin_temp_access.progress_stat, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till FROM admin_main_table INNER JOIN admin_temp_access ON admin_main_table.user_id=admin_temp_access.user_id where admin_temp_access.user_id=? and admin_temp_access.progress_code!=25";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tempAccess.getEpfNo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user.setRqstId(rs.getString(1));
                user.setEpfNo(rs.getString(2));
                user.setNewBranchOrDept(rs.getString(3));
                user.setNewProfile(rs.getString(4));
                user.setNewDesignation(rs.getString(5));
                user.setNewTill(rs.getString(6));
                user.setStartDate(rs.getString(7));
                user.setEndDate(rs.getString(8));
                user.setStatus(rs.getString(9));
                user.setBranchorDept(rs.getString(10));
                user.setProfile(rs.getString(11));
                user.setDesignation(rs.getString(12));
                user.setCurrentTill(rs.getString(13));

                userList.add(user);

                System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());
                System.out.println("bbbbbbbbbbb" + userList.get(0).getBranchorDept());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return user;
    }

    @Override
    public String removeTempAccess(String date) {
        String rtnValue = "";
        System.out.println("------Done" + date);
        String accessList = "";
        String email = "";
//        String setType = "'" + epfNo + "'";

        try {
            DbConn conn = new DbConn();
//            ResultSet rs = connection.createStatement().executeQuery("SELECT branch_or_dept, rqst_id, prev_prof  FROM admin_main_table WHERE user_id=" + setType);
            String sqlSelect = "SELECT admin_main_table.prev_till ,admin_main_table.prev_desig ,admin_main_table.prev_br_dep ,admin_main_table.branch_or_dept,  admin_main_table.rqst_id,  admin_main_table.prev_prof,  admin_main_table.user_id, admin_main_table.new_till, admin_main_table.rqst_id, admin_main_table.new_br FROM admin_main_table INNER JOIN admin_temp_access ON admin_temp_access.user_id=admin_main_table.user_id WHERE admin_temp_access.expire='Y' AND admin_temp_access.date_to=? AND admin_main_table.progres_stus_code!=25 AND admin_temp_access.progress_code!=25";
            Connection connection = conn.getConn();
            PreparedStatement ps = connection.prepareStatement(sqlSelect);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String till = rs.getString(1);
                String desig = rs.getString(2);
                String branchOrdept = rs.getString(3);
                String bod = rs.getString(4);
                String rqstId = rs.getString(5);
                String prof = rs.getString(6);
                String epf = rs.getString(7);
                String newTill = rs.getString(8);
                String addNewRqstId = rs.getString(9);
                String newBranchODept = rs.getString(10);
                System.out.println("Done in side" + bod);

                String ubod = "";
                if (branchOrdept.contains("BRANCH")) {

                    bod = "Branch";
                } else if (branchOrdept.contains("REGION")) {

                    bod = "Region";
                } else {

                    bod = "Department";
                }

                if (bod.equals("Branch")) {

                    System.out.println("New bod------------" + bod);

                    ResultSet rs1 = connection.createStatement().executeQuery("SELECT access_code FROM admin_branch_ass_acc WHERE profile='" + prof + "'");
                    if (rs1.next()) {
                        accessList = rs1.getString(1);
                        System.out.println("Access List------------" + accessList);

                    }

                } else if (bod.equals("Department")) {

                    ResultSet rs1 = connection.createStatement().executeQuery("SELECT admin_department_ass_acc.access_code FROM admin_department_ass_acc INNER JOIN admin_dept ON admin_dept.dept_cc=admin_department_ass_acc.cc WHERE admin_department_ass_acc.profile='" + prof + "' AND admin_dept.dept_name='" + branchOrdept + "'");
                    if (rs1.next()) {
                        accessList = rs1.getString(1);
                        System.out.println("Access List------------" + accessList);
                    }

                }

//                StringBuffer query = new StringBuffer().append("SELECT MSVMAP00.SVVGRP FROM MSVMAP00 INNER JOIN SCP001 ON SCP001.SCUSER=MSVMAP00.SVSGRP WHERE SCP001.SCUNME='" + prof + "'");
                if (bod.equals("Branch")) {
                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + prof + "' AND status='A'");

                    try {

                        if (rs2.next()) {

                            String programeSigPorf = rs2.getString(1);
                            System.out.println("Inside AS400----------------------" + prof);

                            ResultSet rs7 = connection.createStatement().executeQuery("SELECT br_code FROM admin_branch WHERE br_name='" + branchOrdept + "'");

                            if (rs7.next()) {

//                                String brCode = "99999";
//                                if (bod.equals("Branch")) {
                                System.out.println("rs7----------------------");
                                String brCode = rs7.getString(1);
//                                }
                                ResultSet rs8 = connection.createStatement().executeQuery("SELECT new_user_name FROM admin_user_creation_det WHERE user_id='" + epf + "' AND  status='A'");

                                if (rs8.next()) {

                                    System.out.println("rs8----------------------");
                                    String fullName = rs8.getString(1);
                                    String msg = "";

///////////////////////////////////////////////////////////////////////////////////
                                    GetSignatureUSers sigProf = new GetSignatureUSers();
                                    String signatureProfile = sigProf.getSignatureUser(epf);
                                    System.out.println("signatureProfile--------" + signatureProfile);
///////////////////////////////////////////////////////////////////////////////////

                                    System.out.println("Before AS400");
                                    AS400Programme prog = new AS400Programme();
                                    msg = prog.programmeRun(envr, "Temperory Access", signatureProfile, programeSigPorf, "", fullName, brCode, "", "", "");
                                    System.out.println("programe done----" + msg);

                                    if (msg.equals("1")) {
                                        String sql = "update admin_access_assign set requset_id=? , access_code=? where user_id=? AND status=?";
                                        PreparedStatement psUpdate = connection.prepareStatement(sql);
                                        psUpdate.setString(1, rqstId);
                                        psUpdate.setString(2, accessList);
                                        psUpdate.setString(3, epf);
                                        psUpdate.setString(4, "A");
                                        int result = psUpdate.executeUpdate();

                                        if (result > 0) {

                                            String sql1 = "update admin_main_table set new_br=? , new_prof=?, new_desig=?, new_till=?, branch_or_dept=? where user_id=? AND status=?";
                                            PreparedStatement psUpdate1 = connection.prepareStatement(sql1);
                                            psUpdate1.setString(1, branchOrdept);
                                            psUpdate1.setString(2, prof);
                                            psUpdate1.setString(3, desig);
                                            psUpdate1.setString(4, till);
                                            psUpdate1.setString(5, bod);
                                            psUpdate1.setString(6, epf);
                                            psUpdate1.setString(7, "A");
                                            int result1 = psUpdate1.executeUpdate();

                                            try {

                                                String sql9 = "UPDATE admin_temp_access SET expire=? WHERE user_id=? AND expire=?";
                                                PreparedStatement psUpdate9 = connection.prepareStatement(sql9);
                                                psUpdate9.setString(1, "N");
                                                psUpdate9.setString(2, epf);
                                                psUpdate9.setString(3, "Y");
                                                int result6 = psUpdate9.executeUpdate();
                                                if (result6 > 0) {
                                                    String fromD = "";
                                                    String toD = "";

                                                    ConfigEmail obj = new ConfigEmail();
                                                    String TforS = obj.tforSTempAccRemv(email, addNewRqstId, signatureProfile, branchOrdept, newBranchODept, fullName, till, newTill, "Temporary Access", bod);

                                                    String TforSSend = obj.tforSTempUserSend(email, addNewRqstId, signatureProfile, branchOrdept, fullName, till, "Temporary Access", bod, fromD, toD, prof);

                                                    rtnValue = "success";

                                                }

                                                LOGGER.info("sucess");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                LOGGER.error("error : ", e);
                                            }

                                        }

                                    }
                                }
                            }
                        }
                        LOGGER.info("sucess");
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("error : ", e);
                    }
                } else if (bod.equals("Department")) {
                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT admin_config_profile.velocity_group FROM admin_config_profile INNER JOIN admin_dept ON admin_dept.dept_cc=admin_config_profile.cc WHERE admin_config_profile.profile='" + prof + "' AND admin_dept.dept_name='" + branchOrdept + "'");

                    try {

                        if (rs2.next()) {

                            String programeSigPorf = rs2.getString(1);
                            System.out.println("Inside AS400----------------------" + prof);

                            ResultSet rs7 = connection.createStatement().executeQuery("SELECT br_code FROM admin_branch WHERE br_name='" + branchOrdept + "'");

                            if (rs7.next()) {

                                String brCode = "99999";

                                if (bod.equals("Branch")) {
                                    System.out.println("rs7----------------------");
                                    brCode = rs7.getString(1);
                                }
                                ResultSet rs8 = connection.createStatement().executeQuery("SELECT new_user_name FROM admin_user_creation_det WHERE user_id='" + epf + "' AND  status='A'");

                                if (rs8.next()) {

                                    System.out.println("rs8----------------------");
                                    String fullName = rs8.getString(1);
                                    String msg = "";

///////////////////////////////////////////////////////////////////////////////////
                                    GetSignatureUSers sigProf = new GetSignatureUSers();
                                    String signatureProfile = sigProf.getSignatureUser(epf);
                                    System.out.println("signatureProfile--------" + signatureProfile);
///////////////////////////////////////////////////////////////////////////////////

                                    System.out.println("Before AS400");
                                    AS400Programme prog = new AS400Programme();
                                    msg = prog.programmeRun(envr, "Temperory Access", signatureProfile, programeSigPorf, "", fullName, brCode, "", "", "");
                                    System.out.println("programe done----" + msg);

                                    if (msg.equals("1")) {
                                        String sql = "update admin_access_assign set requset_id=? , access_code=? where user_id=? AND status=?";
                                        PreparedStatement psUpdate = connection.prepareStatement(sql);
                                        psUpdate.setString(1, rqstId);
                                        psUpdate.setString(2, accessList);
                                        psUpdate.setString(3, epf);
                                        psUpdate.setString(4, "A");
                                        int result = psUpdate.executeUpdate();

                                        if (result > 0) {

                                            String sql1 = "update admin_main_table set new_br=? , new_prof=?, new_desig=?, new_till=?, branch_or_dept=? where user_id=? AND status=?";
                                            PreparedStatement psUpdate1 = connection.prepareStatement(sql1);
                                            psUpdate1.setString(1, branchOrdept);
                                            psUpdate1.setString(2, prof);
                                            psUpdate1.setString(3, desig);
                                            psUpdate1.setString(4, till);
                                            psUpdate1.setString(5, bod);
                                            psUpdate1.setString(6, epf);
                                            psUpdate1.setString(7, "A");
                                            int result1 = psUpdate1.executeUpdate();

                                            try {

                                                String sql9 = "UPDATE admin_temp_access SET expire=? WHERE user_id=? AND expire=?";
                                                PreparedStatement psUpdate9 = connection.prepareStatement(sql9);
                                                psUpdate9.setString(1, "N");
                                                psUpdate9.setString(2, epf);
                                                psUpdate9.setString(3, "Y");
                                                int result6 = psUpdate9.executeUpdate();

                                                if (result6 > 0) {
                                                    String fromD = "";
                                                    String toD = "";

                                                    ConfigEmail obj = new ConfigEmail();
                                                    String TforS = obj.tforSTempAccRemv(email, addNewRqstId, signatureProfile, branchOrdept, newBranchODept, fullName, till, newTill, "Temporary Access", bod);

                                                    String TforSSend = obj.tforSTempUserSend(email, addNewRqstId, signatureProfile, branchOrdept, fullName, till, "Temporary Access", bod, fromD, toD, prof);

                                                    rtnValue = "success";

                                                }

                                                LOGGER.info("sucess");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                LOGGER.error("error : ", e);
                                            }

                                        }

                                    }
                                }
                            }
                        }
                        LOGGER.info("sucess");
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("error : ", e);
                    }
                }

                LOGGER.info("sucess");
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return rtnValue;
    }

    @Override
    public String deleteTempAccess(User tempAccess) {
        System.out.println("33" + tempAccess.getEpfNo());

        try {
            String sql2 = "update admin_temp_access set progress_code=?,expire=? where user_id=?";

            int result2 = jdbcTemplate.update(sql2, "25", "N", tempAccess.getEpfNo());

            if (result2 > 0) {
                System.out.println("A new row has been inserted.");
            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("user.tempaccessdelete.sucess", null, Locale.US);
    }

    @Override
    public List<User> getAllBranchOrDept(User user) {

        List<User> userList = new ArrayList();
        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));
        System.out.println("-----------------------------" + user.getClick());

        String sql = " ";
        System.out.println("---------------" + user.getClick());
        if (user.getClick() != null) {
            if (user.getClick().trim().equals("Branch")) {

                sql = "WHERE CFCNTR < 1000";
            } else if (user.getClick().trim().equals("Region")) {

                sql = "WHERE CFCNTR BETWEEN 1022 AND 1027";
            } else if (user.getClick().trim().equals("Department")) {

                sql = "WHERE CFCNTR >1000 AND CFCNTR NOT IN (1022,1023,1024,1025,1026,1027)";
            }

            StringBuffer query = new StringBuffer().append("select CFTNME from CFP420 " + sql + " ORDER BY CFTNME");

            try (ResultSet rs = AS400DBConnection.search(query.toString())) {

                while (rs.next()) {
                    User user1 = new User();

                    user1.setNewBranchOrDept(rs.getString(1).trim());
                    userList.add(user1);

                }

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);

            }
        }

        return userList;
    }

    @Override
    public User getToSelectButtonTempById(User tempAccess) {
        System.out.println("getToSelectButtonById Done");
        List<User> userList = new ArrayList();
        User user = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();

            String sql = "SELECT admin_user_creation_det.new_user_name, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_main_table.user_id=admin_user_creation_det.user_id where admin_main_table.user_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tempAccess.getEpfNo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user.setFullName(rs.getString(1));
                user.setEpfNo(tempAccess.getEpfNo());
                user.setBranchorDept(rs.getString(3));
                user.setProfile(rs.getString(4));
                user.setDesignation(rs.getString(5));
                user.setCurrentTill(rs.getString(6));

                userList.add(user);

                System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return user;
    }

    @Override
    public String updateTempAccess(User tempAccess) {
        System.out.println(tempAccess.getEpfNo() + "Done");

        String bod = "";

        if (tempAccess.getClick().trim().equals("Department")) {
            bod = "4050";
        } else if (tempAccess.getClick().trim().equals("Branch")) {
            bod = "4150";
        } else if (tempAccess.getClick().trim().equals("Region Branch")) {
            bod = "4450";
        }

        try {
            String sql = "update admin_temp_access set new_br=?, new_profile=?, new_desig=?, new_till=?, date_from=?, date_to=?, req_type=?, progress_stat=?,progress_code=? where progress_code!=? and user_id=?";

            int result = jdbcTemplate.update(sql, tempAccess.getNewBranchOrDept().trim(), tempAccess.getNewProfile().trim(), tempAccess.getNewDesignation().trim(), tempAccess.getNewTill().trim(), tempAccess.getStartDate().substring(0, 10), tempAccess.getEndDate().substring(0, 10), "Temperory Access", "Pending", bod, "25", tempAccess.getEpfNo());

            if (result > 0) {
                System.out.println("A sql row has been inserted.");
            }

            System.out.println(result);
            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("user.tempaccessupdate.sucess", null, Locale.US);
    }

    @Override
    public User getAppInfoTe(User tempAccess) {

        System.out.println("-------------" + tempAccess.getEpfNo() + "   " + tempAccess.getRqstType() + "   " + tempAccess.getStatus() + "    " + tempAccess.getNewBranchOrDept());

        User tempacc = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT branch_or_dept FROM admin_temp_access WHERE user_id=? AND progress_code!=25 AND expire='Y'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tempAccess.getEpfNo());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String bod = rs.getString(1);
                System.out.println("--------" + bod);
                String sql2 = "SELECT profile_or_design, bod_name FROM admin_approal_users WHERE branch_or_dept=? AND rqst_type=? AND show_info_stts=?";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setString(1, bod.trim());
                ps2.setString(2, tempAccess.getRqstType().trim());
                ps2.setString(3, tempAccess.getStatus().trim());
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {

                    String profOrDesig = rs2.getString(1);
                    String branchOrDept = rs2.getString(2);

                    if (branchOrDept.equals("AB") && profOrDesig.equals("Region Manager")) {
                        String sql4 = "SELECT admin_region_office.region_office_name FROM admin_region_office INNER JOIN admin_branch ON admin_branch.region_unit=admin_region_office.region_office WHERE admin_branch.br_name=?";
                        PreparedStatement ps4 = connection.prepareStatement(sql4);
                        ps4.setString(1, tempAccess.getNewBranchOrDept());
                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            branchOrDept = rs4.getString(1);
                        }

                    } else if (branchOrDept.equals("AB")) {
                        branchOrDept = tempAccess.getNewBranchOrDept();
                    }

                    System.out.println("-----------" + profOrDesig + "         " + branchOrDept);
                    String sql3 = "SELECT admin_user_creation_det.new_user_name FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det.user_id=admin_main_table.user_id WHERE admin_main_table.new_br=? AND admin_main_table.new_prof=? AND admin_main_table.progres_stus_code!=25";
                    PreparedStatement ps3 = connection.prepareStatement(sql3);
                    ps3.setString(1, branchOrDept.trim());
                    ps3.setString(2, profOrDesig.trim());

                    ResultSet rs3 = ps3.executeQuery();

                    if (rs3.next()) {

                        tempacc.setFullName(rs3.getString(1));
                        System.out.println("-------------");

                    }
                }
            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return tempacc;
    }

    @Override
    public String removeTempMnually(User tempAccess) {
        System.out.println(tempAccess.getEpfNo() + "Done");
        DbConn conn = new DbConn();
        Connection connection = conn.getConn();
        try {

            String sql1 = "SELECT prev_br_dep, prev_prof, prev_desig, prev_till FROM admin_main_table WHERE user_id=? AND progres_stus_code!=25 AND status='A'";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setString(1, tempAccess.getEpfNo().trim());

            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                String bod = "";
                String brCode = "";
                String codeList = "";
                String prevDeptOrBranchName = rs1.getString(1);
                String prevProf = rs1.getString(2);
                String prevDesig = rs1.getString(3);
                String prevTill = rs1.getString(4);

                System.out.println("-------------" + prevDeptOrBranchName + "  " + prevProf + "   " + prevDesig + "   " + prevTill);

                if (prevDeptOrBranchName.contains("BRANCH")) {
                    bod = "Branch";

                    try {

                        String sql2 = "SELECT br_code FROM admin_branch WHERE br_name=?";
                        PreparedStatement ps2 = connection.prepareStatement(sql2);
                        ps2.setString(1, prevDeptOrBranchName);

                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            brCode = rs2.getString(1);

                            String sql5 = "SELECT access_code FROM admin_branch_ass_acc WHERE profile=?";
                            PreparedStatement ps5 = connection.prepareStatement(sql5);
                            ps5.setString(1, prevProf);
                            ResultSet rs5 = ps5.executeQuery();

                            if (rs5.next()) {

                                codeList = rs5.getString(1);

                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (prevDeptOrBranchName.contains("REGION")) {
                    bod = "Region";

                    try {

                        String sql2 = "SELECT re_code FROM admin_region_office WHERE region_office_name=?";
                        PreparedStatement ps2 = connection.prepareStatement(sql2);
                        ps2.setString(1, prevDeptOrBranchName);

                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            brCode = rs2.getString(1);

                            String sql5 = "SELECT access_code FROM admin_region_office_access WHERE profile=?";
                            PreparedStatement ps5 = connection.prepareStatement(sql5);
                            ps5.setString(1, prevProf);
                            ResultSet rs5 = ps5.executeQuery();

                            if (rs5.next()) {

                                codeList = rs5.getString(1);

                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    bod = "Department";

                    try {

                        String sql2 = "SELECT dept_code FROM admin_dept WHERE dept_name=?";
                        PreparedStatement ps2 = connection.prepareStatement(sql2);
                        ps2.setString(1, prevDeptOrBranchName);

                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            brCode = rs2.getString(1);

                            String sql5 = "SELECT access_code FROM admin_department_ass_acc WHERE profile=? AND cc=?";
                            PreparedStatement ps5 = connection.prepareStatement(sql5);
                            ps5.setString(1, prevProf);
                            ps5.setString(2, brCode);
                            ResultSet rs5 = ps5.executeQuery();

                            if (rs5.next()) {

                                codeList = rs5.getString(1);

                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                try {

                    String sql3 = "update admin_main_table set new_br=?, new_prof=?, new_desig=?, new_till=?, brdp_code=? where progress_code!=25 and user_id=? and status='A'";

                    int result = jdbcTemplate.update(sql3, prevDeptOrBranchName, prevProf, prevDesig, prevTill, brCode, tempAccess.getEpfNo());

                    if (result > 0) {

                        String sql4 = "update admin_temp_access set expire=? where user_id=?";

                        int result1 = jdbcTemplate.update(sql4, "N", tempAccess.getEpfNo());

                        if (result1 > 0) {

                            String sql6 = "update admin_access_assign set access_code=? where user_id=? AND status='A'";

                            int result6 = jdbcTemplate.update(sql6, codeList, tempAccess.getEpfNo());

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("--------------------------" + bod + "            " + brCode);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return messageSource.getMessage("user.tempaccessupdate.sucess", null, Locale.US);
    }

}
