/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.AS400Programme;
import com.mbsl.Classes.AccessAssign;
import com.mbsl.Classes.AdCreation;
import com.mbsl.Classes.BranchDeptCode;
import com.mbsl.Classes.ConfigEmail;
import com.mbsl.Classes.EnvChange;
import com.mbsl.Classes.GetSignatureUSers;
import com.mbsl.Classes.Payment;
import com.mbsl.Classes.SendSms;
import com.mbsl.Classes.SignatureApprove;

import com.mbsl.DbConn;
import com.mbsl.Properties;
import com.mbsl.dao.ApproveReqstDao;
import com.mbsl.model.StatusList;
import com.mbsl.model.User;
import com.mbsl.util.GetUserDetails;
import com.mbsl.velocity.as400.environment.AS400Libraries;
import com.mbsl.velocity.dal.as400.AS400DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
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
public class ApproveReqstDaoImpl implements ApproveReqstDao {

    private static final Logger LOGGER = LogManager.getLogger(ApproveReqstDaoImpl.class);

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
    public List<User> getApproveRqsts(HttpSession session) {
        JsonArray userData = GetUserDetails.getUserNameAndBranch(session);
        System.out.println("Approvel rqst Done---" + userData.get(0) + "---" + userData.get(1));
        DbConn conn = new DbConn();
        List<User> userList = new ArrayList();
        String accessModel;

        JsonArray ja = new JsonArray();

        StatusList sl = new StatusList();
        List<String> pendingList = new ArrayList<>();

        pendingList.add("'50'");
        pendingList.add("'450'");
        pendingList.add("'950'");
        pendingList.add("'1350'");
        pendingList.add("'1750'");
        pendingList.add("'2250'");
        pendingList.add("'2650'");
        pendingList.add("'3050'");
        pendingList.add("'3350'");
        pendingList.add("'3750'");
        pendingList.add("'4150'");
        pendingList.add("'4450'");

        sl = checkAvailability(userData.get(0).toString(), userData.get(1).toString());
        String checkCode = checkBranch(userData.get(0).toString(), userData.get(1).toString());
        System.out.println("Check Code" + checkCode);
        System.out.println("Avalilability-----");
        System.out.println("sl----");

        int regcount = userData.get(1).toString().length() - 1;
        String subreg = userData.get(1).toString().substring(1, regcount);
        String reg = ",'" + subreg + "'";

        try {
            String status = "";
            String bList = ",";
            for (String list : sl.getUserCreation()) {
                status += "'" + list + "',";
                System.out.println("Status List-----");
            }
            for (String regB : sl.getRegionList()) {

                bList += "'" + regB + "',";
                System.out.println("bList------" + bList);
            }
            int chaCount = status.length() - 1;
            System.out.println("Char count-----" + chaCount);
            status = status.substring(0, chaCount);
            String role = sl.getRoleCheck();
            Connection connection = conn.getConn();

            String sqlPart = "";
            String sqlPartRegion = "";

            if (!role.equals("Y")) {
                String userData1 = userData.get(1).toString();
                sqlPartRegion = "";
                sqlPart = " AND prev_br_dep=" + checkCode;

            }
            if (sl.getRegOrNot().equals("Y")) {

                int chaCount2 = bList.length() - 1;
                bList = bList.substring(1, chaCount2);

                System.out.println("reg----------------------------" + regcount + reg);
                sqlPart = "";
                sqlPartRegion = "  AND prev_br_dep IN ( " + bList + reg + ")";
            } else {

                sqlPart = " AND prev_br_dep=" + checkCode;
                sqlPartRegion = "";
            }
            String[] sttsList = status.split(",");

            for (int i = 0; i < sttsList.length; i++) {
                String sql = "";
                System.out.println("sqlPart-------------------------" + sqlPart + "     " + role + "     " + sttsList[i] + "             " + sl.getPendingList().size() + "        " + sl.getHod());

                if (pendingList.contains(sttsList[i]) && sl.getHod().equals("Y")) {
                    System.out.println("ls.getHod.....................");
                    sql = "select rqst_id,user_id,prev_br_dep,prev_prof,prev_desig,req_type,branch_or_dept from admin_main_table where status='A' and progres_stus_code IN ( " + sttsList[i] + ")" + sqlPart + sqlPartRegion;
                } else {

//                    if ((role.equals("Y") && (sttsList[i].equals("'50'")) || sttsList[i].equals("'450'") || sttsList[i].equals("'950'"))) {
                    if (role.equals("Y")) {

                        sql = "select rqst_id,user_id,prev_br_dep,prev_prof,prev_desig,req_type,branch_or_dept from admin_main_table where status='A' and progres_stus_code IN ( " + sttsList[i] + ")" + sqlPartRegion;
                    } else if ((role.equals("N") && (sttsList[i].equals("'600'") || sttsList[i].equals("'950'") || sttsList[i].equals("'1900'") || sttsList[i].equals("'2250'") || sttsList[i].equals("'2650'") || sttsList[i].equals("'3400'") || sttsList[i].equals("'3750'") || sttsList[i].equals("'4150'") || sttsList[i].equals("'4450'")))) {

//                        sqlPart = " AND prev_br_dep=" + checkCode;
                        sql = "select rqst_id,user_id,prev_br_dep,prev_prof,prev_desig,req_type,branch_or_dept from admin_main_table where status='A' and progres_stus_code IN ( " + sttsList[i] + ")" + sqlPartRegion;
                    } else {
                        sqlPart = " AND prev_br_dep=" + checkCode;
                        sql = "select rqst_id,user_id,prev_br_dep,prev_prof,prev_desig,req_type,branch_or_dept FROM admin_main_table where status='A' and progres_stus_code IN ( " + sttsList[i] + ")" + sqlPartRegion + sqlPart;
                    }
                }
                System.out.println(sql);
                ResultSet rs = connection.createStatement().executeQuery(sql);
                while (rs.next()) {
                    User user = new User();
                    user.setRqstId(rs.getString(1));
                    user.setEpfNo(rs.getString(2));
                    user.setBranchorDept(rs.getString(3));
                    user.setProfile(rs.getString(4));
                    user.setDesignation(rs.getString(5));
                    user.setRqstType(rs.getString(6));
                    user.setClick(rs.getString(7));

                    userList.add(user);

                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + userList.get(0).getEpfNo());
                }
            }

            try {
                bList = ",";
                for (String list1 : sl.getUserTransfer()) {
                    status += ",'" + list1 + "'";
                    System.out.println("Status List-----");
                }
                for (String regB1 : sl.getRegionList()) {

                    bList += "'" + regB1 + "',";
                    System.out.println("bList------");
                }
                String role1 = sl.getRoleCheck();
                System.out.println("Status sub----" + status);
                System.out.println("role sub------" + role1);
                String sqlPart1 = "";
                String sqlPartRegion1 = "";
                if (!role1.equals("Y")) {
                    String userData2 = userData.get(1).toString();
                    sqlPartRegion1 = "";
                    sqlPart1 = " AND admin_user_transfers.new_br=" + checkCode;

                }
                if (sl.getRegOrNot().equals("Y")) {

                    int chaCount3 = bList.length() - 1;
                    bList = bList.substring(1, chaCount3);

                    sqlPart1 = "";
                    sqlPartRegion1 = "  AND admin_user_transfers.new_br IN ( " + bList + reg + ")";
                } else {
                    sqlPart1 = " AND admin_user_transfers.new_br=" + checkCode;
                    sqlPartRegion1 = "";

                }

                String[] sttsList1 = status.split(",");

                for (int i = 0; i < sttsList1.length; i++) {

                    String sql2 = "";
                    System.out.println("sqlPart-------------------------" + sqlPart1 + "     " + role1 + "     " + sttsList1[i]);

                    if (pendingList.contains(sttsList1[i]) && sl.getHod().equals("Y")) {

                        sql2 = "SELECT admin_user_transfers.req_id, admin_user_transfers.user_id, admin_user_transfers.new_br, admin_user_transfers.new_profile,  admin_user_transfers.new_desig, admin_user_transfers.req_type, admin_user_transfers.branch_or_dept  FROM admin_user_transfers INNER JOIN admin_main_table ON admin_user_transfers.user_id=admin_main_table.user_id where progress_code IN ( " + sttsList1[i] + ") AND admin_main_table.status='A'" + sqlPart1 + sqlPartRegion1;
                    } else {

                        if (role1.equals("Y")) {
                            sql2 = "SELECT admin_user_transfers.req_id, admin_user_transfers.user_id, admin_user_transfers.new_br, admin_user_transfers.new_profile,  admin_user_transfers.new_desig, admin_user_transfers.req_type, admin_user_transfers.branch_or_dept  FROM admin_user_transfers INNER JOIN admin_main_table ON admin_user_transfers.user_id=admin_main_table.user_id where progress_code IN ( " + sttsList1[i] + ") AND admin_main_table.status='A'" + sqlPartRegion1;
                        } else {
                            sql2 = "SELECT admin_user_transfers.req_id, admin_user_transfers.user_id, admin_user_transfers.new_br, admin_user_transfers.new_profile,  admin_user_transfers.new_desig, admin_user_transfers.req_type, admin_user_transfers.branch_or_dept  FROM admin_user_transfers INNER JOIN admin_main_table ON admin_user_transfers.user_id=admin_main_table.user_id where progress_code IN ( " + sttsList1[i] + ") AND admin_main_table.status='A'" + sqlPartRegion1;
                        }
                    }

                    ResultSet rs2 = connection.createStatement().executeQuery(sql2);
                    while (rs2.next()) {
                        User user = new User();

                        user.setRqstId(rs2.getString(1));
                        user.setEpfNo(rs2.getString(2));
                        user.setNewBranchOrDept(rs2.getString(3));
                        user.setNewProfile(rs2.getString(4));
                        user.setNewDesignation(rs2.getString(5));
                        user.setRqstType(rs2.getString(6));
                        user.setClick(rs2.getString(7));

                        userList.add(user);

                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + userList.get(0).getEpfNo());

                    }
                }
                try {
                    bList = ",";
                    for (String list2 : sl.getUserInactivation()) {
                        status += ",'" + list2 + "'";
                        System.out.println("Status List-----");
                    }
                    for (String regB2 : sl.getRegionList()) {

                        bList += "'" + regB2 + "',";
                        System.out.println("bList------");
                    }

                    String role2 = sl.getRoleCheck();
                    System.out.println("Status sub----" + status);
                    System.out.println("role sub------" + role2);
                    String sqlPart2 = "";
                    String sqlPartRegion2 = "";
                    if (!role2.equals("Y")) {
                        String userData2 = userData.get(1).toString();
                        sqlPartRegion2 = "";
                        sqlPart2 = " AND admin_main_table.prev_br_dep=" + checkCode;

                    }
                    if (sl.getRegOrNot().equals("Y")) {

                        int chaCount4 = bList.length() - 1;
                        bList = bList.substring(1, chaCount4);

                        sqlPart2 = "";
                        sqlPartRegion2 = "  AND admin_main_table.prev_br_dep IN ( " + bList + reg + ")";
                    } else {

                        sqlPart2 = " AND admin_main_table.prev_br_dep=" + checkCode;
                        sqlPartRegion2 = "";

                    }

                    String[] sttsList2 = status.split(",");

                    for (int i = 0; i < sttsList2.length; i++) {

                        String sql3 = "";
                        System.out.println("sqlPart-------------------------" + sqlPart2 + "     " + role2 + "     " + sttsList2[i]);

                        if (pendingList.contains(sttsList2[i]) && sl.getHod().equals("Y")) {

                            sql3 = "SELECT admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_user_inactivate.req_id, admin_user_inactivate.user_id, admin_user_inactivate.reason, admin_user_inactivate.req_type, admin_main_table.branch_or_dept FROM admin_user_inactivate INNER JOIN admin_main_table ON admin_user_inactivate.user_id=admin_main_table.user_id where progress_code IN ( " + sttsList2[i] + ") AND admin_main_table.status='A'" + sqlPart2 + sqlPartRegion2;
                        } else {

                            if (role2.equals("Y")) {
                                sql3 = "SELECT admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_user_inactivate.req_id, admin_user_inactivate.user_id, admin_user_inactivate.reason, admin_user_inactivate.req_type, admin_main_table.branch_or_dept FROM admin_user_inactivate INNER JOIN admin_main_table ON admin_user_inactivate.user_id=admin_main_table.user_id where progress_code IN ( " + sttsList2[i] + ") AND admin_main_table.status='A'" + sqlPartRegion2;
                            } else {
                                sql3 = "SELECT admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_user_inactivate.req_id, admin_user_inactivate.user_id, admin_user_inactivate.reason, admin_user_inactivate.req_type, admin_main_table.branch_or_dept FROM admin_user_inactivate INNER JOIN admin_main_table ON admin_user_inactivate.user_id=admin_main_table.user_id where progress_code IN ( " + sttsList2[i] + ") AND admin_main_table.status='A'" + sqlPartRegion2;
                            }
                        }

                        System.out.println(sql3);
                        ResultSet rs3 = connection.createStatement().executeQuery(sql3);
                        while (rs3.next()) {
                            User user = new User();

                            user.setBranchorDept(rs3.getString(1));
                            user.setProfile(rs3.getString(2));
                            user.setDesignation(rs3.getString(3));
                            user.setRqstId(rs3.getString(4));
                            user.setEpfNo(rs3.getString(5));
                            user.setReason(rs3.getString(6));
                            user.setRqstType(rs3.getString(7));
                            user.setClick(rs3.getString(8));

                            userList.add(user);

                            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + userList.get(0).getEpfNo());
                        }

                    }

                    try {
                        bList = ",";
                        for (String list3 : sl.getTempAccess()) {
                            status += ",'" + list3 + "'";
                            System.out.println("Status List-----");
                        }
                        for (String regB3 : sl.getRegionList()) {

                            bList += "'" + regB3 + "',";
                            System.out.println("bList------");
                        }
                        String role3 = sl.getRoleCheck();
                        System.out.println("Status sub----" + status);
                        System.out.println("role sub------" + role3);
                        String sqlPart3 = "";
                        String sqlPartRegion3 = "";
                        if (!role3.equals("Y")) {
                            String userData2 = userData.get(1).toString();
                            sqlPartRegion3 = "";
                            sqlPart3 = " AND new_br=" + checkCode;

                        }
                        if (sl.getRegOrNot().equals("Y")) {

                            int chaCount5 = bList.length() - 1;
                            bList = bList.substring(1, chaCount5);

                            sqlPart3 = "";
                            sqlPartRegion3 = "  AND new_br IN ( " + bList + reg + ")";
                        }

                        String[] sttsList3 = status.split(",");

                        for (int i = 0; i < sttsList3.length; i++) {

                            String sql4 = "";
                            System.out.println("sqlPart-------------------------" + sqlPart2 + "     " + role2 + "     " + sttsList3[i]);

                            if (role3.equals("Y") && (sttsList3[i].equals("'4050'"))) {

                                sqlPart3 = " AND new_br=" + checkCode;
                                sql4 = "SELECT rqst_id, user_id, new_br, new_profile, new_desig, req_type, branch_or_dept FROM admin_temp_access WHERE progress_code IN ( " + sttsList3[i] + ") AND progress_code!=25" + sqlPart3 + sqlPartRegion3;
                            } else {

                                sql4 = "SELECT rqst_id, user_id, new_br, new_profile, new_desig, req_type, branch_or_dept FROM admin_temp_access WHERE progress_code IN ( " + sttsList3[i] + ") AND progress_code!=25" + sqlPartRegion3;
                            }

                            System.out.println(sql4);
                            ResultSet rs4 = connection.createStatement().executeQuery(sql4);
                            while (rs4.next()) {
                                User user = new User();

                                user.setRqstId(rs4.getString(1));
                                user.setEpfNo(rs4.getString(2));
                                user.setNewBranchOrDept(rs4.getString(3));
                                user.setNewProfile(rs4.getString(4));
                                user.setNewDesignation(rs4.getString(5));
                                user.setRqstType(rs4.getString(6));
                                user.setClick(rs4.getString(7));

                                userList.add(user);

                                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + userList.get(0).getEpfNo());

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("error : ", e);
                    }

                    LOGGER.info("sucess");
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("error : ", e);
                }

                LOGGER.info("sucess");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return userList;
    }

    private String checkBranch(String id, String ccCode) {

        DbConn conn = new DbConn();
        String bod = ccCode;
        System.out.println("id------" + id);
        try {
            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT branch_or_dept FROM admin_main_table  where user_id=" + id + " AND progres_stus_code!=25");
            if (rs.next()) {
                String getBranch = rs.getString(1);
                System.out.println("getBranch---" + getBranch);
                if (getBranch.equals("Branch")) {
                    ResultSet rs1 = connection.createStatement().executeQuery("SELECT br_name FROM admin_branch  where br_code=" + ccCode);
                    if (rs1.next()) {
                        bod = "'" + rs1.getString(1) + "'";
                    }
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);

        }
        System.out.println("bod--------" + bod);
        return bod;
    }

    private StatusList checkAvailability(String logedID, String bod) {
        StatusList sl = new StatusList();
        DbConn conn = new DbConn();
//        String setType = "'" + type + "'";
        String branchOrDept = "'" + bod + "'";
        String value = "0";
        String sqlPart = "";
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT prev_prof, prev_br_dep, progres_stus_code from admin_main_table where user_id=" + logedID + " AND progres_stus_code!=25");
            System.out.println("Qrydone " + rs);
            JsonArray jb = new JsonArray();
            if (rs.next()) {
                System.out.println("111");

                String data = rs.getString(1);
                String prevDept = rs.getString(2);
                String prvStts = rs.getString(3);

                System.out.println("User Data-------" + data);
                //checking the role whether ITHead or not
                String RoleCheck = "N";
                if (data.equals("Staff Assistant") && prevDept.equals("HUMAN RESOURCE")) {
                    RoleCheck = "Y";
                } else if (data.equals("General manager(HOD)") && prevDept.equals("HUMAN RESOURCE")) {
                    RoleCheck = "Y";
                    sqlPart = " AND bod_name IN ('HUMAN RESOURCE','AB')";
                } else if (data.equals("General manager(HOD)") && prevDept.equals("INFORMATION TECHNOLOGY")) {
                    RoleCheck = "Y";
                    sqlPart = " AND bod_name IN ('INFORMATION TECHNOLOGY','AB')";
                } else if (data.equals("General manager(HOD)") && (!prevDept.equals("INFORMATION TECHNOLOGY") || !prevDept.equals("HUMAN RESOURCE"))) {

                    sqlPart = " AND bod_name IN ('AB')";

                }
                if (data.equals("General manager(HOD)") || data.equals("Branch Manager") || data.equals("Region Manager")) {

                    sl.setHod("Y");

                } else {

                    sl.setHod("N");
                }

                try {

//                    if (RoleCheck.equals("N")) {
//
//                        sqlPart = " AND bod_name='AB'";
//                    } else {
//                        sqlPart = " AND bod_name='" + prevDept + "'";
//                    }
                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT rqst_type,view_status from admin_approal_users where profile_or_design='" + data + "'" + sqlPart);
                    List<String> ug = new ArrayList();
                    List<String> ut = new ArrayList();
                    List<String> ui = new ArrayList();
                    List<String> ta = new ArrayList();
                    List<String> rg = new ArrayList();

                    String isRegion = "N";

                    if (prevDept.equals("REGION 1") || prevDept.equals("REGION 2") || prevDept.equals("REGION 3") || prevDept.equals("REGION 4") || prevDept.equals("REGION 5") || prevDept.equals("REGION 6")) {

                        ResultSet rs3 = connection.createStatement().executeQuery(" SELECT admin_branch.br_name FROM admin_branch INNER JOIN admin_region_office ON admin_region_office.region_office=admin_branch.region_unit WHERE admin_region_office.region_office_name='" + prevDept + "'");

                        while (rs3.next()) {

                            rg.add(rs3.getString(1));
                            sl.setRegionList(rg);

                            isRegion = "Y";
                        }

                    }

                    sl.setRegOrNot(isRegion);

                    while (rs2.next()) {
                        String rType = rs2.getString(1);
                        if (rType.equals("User Grant")) {
//                            hm.put("uc", rs2.getString(2));
                            ug.add(rs2.getString(2));
                            sl.setUserCreation(ug);
                        } else if (rType.equals("User Transfer")) {
//                            hm.put("ut", rs2.getString(2));
                            ut.add(rs2.getString(2));
                            sl.setUserTransfer(ut);
                        } else if (rType.equals("User Inactivate")) {
//                            hm.put("ui", rs2.getString(2));
                            ui.add(rs2.getString(2));
                            sl.setUserInactivation(ui);
                        } else {
                            ta.add(rs2.getString(2));
                            sl.setTempAccess(ta);
//                            hm.put("ta", rs2.getString(2));
                        }
                        String data2 = rs2.getString(2);
                        System.out.println("data2----" + data2);
                        value = data2;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("error : ", e);
                }
                sl.setRoleCheck(RoleCheck);
            }

            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        System.out.println("sl list----" + sl);
        return sl;

    }

    @Override
    public User getListByRqstId(User approveRqst) {
        System.out.println("getListByRqstId Done......." + approveRqst.getEpfNo() + "  " + approveRqst.getRqstType());
        List<User> userList = new ArrayList();
        User user = new User();
        String accessModel;
        JsonArray ja = new JsonArray();

        if (approveRqst.getRqstType().equals("User Grant")) {
            try {
                Connection connection = dataSource.getConnection();
                String sql = "SELECT admin_user_creation_det.new_user_name, admin_user_creation_det.email_add, admin_user_creation_det.mobile_no, admin_main_table.rqst_id, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_main_table.branch_or_dept, admin_main_table.password_encript, admin_main_table.branch_or_dept FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_main_table .user_id=admin_user_creation_det.user_id where admin_main_table .user_id=? AND admin_main_table.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, approveRqst.getEpfNo());
                ps.setString(2, "A");

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    user.setFullName(rs.getString(1));
                    user.setEmailAddress(rs.getString(2));
                    user.setMobileNo(rs.getString(3));
                    user.setRqstId(rs.getString(4));
                    user.setEpfNo(rs.getString(5));
                    user.setBranchorDept(rs.getString(6));
                    user.setProfile(rs.getString(7));
                    user.setDesignation(rs.getString(8));
                    user.setCurrentTill(rs.getString(9));
                    user.setClick(rs.getString(10));
                    user.setPassword(rs.getString(11));
                    user.setClick(rs.getString(12));

                    userList.add(user);

                    System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (approveRqst.getRqstType().equals("User Transfer")) {
            try {
                Connection connection = dataSource.getConnection();
                String sql = "SELECT admin_user_creation_det.new_user_name, admin_user_transfers.req_id, admin_user_transfers.user_id, admin_user_transfers.new_br, admin_user_transfers.new_profile, admin_user_transfers.new_desig, admin_user_transfers.new_till, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_user_transfers.branch_or_dept FROM admin_user_transfers INNER JOIN admin_main_table ON admin_user_transfers.user_id=admin_main_table.user_id INNER JOIN admin_user_creation_det ON admin_main_table.user_id=admin_user_creation_det.user_id where admin_main_table.user_id=? AND admin_main_table.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, approveRqst.getEpfNo());
                ps.setString(2, "A");

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    user.setFullName(rs.getString(1));
                    user.setRqstId(rs.getString(2));
                    user.setEpfNo(rs.getString(3));
                    user.setNewBranchOrDept(rs.getString(4));
                    user.setNewProfile(rs.getString(5));
                    user.setNewDesignation(rs.getString(6));
                    user.setNewTill(rs.getString(7));
                    user.setBranchorDept(rs.getString(8));
                    user.setProfile(rs.getString(9));
                    user.setDesignation(rs.getString(10));
                    user.setCurrentTill(rs.getString(11));
                    user.setClick(rs.getString(12));

                    userList.add(user);

                    System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (approveRqst.getRqstType().equals("User Inactivate")) {
            try {
                Connection connection = dataSource.getConnection();
                String sql = "SELECT admin_user_inactivate.req_id, admin_user_inactivate.reason, admin_user_creation_det.new_user_name, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_main_table.branch_or_dept, admin_main_table.branch_or_dept FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_main_table .user_id=admin_user_creation_det.user_id INNER JOIN admin_user_inactivate ON admin_user_creation_det.user_id=admin_user_inactivate.user_id where admin_main_table.user_id=? AND admin_main_table.status=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, approveRqst.getEpfNo());
                ps.setString(2, "A");

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    user.setRqstId(rs.getString(1));
                    user.setReason(rs.getString(2));
                    user.setFullName(rs.getString(3));
                    user.setEpfNo(rs.getString(4));
                    user.setBranchorDept(rs.getString(5));
                    user.setProfile(rs.getString(6));
                    user.setDesignation(rs.getString(7));
                    user.setCurrentTill(rs.getString(8));
                    user.setClick(rs.getString(9));
                    user.setClick(rs.getString(10));

                    userList.add(user);

                    System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (approveRqst.getRqstType().equals("Temperory Access")) {

            try {
                Connection connection = dataSource.getConnection();
                String sql = "SELECT admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_temp_access.rqst_id, admin_temp_access.date_from, admin_temp_access.date_to, admin_temp_access.new_br, admin_temp_access.new_profile, admin_temp_access.new_desig, admin_temp_access.new_till FROM admin_temp_access INNER JOIN admin_main_table ON admin_main_table.user_id=admin_temp_access.user_id WHERE admin_temp_access.progress_code!=25 AND admin_main_table.progres_stus_code!=25 AND admin_temp_access.user_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, approveRqst.getEpfNo());

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    user.setEpfNo(rs.getString(1));
                    user.setBranchorDept(rs.getString(2));
                    user.setProfile(rs.getString(3));
                    user.setDesignation(rs.getString(4));
                    user.setCurrentTill(rs.getString(5));
                    user.setRqstId(rs.getString(6));
                    user.setStartDate(rs.getString(7));
                    user.setEndDate(rs.getString(8));
                    user.setNewBranchOrDept(rs.getString(9));
                    user.setNewProfile(rs.getString(10));
                    user.setNewDesignation(rs.getString(11));
                    user.setNewTill(rs.getString(12));

                    userList.add(user);

                    System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

                }
                LOGGER.info("sucess");
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        }
        return user;
    }

    @Override
    public String approvedMessage(User approveRqst, HttpSession session) {
        StatusList sl = new StatusList();

        DbConn conn = new DbConn();
        Connection connection = conn.getConn();

        JsonArray userData = GetUserDetails.getUserNameAndBranch(session);
        sl = checkApprovedMessage(userData.get(0).toString(), approveRqst.getClick(), approveRqst.getEpfNo(), approveRqst.getRqstType());
        String msg = "";
        boolean b = false;
        if (approveRqst.getRqstType().equals("User Grant")) {
            try {
                String status = "";
                String progStatus = "";
                int result = 1;

                String sql1 = "update admin_main_table set progres_stus=? , progres_stus_code=? where user_id=? and status=?";

                result = jdbcTemplate.update(sql1, sl.getUserCreation().get(1), sl.getUserCreation().get(0), approveRqst.getEpfNo(), "A");

                if (result > 0) {
                    System.out.println("A sql row has been updated.");
                    try {
                        System.out.println("EPF" + approveRqst.getEpfNo());

                        ResultSet rs = connection.createStatement().executeQuery("SELECT admin_main_table.rqst_id , admin_main_table.prev_br_dep , admin_main_table.prev_prof , admin_main_table.progres_stus_code , admin_main_table.user_id, admin_main_table.prev_desig, admin_main_table.password_encript, admin_user_creation_det.new_user_name, admin_user_creation_det.email_add , admin_user_creation_det.mobile_no FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det .user_id=admin_main_table.user_id where admin_main_table.progres_stus_code!=25 and admin_user_creation_det.status='A' AND admin_main_table.status='A' AND admin_main_table.user_id='" + approveRqst.getEpfNo() + "'");

                        if (rs.next()) {

                            String rqstId = rs.getString(1);
                            String branchOrDept = rs.getString(2);
                            String prof = rs.getString(3);
                            String statusCode = rs.getString(4);
                            String epf = rs.getString(5);
                            String desig = rs.getString(6);
                            String pss = rs.getString(7);
                            String fullName = rs.getString(8);
                            String email = rs.getString(9);
                            String contNo = rs.getString(10);
                            b = true;
                            System.out.println("status---------" + statusCode);

                            if (sl.getUserCreation().get(0).equals("400") || sl.getUserCreation().get(0).equals("900") || sl.getUserCreation().get(0).equals("1300")) {

                                System.out.println("IN side------------------------" + prof);
                                AS400Libraries libs = new AS400Libraries();
                                AS400DBConnection.setLibraries(libs.getLibraries(envr));
                                System.out.println("IN side11------------------------" + prof);
                                b = false;
                                try {

                                    if (approveRqst.getClick().equals("Branch")) {

                                        try {

                                            ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_branch,ou FROM admin_branch WHERE br_name='" + branchOrDept + "'");
                                            if (rs2.next()) {

                                                String adBranch = rs2.getString(1);
                                                String ou = rs2.getString(2);

                                                String sql = "update admin_user_creation_det set ad_status=? where user_id=? AND status='A'";

                                                int result1 = jdbcTemplate.update(sql, "Pending", epf);
                                                if (result1 > 0) {
                                                    AdCreation tst = new AdCreation(epf, fullName, fullName, desig, pss, email, ou);
                                                    b = tst.addUser();
                                                    System.out.println("AD Status : " + b);

                                                    ConfigEmail obj1 = new ConfigEmail();
                                                    String cmnMail = obj1.comonMail(rqstId, "User Grant", sl.getUserCreation().get(1), approveRqst.getClick(), branchOrDept);
                                                    System.out.println("----------------Else if Send");

                                                }
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            LOGGER.error("error : ", e);
                                        }

                                        System.out.println("MSG-------" + msg);

                                    } else if (approveRqst.getClick().equals("Department")) {

                                        try {

                                            ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_dept,ou FROM admin_dept WHERE dept_name='" + branchOrDept + "'");
                                            if (rs2.next()) {

                                                String adDept = rs2.getString(1);
                                                String ou = rs2.getString(2);
                                                System.out.println("OU-----" + ou);

                                                String sql = "update admin_user_creation_det set ad_status=? where user_id=? AND status='A'";

                                                int result1 = jdbcTemplate.update(sql, "Pending", epf);

                                                if (result1 > 0) {
                                                    AdCreation tst = new AdCreation(epf, fullName, fullName, desig, pss, email, ou);
                                                    b = tst.addUser();
                                                    System.out.println("AD Status : " + b);

                                                    ConfigEmail obj1 = new ConfigEmail();
                                                    String cmnMail = obj1.comonMail(rqstId, "User Grant", sl.getUserCreation().get(1), approveRqst.getClick(), branchOrDept);
                                                    System.out.println("----------------Else if Send");
                                                }
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            LOGGER.error("error : ", e);
                                        }

                                    } else {

                                        try {

                                            ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_region,ou FROM admin_region_office WHERE region_office_name='" + branchOrDept + "'");
                                            if (rs2.next()) {

                                                String adrig = rs2.getString(1);
                                                String ou = rs2.getString(2);
                                                System.out.println("OU-----" + ou);

                                                String sql = "update admin_user_creation_det set ad_status=? where user_id=? AND status='A'";

                                                int result1 = jdbcTemplate.update(sql, "Pending", epf);

                                                if (result1 > 0) {
                                                    AdCreation tst = new AdCreation(epf, fullName, fullName, desig, pss, email, ou);
                                                    b = tst.addUser();
                                                    System.out.println("AD Status : " + b);

                                                    ConfigEmail obj1 = new ConfigEmail();
                                                    String cmnMail = obj1.comonMail(rqstId, "User Grant", sl.getUserCreation().get(1), approveRqst.getClick(), branchOrDept);
                                                    System.out.println("----------------Else if Send");
                                                }
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            LOGGER.error("error : ", e);
                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    LOGGER.error("error : ", e);
                                }

                            } else {
                                ConfigEmail obj1 = new ConfigEmail();
                                String cmnMail = obj1.comonMail(rqstId, "User Grant", sl.getUserCreation().get(1), approveRqst.getClick(), branchOrDept);
                                System.out.println("----------------Else if Send");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("error : ", e);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (approveRqst.getRqstType().equals("User Transfer")) {

            try {
                String rqstId = "";
                String newBranch = "";
                String branchOrDept = "";
                String sql = "update admin_user_transfers set progress_stat=? , progress_code=? where user_id=? AND progress_code!=25 AND progress_stat!='Approved'";

                int result = jdbcTemplate.update(sql, sl.getUserTransfer().get(1), sl.getUserTransfer().get(0), approveRqst.getEpfNo());
                b = true;
                if (sl.getUserTransfer().get(0).equals("1700") || sl.getUserTransfer().get(0).equals("2200") || sl.getUserTransfer().get(0).equals("2600") || sl.getUserTransfer().get(0).equals("3000")) {
                    try {
                        b = false;
                        ResultSet rs = connection.createStatement().executeQuery("SELECT req_id, new_br, new_profile, new_till, branch_or_dept, progress_code, new_desig FROM admin_user_transfers WHERE progress_code!=25 and user_id='" + approveRqst.getEpfNo() + "'");

                        if (rs.next()) {

                            rqstId = rs.getString(1);
                            newBranch = rs.getString(2);
                            String newProfile = rs.getString(3);
                            String newTill = rs.getString(4);
                            branchOrDept = rs.getString(5);
                            String status = rs.getString(6);
                            String newDesig = rs.getString(7);

                            ResultSet rs9 = connection.createStatement().executeQuery("SELECT prev_br_dep,prev_till FROM admin_main_table WHERE progres_stus_code!=25 and user_id='" + approveRqst.getEpfNo() + "'");

                            if (rs9.next()) {

                                String oldBranchOrDept = rs9.getString(1);
                                String oldTill = rs9.getString(2);

                                try {
                                    String statusUpdate = "";

                                    if (approveRqst.getClick().equals("Branch")) {

                                        statusUpdate = "901";

                                    } else if (approveRqst.getClick().equals("Department")) {

                                        statusUpdate = "401";

                                    } else {

                                        statusUpdate = "1301";

                                    }
                                    BranchDeptCode code = new BranchDeptCode();
                                    String bodor = code.getBrDeptCode(approveRqst.getClick(), newBranch);

                                    String sql1 = "update admin_main_table set prev_br_dep=?, new_br=?, prev_prof=?, new_prof=? , prev_desig=?, new_desig=? , prev_till=?, new_till=? , branch_or_dept=?, progres_stus_code=?, brdp_code=? where user_id=? AND status=?";
                                    int result1 = jdbcTemplate.update(sql1, newBranch, newBranch, newProfile, newProfile, newDesig, newDesig, newTill, newTill, branchOrDept, statusUpdate, bodor, approveRqst.getEpfNo(), "A");

                                    if (result1 > 0) {
                                        System.out.println("A main table has been updated.");

                                        try {

                                            ResultSet rs5 = connection.createStatement().executeQuery("SELECT admin_main_table.rqst_id , admin_main_table.prev_br_dep , admin_main_table.prev_prof , admin_main_table.progres_stus_code , admin_main_table.user_id, admin_main_table.prev_desig, admin_main_table.password_encript, admin_user_creation_det.new_user_name, admin_user_creation_det.email_add , admin_user_creation_det.mobile_no FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det .user_id=admin_main_table.user_id where admin_main_table.progres_stus_code!=25 and admin_user_creation_det.status='A' AND admin_main_table.user_id='" + approveRqst.getEpfNo() + "'");
                                            System.out.println("Inside main table----------------------");
                                            if (rs5.next()) {
                                                System.out.println("Inside main table1----------------------");
                                                String rqstIdAc = rs5.getString(1);
                                                String branchOrDeptAc = rs5.getString(2);
                                                String prof = rs5.getString(3);
                                                String statusCode = rs5.getString(4);
                                                String epf = rs5.getString(5);
                                                String desig = rs5.getString(6);
                                                String pss = rs5.getString(7);
                                                String fullName = rs5.getString(8);
                                                String email = rs5.getString(9);
                                                String contNo = rs5.getString(10);

                                                AS400Libraries libs = new AS400Libraries();
                                                AS400DBConnection.setLibraries(libs.getLibraries(envr));

                                                if (approveRqst.getClick().equals("Branch")) {

                                                    System.out.println("Inside AS400----------------------" + prof);
                                                    ResultSet rs7 = connection.createStatement().executeQuery("SELECT br_code FROM admin_branch WHERE br_name='" + branchOrDeptAc + "'");

                                                    if (rs7.next()) {

                                                        System.out.println("rs7----------------------");
                                                        String brCode = rs7.getString(1);

                                                        String mkt = "N";

                                                        if (prof.contains("Marketing Officer")) {
                                                            mkt = "Y";
                                                        }

                                                        ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + prof + "' AND status='A'");

                                                        if (rs8.next()) {

                                                            String programeSigPorf = rs8.getString(1);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                                            GetSignatureUSers sigProf = new GetSignatureUSers();
                                                            String signatureProfile = sigProf.getSignatureUser(epf);
                                                            System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                                                            if (!signatureProfile.equals("")) {

                                                                AS400Programme prog = new AS400Programme();
                                                                msg = prog.programmeRun(envr, "User Transfer", signatureProfile, programeSigPorf, "", fullName, brCode, "", "", "");
                                                                System.out.println("programe done----" + msg);

                                                                if (msg.equals("1")) {
                                                                    try {

                                                                        ResultSet rs1 = connection.createStatement().executeQuery("SELECT access_code FROM admin_branch_ass_acc WHERE profile='" + prof + "'");

                                                                        if (rs1.next()) {

                                                                            String codeList = rs1.getString(1);

                                                                            Payment payObj = new Payment();
                                                                            String payment = payObj.transferPayment(epf, branchOrDeptAc, brCode);

                                                                            AccessAssign obj1 = new AccessAssign();
                                                                            boolean accessDone = obj1.accessAssignFinal(approveRqst.getRqstType(), rqstIdAc, epf, codeList, "A", brCode, fullName, branchOrDeptAc, "1");
                                                                            System.out.println("Final ok br----------------------" + accessDone);

                                                                            if (accessDone == true) {

                                                                                SignatureApprove sigp = new SignatureApprove();
                                                                                boolean rslt = sigp.signatureApprovel(prof, "Branch", signatureProfile, brCode);

                                                                                System.out.println("--------------" + rslt);

                                                                                try {

                                                                                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_branch,ou FROM admin_branch WHERE br_name='" + branchOrDeptAc + "'");
                                                                                    if (rs2.next()) {

                                                                                        String adBranch = rs2.getString(1);
                                                                                        String ou = rs2.getString(2);

                                                                                        ResultSet rs3 = connection.createStatement().executeQuery("SELECT ad_branch,ou FROM admin_branch WHERE br_name='" + oldBranchOrDept + "'");
                                                                                        if (rs3.next()) {

                                                                                            String oldAdBranch = rs3.getString(1);
                                                                                            String oldOu = rs3.getString(2);

                                                                                            AdCreation tst = new AdCreation(epf, oldOu);
                                                                                            b = tst.moveUser(ou);

                                                                                            ConfigEmail obj = new ConfigEmail();
                                                                                            String TforS = obj.tforSTransAcc(email, rqstIdAc, signatureProfile, oldBranchOrDept, fullName, oldTill, "User Transfer", approveRqst.getClick());

                                                                                            ConfigEmail obj2 = new ConfigEmail();
                                                                                            String cmnMail = obj2.comonMail(rqstId, "User Transfer", sl.getUserTransfer().get(1), branchOrDeptAc, newBranch);
                                                                                            System.out.println("----------------Else if Send");

                                                                                        }
                                                                                    }
                                                                                } catch (Exception e) {
                                                                                    e.printStackTrace();
                                                                                }

                                                                            }

                                                                        }

                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                        LOGGER.error("error : ", e);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else if (approveRqst.getClick().equals("Department")) {

                                                    String mkt = "N";
                                                    String brCode = "99999";
                                                    System.out.println("-------" + prof + "   " + brCode + "  " + branchOrDeptAc);

                                                    ResultSet rs7 = connection.createStatement().executeQuery("SELECT dept_cc FROM admin_dept WHERE dept_name='" + branchOrDeptAc + "'");
                                                    if (rs7.next()) {

                                                        String bCode = rs7.getString(1);

                                                        ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + prof + "' AND cc='" + bCode + "' AND status='A'");

                                                        if (rs8.next()) {

                                                            String programeSigPorf = rs8.getString(1);
                                                            System.out.println("programeSigPorf" + programeSigPorf);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                                            GetSignatureUSers sigProf = new GetSignatureUSers();
                                                            String signatureProfile = sigProf.getSignatureUser(epf);
                                                            System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                                                            if (!signatureProfile.equals("")) {

                                                                AS400Programme prog = new AS400Programme();
                                                                msg = prog.programmeRun(envr, "User Transfer", signatureProfile, programeSigPorf, "", fullName, brCode, "", "", "");
                                                                System.out.println("programe done----" + msg);

                                                                if (msg.equals("1")) {
                                                                    try {

                                                                        ResultSet rs10 = connection.createStatement().executeQuery("SELECT dept_cc FROM admin_dept WHERE dept_name='" + branchOrDeptAc + "'");

                                                                        if (rs10.next()) {

                                                                            String cc = rs10.getString(1);
                                                                            System.out.println("cc--------------------" + cc);
                                                                            try {

                                                                                ResultSet rs3 = connection.createStatement().executeQuery("SELECT access_code FROM admin_department_ass_acc WHERE cc='" + cc + "' AND profile='" + prof + "'");

                                                                                if (rs3.next()) {
                                                                                    String codeList = rs3.getString(1);
                                                                                    System.out.println("Code list" + codeList);

                                                                                    Payment payObj = new Payment();
                                                                                    String payment = payObj.transferPayment(epf, branchOrDeptAc, brCode);

                                                                                    AccessAssign obj1 = new AccessAssign();
                                                                                    boolean accessDone = obj1.accessAssignFinal(approveRqst.getRqstType(), rqstIdAc, epf, codeList, "A", cc, fullName, branchOrDeptAc, "1");
                                                                                    System.out.println("Final ok de----------------------");

                                                                                    if (accessDone == true) {

                                                                                        SignatureApprove sigp = new SignatureApprove();
                                                                                        boolean rslt = sigp.signatureApprovel(prof, "Department", signatureProfile, cc);

                                                                                        System.out.println("--------------" + rslt);

                                                                                        try {

                                                                                            ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_dept,ou FROM admin_dept WHERE dept_name='" + branchOrDeptAc + "'");
                                                                                            if (rs2.next()) {

                                                                                                String adDept = rs2.getString(1);
                                                                                                String ou = rs2.getString(2);
                                                                                                System.out.println("OU-----------" + ou);

                                                                                                ResultSet rs4 = connection.createStatement().executeQuery("SELECT ad_dept,ou FROM admin_dept WHERE dept_name='" + oldBranchOrDept + "'");
                                                                                                if (rs4.next()) {

                                                                                                    String oldAdBranch = rs4.getString(1);
                                                                                                    String oldOu = rs4.getString(2);
                                                                                                    System.out.println("OU-----------" + oldOu);

                                                                                                    AdCreation tst = new AdCreation(epf, oldOu);
                                                                                                    b = tst.moveUser(ou);
                                                                                                    System.out.println("resp----------" + b);

                                                                                                    ConfigEmail obj = new ConfigEmail();
                                                                                                    String TforS = obj.tforSTransAcc(email, rqstIdAc, signatureProfile, branchOrDept, fullName, newTill, "User Transfer", approveRqst.getClick());

                                                                                                    ConfigEmail obj2 = new ConfigEmail();
                                                                                                    String cmnMail = obj2.comonMail(rqstId, "User Transfer", sl.getUserTransfer().get(1), branchOrDeptAc, newBranch);
                                                                                                    System.out.println("----------------Else if Send");

                                                                                                }

                                                                                            }
                                                                                        } catch (Exception e) {
                                                                                            e.printStackTrace();
                                                                                        }

                                                                                    }

                                                                                }
                                                                            } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                                LOGGER.error("error : ", e);
                                                                            }

                                                                        }

                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                        LOGGER.error("error : ", e);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {

                                                    String mkt = "N";
                                                    String brCode = "99999";
                                                    System.out.println("-------" + prof + "   " + brCode + "  " + branchOrDeptAc);

                                                    ResultSet rs7 = connection.createStatement().executeQuery("SELECT region_cc FROM admin_region_office WHERE region_office_name='" + branchOrDeptAc + "'");
                                                    if (rs7.next()) {

                                                        String bCode = rs7.getString(1);

                                                        ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + prof + "' AND cc='Region' AND status='A'");

                                                        if (rs8.next()) {

                                                            String programeSigPorf = rs8.getString(1);
                                                            System.out.println("programeSigPorf" + programeSigPorf);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                                            GetSignatureUSers sigProf = new GetSignatureUSers();
                                                            String signatureProfile = sigProf.getSignatureUser(epf);
                                                            System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                                                            if (!signatureProfile.equals("")) {

                                                                AS400Programme prog = new AS400Programme();
                                                                msg = prog.programmeRun(envr, "User Transfer", signatureProfile, programeSigPorf, "", fullName, brCode, "", "", "");
                                                                System.out.println("programe done----" + msg);

                                                                if (msg.equals("1")) {
                                                                    try {

                                                                        ResultSet rs10 = connection.createStatement().executeQuery("SELECT region_cc FROM admin_region_office WHERE region_office_name='" + branchOrDeptAc + "'");

                                                                        if (rs10.next()) {

                                                                            String cc = rs10.getString(1);
                                                                            System.out.println("cc--------------------" + cc);
                                                                            try {

                                                                                ResultSet rs3 = connection.createStatement().executeQuery("SELECT access_code FROM admin_region_office_access WHERE profile='" + prof + "'");

                                                                                if (rs3.next()) {
                                                                                    String codeList = rs3.getString(1);
                                                                                    System.out.println("Code list" + codeList);

                                                                                    Payment payObj = new Payment();
                                                                                    String payment = payObj.transferPayment(epf, branchOrDeptAc, brCode);

                                                                                    AccessAssign obj1 = new AccessAssign();
                                                                                    boolean accessDone = obj1.accessAssignFinal(approveRqst.getRqstType(), rqstIdAc, epf, codeList, "A", cc, fullName, branchOrDeptAc, "1");
                                                                                    System.out.println("Final ok de----------------------");

                                                                                    if (accessDone == true) {

                                                                                        SignatureApprove sigp = new SignatureApprove();
                                                                                        boolean rslt = sigp.signatureApprovel(prof, "Region", signatureProfile, cc);

                                                                                        System.out.println("--------------" + rslt);

                                                                                        try {

                                                                                            ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_region,ou FROM admin_region_office WHERE region_office_name='" + branchOrDept + "'");
                                                                                            if (rs2.next()) {

                                                                                                String adrig = rs2.getString(1);
                                                                                                String ou = rs2.getString(2);

                                                                                                ResultSet rs4 = connection.createStatement().executeQuery("SELECT ad_region,ou FROM admin_region_office WHERE region_office_name='" + oldBranchOrDept + "'");
                                                                                                if (rs4.next()) {

                                                                                                    String oldAdBranch = rs4.getString(1);
                                                                                                    String oldOu = rs4.getString(2);

                                                                                                    AdCreation tst = new AdCreation(epf, oldOu);
                                                                                                    b = tst.moveUser(ou);
                                                                                                    System.out.println("resp----------" + b);

                                                                                                    ConfigEmail obj = new ConfigEmail();
                                                                                                    String TforS = obj.tforSTransAcc(email, rqstIdAc, signatureProfile, branchOrDept, fullName, newTill, "User Transfer", approveRqst.getClick());

                                                                                                    ConfigEmail obj2 = new ConfigEmail();
                                                                                                    String cmnMail = obj2.comonMail(rqstId, "User Transfer", sl.getUserTransfer().get(1), branchOrDeptAc, newBranch);
                                                                                                    System.out.println("----------------Else if Send");

                                                                                                }

                                                                                            }
                                                                                        } catch (Exception e) {
                                                                                            e.printStackTrace();
                                                                                        }

                                                                                    }

                                                                                }
                                                                            } catch (Exception e) {
                                                                                e.printStackTrace();
                                                                                LOGGER.error("error : ", e);
                                                                            }

                                                                        }

                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                        LOGGER.error("error : ", e);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                }

                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            LOGGER.error("error : ", e);
                                        }

                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    LOGGER.error("error : ", e);
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("error : ", e);
                    }

                } else {
                    ConfigEmail obj1 = new ConfigEmail();
                    String cmnMail = obj1.comonMail(rqstId, "User Transfer", sl.getUserTransfer().get(1), branchOrDept, newBranch);
                    System.out.println("----------------Else if Send");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else if (approveRqst.getRqstType().equals("User Inactivate")) {
            String rqstIdAc = "";
            String branchOrDeptAc = "";
            String bod = "";
            try {

                String sql = "update admin_user_inactivate set progress_stat=? , progress_code=? where user_id=? AND progress_code!=25";

                int result = jdbcTemplate.update(sql, sl.getUserInactivation().get(1), sl.getUserInactivation().get(0), approveRqst.getEpfNo());
                b = true;
                if (result > 0) {
                    System.out.println("A sql row has been updated.");

                    if (sl.getUserInactivation().get(0).equals("3300") || sl.getUserInactivation().get(0).equals("3700") || sl.getUserInactivation().get(0).equals("4000")) {
                        b = false;
                        String sql1 = "update admin_main_table set status=? where user_id=? and progres_stus_code!=25";

                        PreparedStatement preparedStmt = connection.prepareStatement(sql1);

                        preparedStmt.setString(1, "I");
                        preparedStmt.setString(2, approveRqst.getEpfNo());

                        preparedStmt.executeUpdate();

                        try {
                            ResultSet rs5 = connection.createStatement().executeQuery("SELECT admin_main_table.branch_or_dept , admin_main_table.rqst_id , admin_main_table.prev_br_dep , admin_main_table.prev_prof , admin_main_table.progres_stus_code , admin_main_table.user_id, admin_main_table.prev_desig, admin_main_table.password_encript, admin_user_creation_det.new_user_name, admin_user_creation_det.email_add , admin_user_creation_det.mobile_no FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det .user_id=admin_main_table.user_id where admin_main_table.progres_stus_code!=25 and admin_user_creation_det.status='A' AND admin_main_table.user_id='" + approveRqst.getEpfNo() + "'");

                            if (rs5.next()) {

                                bod = rs5.getString(1);
                                rqstIdAc = rs5.getString(2);
                                branchOrDeptAc = rs5.getString(3);
                                String prof = rs5.getString(4);
                                String statusCode = rs5.getString(5);
                                String epf = rs5.getString(6);
                                String desig = rs5.getString(7);
                                String pss = rs5.getString(8);
                                String fullName = rs5.getString(9);
                                String email = rs5.getString(10);
                                String contNo = rs5.getString(11);

                                System.out.println("Before AS400");

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                GetSignatureUSers sigProf = new GetSignatureUSers();
                                String signatureProfile = sigProf.getSignatureUser(epf);
                                System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                                if (!signatureProfile.equals("")) {

                                    AS400Programme prog = new AS400Programme();
                                    msg = prog.programmeRun(envr, "User Inactivate", signatureProfile, "", "", fullName, "", "", "", "");
                                    System.out.println("programe done----" + msg);

                                    if (msg.equals("1")) {

                                        AccessAssign obj1 = new AccessAssign();
                                        boolean accessDone = obj1.accessAssignFinal(approveRqst.getRqstType(), rqstIdAc, epf, "", "I", "", fullName, branchOrDeptAc, "0");
                                        System.out.println("Final ok----------------------");

                                        if (accessDone == true) {

                                            String sqlf = "update admin_user_creation_det set ad_status=?,status=? where user_id=? and status='A'";

                                            PreparedStatement preparedStmtf = connection.prepareStatement(sqlf);

                                            preparedStmtf.setString(1, "Disabled");
                                            preparedStmtf.setString(2, "I");
                                            preparedStmtf.setString(3, approveRqst.getEpfNo());

                                            preparedStmtf.executeUpdate();

                                            try {

                                                if (bod.equals("Branch")) {

                                                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_branch,ou FROM admin_branch WHERE br_name='" + branchOrDeptAc + "'");
                                                    if (rs2.next()) {

                                                        String adrig = rs2.getString(1);
                                                        String ou = rs2.getString(2);

                                                        AdCreation tst = new AdCreation(epf, ou);
                                                        b = tst.disableAccount();

                                                        ConfigEmail obj = new ConfigEmail();
                                                        String TforS = obj.tforSInactAcc("", rqstIdAc, signatureProfile, "", fullName, "", "User Inactivate", bod);

                                                    }
                                                } else if (bod.equals("Department")) {

                                                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_dept,ou FROM admin_dept WHERE dept_name='" + branchOrDeptAc + "'");
                                                    if (rs2.next()) {

                                                        String adrig = rs2.getString(1);
                                                        String ou = rs2.getString(2);
                                                        System.out.println("OU------" + ou);

                                                        AdCreation tst = new AdCreation(epf, ou);
                                                        b = tst.disableAccount();

                                                        ConfigEmail obj = new ConfigEmail();
                                                        String TforS = obj.tforSInactAcc("", rqstIdAc, signatureProfile, "", fullName, "", "User Inactivate", bod);

                                                    }

                                                } else {

                                                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT ad_region,ou FROM admin_region_office WHERE region_office_name='" + branchOrDeptAc + "'");
                                                    if (rs2.next()) {

                                                        String adrig = rs2.getString(1);
                                                        String ou = rs2.getString(2);
                                                        System.out.println("OU------" + ou);

                                                        AdCreation tst = new AdCreation(epf, ou);
                                                        b = tst.disableAccount();

                                                        ConfigEmail obj = new ConfigEmail();
                                                        String TforS = obj.tforSInactAcc("", rqstIdAc, signatureProfile, "", fullName, "", "User Inactivate", "Region");

                                                    }

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            LOGGER.error("error : ", e);
                        }

                    } else {
                        ConfigEmail obj1 = new ConfigEmail();
                        String cmnMail = obj1.comonMail(rqstIdAc, "User Inactivate", sl.getUserTransfer().get(1), bod, branchOrDeptAc);
                        System.out.println("----------------Else if Send");

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else if (approveRqst.getRqstType().equals("Temporary Access")) {
            String rqstId = "";
            String bod = "";
            String brOrDr = "";
            String newBr = "";
            try {

                if (approveRqst.getClick().contains("BRANCH")) {

                    bod = "Branch";
                } else {

                    bod = "Department";
                }

//                System.out.println("Insinde Tepm Access-----------------" + bod + "   " + approveRqst.getEpfNo() + "    " + sl.getTempAccess().get(1) + "    " + sl.getTempAccess().get(0));
                String sql = "update admin_temp_access set progress_stat=? , progress_code=? where user_id=? AND progress_code!=25 AND expire=?";

                int result = jdbcTemplate.update(sql, sl.getTempAccess().get(1), sl.getTempAccess().get(0), approveRqst.getEpfNo(), "Y");

                if (result > 0) {
                    System.out.println("A sql row has been updated.");

                    if (sl.getTempAccess().get(0).equals("4101") || sl.getTempAccess().get(0).equals("4400") || sl.getTempAccess().get(0).equals("4600")) {

                        ResultSet rs6 = connection.createStatement().executeQuery("SELECT new_br, new_profile, new_desig, new_till, date_from, date_to, branch_or_dept, rqst_id FROM admin_temp_access WHERE user_id='" + approveRqst.getEpfNo() + "'" + " AND expire='Y' AND progress_code!=25");

                        if (rs6.next()) {

                            newBr = rs6.getString(1);
                            String newProf = rs6.getString(2);
                            String newDesig = rs6.getString(3);
                            String newTill = rs6.getString(4);
                            String fromD = rs6.getString(5);
                            String toD = rs6.getString(6);
                            brOrDr = rs6.getString(7);
                            rqstId = rs6.getString(8);

                            try {

                                BranchDeptCode code = new BranchDeptCode();
                                String bodor = code.getBrDeptCode(brOrDr, newBr);

                                System.out.println("-----------------------------" + bodor);

                                String sql1 = "update admin_main_table set new_br=?, new_prof=?, new_desig=?, new_till=?, branch_or_dept=?, brdp_code=?  where user_id=? and progres_stus_code!=25";

//                        int result1 = jdbcTemplate.update(sql, "I", epfNo);
                                PreparedStatement preparedStmt = connection.prepareStatement(sql1);
                                preparedStmt.setString(1, newBr);
                                preparedStmt.setString(2, newProf);
                                preparedStmt.setString(3, newDesig);
                                preparedStmt.setString(4, newTill);
                                preparedStmt.setString(5, brOrDr);
                                preparedStmt.setString(6, bodor);
                                preparedStmt.setString(7, approveRqst.getEpfNo());
                                preparedStmt.executeUpdate();

                                try {
                                    ResultSet rs5 = connection.createStatement().executeQuery("SELECT admin_main_table.rqst_id , admin_main_table.new_br , admin_main_table.new_prof , admin_main_table.progres_stus_code , admin_main_table.user_id, admin_main_table.new_desig, admin_main_table.password_encript, admin_user_creation_det.new_user_name, admin_user_creation_det.email_add , admin_user_creation_det.mobile_no FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det .user_id=admin_main_table.user_id where admin_main_table.progres_stus_code!=25 and admin_user_creation_det.status='A' AND admin_main_table.user_id='" + approveRqst.getEpfNo() + "'");

                                    if (rs5.next()) {

                                        String rqstIdAc = rs5.getString(1);
                                        String branchOrDeptAc = rs5.getString(2);
                                        String prof = rs5.getString(3);
                                        String statusCode = rs5.getString(4);
                                        String epf = rs5.getString(5);
                                        String desig = rs5.getString(6);
                                        String pss = rs5.getString(7);
                                        String fullName = rs5.getString(8);
                                        String email = rs5.getString(9);
                                        String contNo = rs5.getString(10);

//                                        StringBuffer query = new StringBuffer().append("SELECT MSVMAP00.SVVGRP FROM MSVMAP00 INNER JOIN SCP001 ON SCP001.SCUSER=MSVMAP00.SVSGRP WHERE SCP001.SCUNME='" + newProf + "'");
//
//                                        try (ResultSet rs2 = AS400DBConnection.search(query.toString())) {
//
//                                            if (rs2.next()) {
//
//                                                String programeSigPorf = rs2.getString(1);
                                        System.out.println("Inside AS400----------------------" + prof);

                                        ResultSet rs7 = connection.createStatement().executeQuery("SELECT br_code FROM admin_branch WHERE br_name='" + branchOrDeptAc + "'");

                                        if (rs7.next()) {
                                            System.out.println("rs7----------------------");
                                            String brCode = rs7.getString(1);

                                            if (bod.equals("Branch")) {

                                                String mkt = "N";

                                                if (prof.contains("Marketing Officer")) {
                                                    mkt = "Y";
                                                }

                                                ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + prof + "' AND status='A'");

                                                if (rs8.next()) {

                                                    String programeSigPorf = rs8.getString(1);
                                                    System.out.println("SIgptof------------------------------------" + programeSigPorf);
/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                                    GetSignatureUSers sigProf = new GetSignatureUSers();
                                                    String signatureProfile = sigProf.getSignatureUser(epf);
                                                    System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                                                    if (!signatureProfile.equals("")) {

                                                        System.out.println("Before AS400");
                                                        AS400Programme prog = new AS400Programme();
                                                        msg = prog.programmeRun(envr, "Temperory Access", signatureProfile, programeSigPorf, "", fullName, brCode, "", "", "");
                                                        System.out.println("programe done----" + msg);

                                                        if (msg.equals("1")) {

                                                            ResultSet rs1 = connection.createStatement().executeQuery("SELECT access_code FROM admin_branch_ass_acc WHERE profile='" + prof + "'");

                                                            if (rs1.next()) {

                                                                String codeList = rs1.getString(1);

                                                                AccessAssign obj1 = new AccessAssign();
                                                                boolean asscesDone = obj1.accessAssignFinal(approveRqst.getRqstType(), rqstId, epf, codeList, "A", brCode, fullName, branchOrDeptAc, "1");
                                                                System.out.println("Final ok----------------------");

                                                                SignatureApprove sigp = new SignatureApprove();
                                                                boolean rslt = sigp.signatureApprovel(prof, "Branch", signatureProfile, brCode);
                                                                b = true;
                                                                System.out.println("--------------" + rslt);

                                                                ConfigEmail obj = new ConfigEmail();
                                                                String TforS = obj.tforStempAcc(email, rqstIdAc, signatureProfile, branchOrDeptAc, fullName, newTill, "Temporary Access", bod);

                                                                String TforSSend = obj.tforSTempUserSend(email, rqstIdAc, signatureProfile, branchOrDeptAc, fullName, newTill, "Temporary Access", bod, fromD, toD, prof);

                                                                ConfigEmail obj2 = new ConfigEmail();
                                                                String cmnMail = obj2.comonMail(rqstId, "Temporary Access", sl.getTempAccess().get(1), brOrDr, newBr);
                                                                System.out.println("----------------Else if Send");

                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (bod.equals("Department")) {

                                                String mkt = "N";
                                                brCode = "99999";

                                                ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + prof + "' AND cc='" + brCode + "' AND status='A'");

                                                if (rs8.next()) {

                                                    String programeSigPorf = rs8.getString(1);
                                                    System.out.println("SIg Prof---------" + programeSigPorf);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                                    GetSignatureUSers sigProf = new GetSignatureUSers();
                                                    String signatureProfile = sigProf.getSignatureUser(epf);
                                                    System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                                                    if (!signatureProfile.equals("")) {

                                                        System.out.println("Before AS400");
                                                        AS400Programme prog = new AS400Programme();
                                                        msg = prog.programmeRun(envr, "Temperory Access", epf, programeSigPorf, "", fullName, brCode, "", "", "");
                                                        System.out.println("programe done----" + msg);

                                                        if (msg.equals("1")) {

                                                            ResultSet rs1 = connection.createStatement().executeQuery("SELECT dept_cc FROM admin_dept WHERE dept_name='" + branchOrDeptAc + "'");

                                                            if (rs1.next()) {

                                                                String cc = rs1.getString(1);

                                                                try {

                                                                    ResultSet rs3 = connection.createStatement().executeQuery("SELECT access_code FROM admin_department_ass_acc WHERE cc='" + cc + "' AND profile='" + prof + "'");

                                                                    String codeList = rs3.getString(1);

                                                                    AccessAssign obj1 = new AccessAssign();
                                                                    boolean asscesDone = obj1.accessAssignFinal(approveRqst.getRqstType(), rqstId, epf, codeList, "A", cc, fullName, branchOrDeptAc, "1");
                                                                    System.out.println("Final ok de----------------------");

                                                                    SignatureApprove sigp = new SignatureApprove();
                                                                    boolean rslt = sigp.signatureApprovel(prof, "Department", signatureProfile, cc);
                                                                    b = true;
                                                                    System.out.println("--------------" + rslt);

                                                                    ConfigEmail obj = new ConfigEmail();
                                                                    String TforS = obj.tforS(email, rqstIdAc, signatureProfile, branchOrDeptAc, fullName, newTill, "Temporary Access", bod);

                                                                    String TforSSend = obj.tforSTempUserSend(email, rqstIdAc, signatureProfile, branchOrDeptAc, fullName, newTill, "Temporary Access", bod, fromD, toD, prof);

                                                                    ConfigEmail obj2 = new ConfigEmail();
                                                                    String cmnMail = obj2.comonMail(rqstId, "Temporary Access", sl.getTempAccess().get(1), brOrDr, newBr);
                                                                    System.out.println("----------------Else if Send");

                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                    LOGGER.error("error : ", e);
                                                                }

                                                            }

                                                        }
                                                    }
                                                }
                                            }

                                        }

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    LOGGER.error("error : ", e);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                LOGGER.error("error : ", e);
                            }
                        }
                    } else {
                        ConfigEmail obj2 = new ConfigEmail();
                        String cmnMail = obj2.comonMail(rqstId, "Temporary Access", sl.getTempAccess().get(1), brOrDr, newBr);
                        System.out.println("----------------Else if Send");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }
        if (b == false) {
            return messageSource.getMessage("approvel.approved.false", null, Locale.US);
        } else {
            return messageSource.getMessage("approvel.approved.success", null, Locale.US);
        }
    }

    @Override
    public String rejecctMessage(User approveRqst, HttpSession session) {
        StatusList sl = new StatusList();
        int result = 0;

        JsonArray userData = GetUserDetails.getUserNameAndBranch(session);
        sl = checkRejectMessage(userData.get(0).toString(), approveRqst.getClick());

        if (approveRqst.getRqstType().equals("User Grant")) {
            try {
                String status = "";
                String progStatus = "";
//                for (String list : sl.getUserCreation()) {
//                    status += list;
//                    System.out.println("Status List-----" + status);

                String sql = "update admin_main_table set progres_stus=? , progres_stus_code=? where user_id=? AND status=?";

                result = jdbcTemplate.update(sql, "Rejected", "25", approveRqst.getEpfNo(), "A");

                if (result > 0) {
                    System.out.println("A sql row has been updated.");

                }
//                }

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }
        } else if (approveRqst.getRqstType().equals("User Transfer")) {

            try {

                String sql = "update admin_user_transfers set progress_stat=? , progress_code=? where user_id=? AND progress_code!=25";

                result = jdbcTemplate.update(sql, "Rejected", "25", approveRqst.getEpfNo());

                if (result > 0) {
                    System.out.println("A sql row has been updated.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        } else if (approveRqst.getRqstType().equals("User Inactivate")) {

            try {

                String sql = "update admin_user_inactivate set progress_stat=? , progress_code=? where user_id=? AND progress_code!=25";

                result = jdbcTemplate.update(sql, "Rejected", "25", approveRqst.getEpfNo());

                if (result > 0) {
                    System.out.println("A sql row has been updated.");

                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error : ", e);
            }

        }
        if (result == 0) {
            return messageSource.getMessage("approvel.rejected.false", null, Locale.US);
        } else {
            return messageSource.getMessage("approvel.rejected.success", null, Locale.US);
        }
    }

    private StatusList checkApprovedMessage(String logId, String brnchOrDept, String userEpf, String rqstType) {

        String value = "0";
        StatusList sl = new StatusList();
        DbConn conn = new DbConn();

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT new_prof  from admin_main_table where user_id=" + logId + " AND progres_stus_code!=25");
            System.out.println("Qrydone " + rs);
            JsonArray jb = new JsonArray();
            String bod = "";
            if (rs.next()) {
                String prof = rs.getString(1);
                System.out.println("Qrydone" + prof);
                System.out.println("bod--" + brnchOrDept);

                String prevCode = "";
                try {
                    if (rqstType.equals("User Grant")) {

                        ResultSet rs1 = connection.createStatement().executeQuery("SELECT progres_stus_code  from admin_main_table where user_id='" + userEpf + "' AND progres_stus_code!=25");

                        if (rs1.next()) {
                            prevCode = rs1.getString(1);
                            bod = brnchOrDept;
                        }

                    } else if (rqstType.equals("User Transfer")) {

                        ResultSet rs1 = connection.createStatement().executeQuery("SELECT progress_code  from admin_user_transfers where user_id='" + userEpf + "' AND progress_code!=25 AND progress_stat!='Approved'");

                        if (rs1.next()) {
                            prevCode = rs1.getString(1);
                            bod = brnchOrDept;
                        }

                    } else if (rqstType.equals("User Inactivate")) {

                        ResultSet rs1 = connection.createStatement().executeQuery("SELECT progress_code  from admin_user_inactivate where user_id='" + userEpf + "' AND progress_code!=25 AND progress_stat!='Approved'");

                        if (rs1.next()) {
                            prevCode = rs1.getString(1);
                            bod = brnchOrDept;
                        }

                    } else if (rqstType.equals("Temporary Access")) {

                        ResultSet rs1 = connection.createStatement().executeQuery("SELECT progress_code  from admin_temp_access where user_id='" + userEpf + "' AND progress_code!=25 AND progress_stat!='Approved'");

                        if (rs1.next()) {
                            prevCode = rs1.getString(1);

                            if (brnchOrDept.contains("BRANCH")) {
                                System.out.println("Branch done");
                                bod = "Branch";
                            } else if (brnchOrDept.contains("REGION")) {

                                bod = "Region";
                            } else {

                                bod = "Department";
                            }

                        }

                    }
                    try {
                        System.out.println("-----------------------------------" + prof + "     " + bod + "        " + rqstType + "         " + prevCode);

                        ResultSet rs2 = connection.createStatement().executeQuery("SELECT rqst_type,approve_level,status, approved_level from admin_approal_users where profile_or_design='" + prof + "'" + " AND branch_or_dept='" + bod + "' AND rqst_type='" + rqstType + "' AND view_status='" + prevCode + "'");
                        List<String> ug = new ArrayList();
                        List<String> ut = new ArrayList();
                        List<String> ui = new ArrayList();
                        List<String> ta = new ArrayList();
                        System.out.println("-----------------------------------" + rs2);
                        while (rs2.next()) {
                            String rType = rs2.getString(1);
                            String appLevel = rs2.getString(4);
                            if (rType.equals("User Grant")) {
//                            hm.put("uc", rs2.getString(2));
                                ug.add(rs2.getString(2));
                                ug.add(rs2.getString(3));
                                sl.setUserCreation(ug);
                            } else if (rType.equals("User Transfer")) {
//                            hm.put("ut", rs2.getString(2));
                                ut.add(rs2.getString(2));
                                ut.add(rs2.getString(3));
                                sl.setUserTransfer(ut);
                            } else if (rType.equals("User Inactivate")) {
//                            hm.put("ui", rs2.getString(2));
                                ui.add(rs2.getString(2));
                                ui.add(rs2.getString(3));
                                sl.setUserInactivation(ui);
                            } else {
                                ta.add(rs2.getString(2));
                                ta.add(rs2.getString(3));
                                sl.setTempAccess(ta);
//                            hm.put("ta", rs2.getString(2));
                            }
                            String data2 = rs2.getString(2);
                            String data3 = rs2.getString(3);
                            System.out.println("data2----" + "   " + rType + "  " + data2 + "  " + data3);
                            value = data2;

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        LOGGER.error("error : ", e);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("error : ", e);
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return sl;
    }

    private StatusList checkRejectMessage(String logId, String brnchOrDept) {

        String value = "0";
        StatusList sl = new StatusList();
        DbConn conn = new DbConn();

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT prev_prof from admin_main_table where user_id=" + logId + " AND progres_stus_code!=25");
            System.out.println("Qrydone " + rs);
            JsonArray jb = new JsonArray();

            if (rs.next()) {
                String prof = rs.getString(1);
                System.out.println("Qrydone" + prof);
                System.out.println("bod--" + brnchOrDept);

                try {

                    ResultSet rs2 = connection.createStatement().executeQuery("SELECT rqst_type,reject_level,status from admin_approal_users where profile_or_design='" + prof + "'" + " and branch_or_dept='" + brnchOrDept + "'");
                    List<String> ug = new ArrayList();
                    List<String> ut = new ArrayList();
                    List<String> ui = new ArrayList();
                    List<String> ta = new ArrayList();

                    while (rs2.next()) {
                        String rType = rs2.getString(1);
                        if (rType.equals("User Grant")) {
//                            hm.put("uc", rs2.getString(2));
                            ug.add(rs2.getString(2));
                            ug.add(rs2.getString(3));
                            sl.setUserCreation(ug);
                        } else if (rType.equals("User Transfer")) {
//                            hm.put("ut", rs2.getString(2));
                            ut.add(rs2.getString(2));
                            ut.add(rs2.getString(3));
                            sl.setUserTransfer(ut);
                        } else if (rType.equals("User Inactivation")) {
//                            hm.put("ui", rs2.getString(2));
                            ui.add(rs2.getString(2));
                            ui.add(rs2.getString(3));
                            sl.setUserInactivation(ui);
                        } else {
                            ta.add(rs2.getString(2));
//                            hm.put("ta", rs2.getString(2));
                        }
                        String data2 = rs2.getString(2);
                        String data3 = rs2.getString(3);
                        System.out.println("data2----" + "   " + rType + "  " + data2 + "  " + data3);
                        value = data2;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("error : ", e);
                }

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return sl;
    }

}
