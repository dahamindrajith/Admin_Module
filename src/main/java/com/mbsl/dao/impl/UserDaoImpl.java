/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.AS400Programme;
import com.mbsl.Classes.AdCreation;
import com.mbsl.Classes.BranchDeptCode;
import com.mbsl.Classes.ConfigEmail;
import com.mbsl.Classes.EnvChange;
import com.mbsl.Classes.RequestIdGenerator;
import com.mbsl.Classes.SendSms;
import com.mbsl.Classes.securityManager;
import com.mbsl.DbConn;
import com.mbsl.Properties;
import com.mbsl.dao.UserDao;
import com.mbsl.model.User;
import com.mbsl.model.StatusList;
import com.mbsl.velocity.as400.environment.AS400Libraries;
import com.mbsl.velocity.dal.as400.AS400DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MBSL2395
 */
@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

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

// save user--------------------------------------------------------------------
    @Override
    public String createUser(User user) {
        System.out.println("userrrrrrrrrrrrrrrrrrrrr" + envr);
        System.out.println("Click" + user.getClick());
        String bod = "";

        if (user.getClick().trim().equals("Department")) {
            bod = "50";
        } else if (user.getClick().trim().equals("Branch")) {
            bod = "450";
        } else if (user.getClick().trim().equals("Region")) {
            bod = "950";
        }

        String tilz = user.getCurrentTill().trim().toString();

        if (user.getCurrentTill().trim().equals("") || user.getCurrentTill().trim().equals(null)) {
            tilz = "0";
        }

        securityManager obj = new securityManager();
        String passwrd = obj.passwordG();
        String availability = checkAvailability(user.getEpfNo());
        System.out.println("Availability" + availability);
        int epfLength = user.getEpfNo().length();

        try {

            if (availability.equals("0")) {

                RequestIdGenerator objRqst = new RequestIdGenerator();
                String RqstID = objRqst.generateRequestId("1");

                BranchDeptCode code = new BranchDeptCode();
                String bodor = code.getBrDeptCode(user.getClick().trim(), user.getBranchorDept().trim());

                String sql = "INSERT INTO admin_main_table(rqst_id, user_id, epf_no, prev_br_dep, new_br, prev_prof, new_prof, prev_desig, new_desig, prev_till, new_till, branch_or_dept,password_encript, req_type, status, progres_stus, progres_stus_code, brdp_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                int result = jdbcTemplate.update(sql, RqstID, user.getEpfNo(), user.getEpfNo().substring(4, epfLength), user.getBranchorDept().trim(), user.getBranchorDept().trim(), user.getProfile().trim(), user.getProfile().trim(), user.getDesignation().trim(), user.getDesignation().trim(), tilz, tilz, user.getClick().trim(), user.getPassword(), "User Grant", "A", "Pending", bod, bodor);
                System.out.println(user.getBranchorDept());
                if (result > 0) {
                    System.out.println("A sql row has been inserted.");
                }

                try {
                    String sql2 = "INSERT INTO admin_user_creation_det(user_id, bank_code, new_user_name, email_add, mobile_no, status) VALUES (?, ?, ?, ?, ?, ?)";

                    int result2 = jdbcTemplate.update(sql2, user.getEpfNo(), "MBSL", user.getFullName(), user.getEmailAddress(), user.getMobileNo(), "A");

                    if (result2 > 0) {

                        System.out.println("A new row has been inserted." + user.getMobileNo());

                        ConfigEmail obj1 = new ConfigEmail();
                        String cmnMail = obj1.comonMail(RqstID, "User Grant", "Pending", user.getClick().trim(), user.getBranchorDept().trim());

                    }

                    LOGGER.info("sucess");
                } catch (Exception e) {
                    LOGGER.error("error : ", e);
                }

                LOGGER.info("sucess");
            }
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }

//        AdCreation ad = new AdCreation("MBSL" + user.getEpfNo(), user.getFullName(), user.getFullName(), user.getPassword(), "Test");
//        System.out.println("Return AD-------"+ad);
        if (availability.equals("1")) {
            return messageSource.getMessage("user.create.already", null, Locale.US);
        } else {
            return messageSource.getMessage("user.create.success", null, Locale.US);
        }
    }

    private String checkAvailability(String epf) {
        DbConn conn = new DbConn();
        String setType = "'" + epf + "'";
        String value = "0";

        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT user_id FROM admin_main_table  where progres_stus_code!=25 and user_id=" + setType);
            System.out.println("Qrydone " + rs);
            if (rs.next()) {

                value = "1";

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        return value;

    }

// Updaet User------------------------------------------------------------------
    @Override
    public String updateUser(User user) {
        System.out.println(user.getEpfNo() + "Done");

        String bod = "";

        if (user.getClick().trim().equals("Department")) {
            bod = "50";
        } else if (user.getClick().trim().equals("Branch")) {
            bod = "450";
        } else if (user.getClick().trim().equals("Region Branch")) {
            bod = "950";
        }

        try {
            String sql = "update admin_main_table set prev_br_dep=?, new_br=?, prev_prof=?, new_prof=?, prev_desig=?, new_desig=?, prev_till=?, new_till=?, branch_or_dept=? , progres_stus=?, progres_stus_code=? where progres_stus_code!=? and user_id=?";

            int result = jdbcTemplate.update(sql, user.getBranchorDept().trim(), user.getBranchorDept().trim(), user.getProfile().trim(), user.getProfile().trim(), user.getDesignation().trim(), user.getDesignation().trim(), user.getCurrentTill().trim(), user.getCurrentTill().trim(), user.getClick().trim(), "Pending", bod, "25", user.getEpfNo());

            if (result > 0) {
                System.out.println("A sql row has been inserted.");
            }

            try {
                String sql2 = "update admin_user_creation_det set new_user_name=? , email_add=? , mobile_no=? where user_id=?";

                int result2 = jdbcTemplate.update(sql2, user.getFullName(), user.getEmailAddress(), user.getMobileNo(), user.getEpfNo());

                if (result2 > 0) {
                    System.out.println("A new row has been inserted.");
                }

                LOGGER.info("sucess");
            } catch (Exception e) {
                LOGGER.error("error : ", e);
            }

            System.out.println(result);
            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("user.update.success", null, Locale.US);
    }

// delete added pending record--------------------------------------------------
    @Override
    public String deleteUser(User user) {
        System.out.println("22222222222222222222222222222222" + user.getEpfNo());

        try {
            String sql2 = "update admin_main_table set progres_stus_code=? , status=? where user_id=?";

            int result2 = jdbcTemplate.update(sql2, "25", "I", user.getEpfNo());

            if (result2 > 0) {
                System.out.println("A new row has been inserted.");
            }

            try {
                String sql3 = "update admin_user_creation_det set status=? where user_id=?";

                int result3 = jdbcTemplate.update(sql3, "I", user.getEpfNo());

                if (result2 > 0) {
                    System.out.println("A new row has been inserted.");
                }

                LOGGER.info("sucess");
            } catch (Exception e) {
                LOGGER.error("error : ", e);
            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("user.delete.success", null, Locale.US);
    }

// Get added User List----------------------------------------------------------
    @Override
    public List<User> getUsers() {

        DbConn conn = new DbConn();
        List<User> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT admin_main_table.rqst_id, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig,  admin_main_table.prev_till, admin_main_table.branch_or_dept, admin_main_table.password_encript, admin_main_table.progres_stus, admin_user_creation_det.new_user_name, admin_user_creation_det.email_add, admin_user_creation_det.mobile_no FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det .user_id=admin_main_table.user_id where admin_main_table.progres_stus_code!=25 and admin_user_creation_det.status='A' AND admin_main_table.progres_stus!='Approved' ORDER BY admin_main_table.id DESC LIMIT 30");
            while (rs.next()) {

                User user = new User();
                user.setRqstId(rs.getString(1));
                user.setEpfNo(rs.getString(2));
                user.setBranchorDept(rs.getString(3));
                user.setProfile(rs.getString(4));
                user.setDesignation(rs.getString(5));
                user.setCurrentTill(rs.getString(6));
                user.setClick(rs.getString(7));
                user.setPassword(rs.getString(8));
                user.setStatus(rs.getString(9));
                user.setFullName(rs.getString(10));
                user.setEmailAddress(rs.getString(11));
                user.setMobileNo(rs.getString(12));

                userList.add(user);

//                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb" + userList.get(0).getClick());
            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return userList;
    }

// Click View button in Create User---------------------------------------------
    @Override
    public User getListById(User user) {

        System.out.println("-------------" + user.getEpfNo());
        List<User> userList = new ArrayList();
        User users = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT admin_user_creation_det.new_user_name, admin_user_creation_det.email_add, admin_user_creation_det.mobile_no, admin_main_table.rqst_id, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till, admin_main_table.branch_or_dept, admin_main_table.password_encript FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_main_table .user_id=admin_user_creation_det.user_id where admin_main_table .user_id=? AND admin_main_table.progres_stus_code!=25";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEpfNo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                users.setFullName(rs.getString(1));
                users.setEmailAddress(rs.getString(2));
                users.setMobileNo(rs.getString(3));
                users.setRqstId(rs.getString(4));
                users.setEpfNo(rs.getString(5));
                users.setBranchorDept(rs.getString(6));
                users.setProfile(rs.getString(7));
                users.setDesignation(rs.getString(8));
                users.setCurrentTill(rs.getString(9));
                users.setClick(rs.getString(10));
                users.setPassword(rs.getString(11));

                userList.add(users);

                System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return users;
    }

    @Override
    public User getAppInfoC(User user) {

        System.out.println("-------------" + user.getEpfNo() + "   " + user.getRqstType() + "   " + user.getStatus() + "    " + user.getBranchorDept());
        List<User> userList = new ArrayList();
        User users = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT branch_or_dept FROM admin_main_table WHERE user_id=? AND progres_stus_code!=25";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEpfNo());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String bod = rs.getString(1);
                System.out.println("--------" + bod);
                String sql2 = "SELECT profile_or_design, bod_name FROM admin_approal_users WHERE branch_or_dept=? AND rqst_type=? AND show_info_stts=?";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setString(1, bod.trim());
                ps2.setString(2, user.getRqstType().trim());
                ps2.setString(3, user.getStatus().trim());
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {

                    String profOrDesig = rs2.getString(1);
                    String branchOrDept = rs2.getString(2);

                    if (branchOrDept.equals("AB") && profOrDesig.equals("Region Manager")) {
                        String sql4 = "SELECT admin_region_office.region_office_name FROM admin_region_office INNER JOIN admin_branch ON admin_branch.region_unit=admin_region_office.region_office WHERE admin_branch.br_name=?";
                        PreparedStatement ps4 = connection.prepareStatement(sql4);
                        ps4.setString(1, user.getBranchorDept());
                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            branchOrDept = rs4.getString(1);
                        }

                    } else if (branchOrDept.equals("AB")) {
                        branchOrDept = user.getBranchorDept();
                    }

                    System.out.println("-----------" + profOrDesig + "         " + branchOrDept);
                    String sql3 = "SELECT admin_user_creation_det.new_user_name FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det.user_id=admin_main_table.user_id WHERE admin_main_table.new_br=? AND admin_main_table.new_prof=? AND admin_main_table.progres_stus_code!=25";
                    PreparedStatement ps3 = connection.prepareStatement(sql3);
                    ps3.setString(1, branchOrDept.trim());
                    ps3.setString(2, profOrDesig.trim());

                    ResultSet rs3 = ps3.executeQuery();

                    if (rs3.next()) {

                        users.setFullName(rs3.getString(1));
                        System.out.println("-------------"+rs3.getString(1));

                    }
                }
            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return users;
    }

    @Override
    public List<User> getAllBranchOrDept(User user) {

        List<User> userList = new ArrayList();
        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));
        String sql = " ";
        JsonArray ja = new JsonArray();
        System.out.println("------------------------" + user.getClick());

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

                user1.setBranchorDept(rs.getString(1).trim());
                userList.add(user1);

            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);

        }
        System.out.println("@return--" + userList.get(0));
        return userList;
    }
}
