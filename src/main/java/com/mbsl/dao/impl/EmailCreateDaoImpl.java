/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.AS400Programme;
import com.mbsl.Classes.AccessAssign;
import com.mbsl.Classes.ConfigEmail;
import com.mbsl.Classes.EnvChange;
import com.mbsl.Classes.GetSignatureUSers;
import com.mbsl.Classes.SendSms;
import com.mbsl.Classes.SignatureApprove;
import com.mbsl.DbConn;
import com.mbsl.Properties;
import com.mbsl.dao.EmailCreateDao;
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
public class EmailCreateDaoImpl implements EmailCreateDao {

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

    @Override
    public List<User> getEmailToCreate() {

        DbConn conn = new DbConn();
        List<User> userLists = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT admin_main_table.rqst_id, admin_main_table.user_id, admin_main_table.new_br, admin_main_table.new_prof, admin_main_table.new_desig, admin_main_table.password_encript, admin_user_creation_det.email_add, admin_user_creation_det.new_user_name FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_user_creation_det.user_id=admin_main_table.user_id where admin_main_table.progres_stus_code!=25 AND admin_user_creation_det.status='A' AND admin_user_creation_det.ad_status='Pending' AND admin_main_table.progres_stus!='Approved'");
            while (rs.next()) {

                User users = new User();
                users.setRqstId(rs.getString(1));
                users.setEpfNo(rs.getString(2));
                users.setNewBranchOrDept(rs.getString(3));
                users.setNewProfile(rs.getString(4));
                users.setNewDesignation(rs.getString(5));
                users.setPassword(rs.getString(6));
                users.setEmailAddress(rs.getString(7));
                users.setFullName(rs.getString(8));

                userLists.add(users);

                System.out.println("bbbbbbbbbbbbbbbbbbbbbbb Method Called");
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return userLists;
    }

    @Override
    public User getDataByRqstId(User mailCreate) {

        System.out.println("-------------" + mailCreate.getEpfNo());
        List<User> userList = new ArrayList();
        User users = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT admin_main_table.rqst_id, admin_main_table.new_br, admin_main_table.password_encript, admin_user_creation_det.email_add, admin_user_creation_det.new_user_name, admin_user_creation_det.user_id FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_user_creation_det.user_id=admin_main_table.user_id where admin_main_table.progres_stus_code!=25 AND admin_user_creation_det.status='A' AND admin_user_creation_det.ad_status='Pending' AND admin_main_table.user_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mailCreate.getEpfNo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                users.setRqstId(rs.getString(1));
                users.setNewBranchOrDept(rs.getString(2));
                users.setPassword(rs.getString(3));
                users.setEmailAddress(rs.getString(4));
                users.setFullName(rs.getString(5));
                users.setEpfNo(rs.getString(6));

                userList.add(users);

                System.out.println("bbbbbbbbbbb");

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

    public String cofrmSubmition(User mailCreate) {

        System.out.println("-------------" + mailCreate.getRqstId());
        List<User> userList = new ArrayList();
        User users = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        String msg = "";
        int result1 = 0;
        try {

            String sqlmain = "update admin_user_creation_det set email_add=? where user_id=? and status=?";

            int resultn = jdbcTemplate.update(sqlmain, mailCreate.getEmailAddress(), mailCreate.getEpfNo(), "A");

            if (resultn > 0) {

                String sqlmain2 = "update admin_main_table set password_encript=? where user_id=? and status=?";

                int resultn1 = jdbcTemplate.update(sqlmain2, mailCreate.getPassword(), mailCreate.getEpfNo(), "A");

                if (resultn1 > 0) {

                    try {
                        Connection connection = dataSource.getConnection();
                        String sql = "SELECT admin_main_table.prev_till, admin_main_table.user_id, admin_main_table.branch_or_dept, admin_main_table.new_prof, admin_main_table.rqst_id, admin_main_table.new_br, admin_main_table.password_encript, admin_user_creation_det.email_add, admin_user_creation_det.new_user_name, admin_user_creation_det.mobile_no FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_user_creation_det.user_id=admin_main_table.user_id where admin_main_table.progres_stus_code!=25 AND admin_user_creation_det.status='A' AND admin_user_creation_det.ad_status='Pending' AND admin_main_table.user_id=?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, mailCreate.getEpfNo());
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            String tillNo = rs.getString(1);
                            String uId = rs.getString(2);
                            String bod = rs.getString(3);
                            String profile = rs.getString(4);
                            String rqstId = rs.getString(5);
                            String branchOrDept = rs.getString(6);
                            String pw = rs.getString(7);
                            String email = rs.getString(8);
                            String fullName = rs.getString(9);
                            String contNo = rs.getString(10);

                            System.out.println("IN side------------------------" + profile);
                            AS400Libraries libs = new AS400Libraries();
                            AS400DBConnection.setLibraries(libs.getLibraries(envr));
                            System.out.println("IN side11------------------------" + profile);

                            if (bod.equals("Branch")) {

                                ResultSet rs7 = connection.createStatement().executeQuery("SELECT br_code FROM admin_branch WHERE br_name='" + branchOrDept + "'");

                                if (rs7.next()) {

                                    String brCode = rs7.getString(1);
                                    System.out.println("br COde" + brCode);

                                    String mkt = "N";

                                    if (profile.contains("Marketing Officer")) {
                                        mkt = "Y";
                                    }

                                    ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + profile + "' AND status='A'");

                                    if (rs8.next()) {

                                        String programeSigPorf = rs8.getString(1);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                        GetSignatureUSers sigProf = new GetSignatureUSers();
                                        String signatureProfile = sigProf.setSignatureUser(uId, fullName);
                                        System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////     

                                        if (signatureProfile.equals("Success")) {

                                            System.out.println("MKT-------" + mkt);
                                            AS400Programme prog = new AS400Programme();
                                            msg = prog.programmeRun(envr, "User Grant", uId, programeSigPorf, pw, fullName, brCode, mkt, contNo, email);
                                            System.out.println("programe done----" + msg);

                                            if (msg.equals("1")) {
                                                ResultSet rs1 = connection.createStatement().executeQuery("SELECT access_code FROM admin_branch_ass_acc WHERE profile='" + profile + "'");

                                                if (rs1.next()) {

                                                    String codeList = rs1.getString(1);

                                                    AccessAssign obj1 = new AccessAssign();
                                                    boolean accessDone = obj1.accessAssignFinal("User Grant", rqstId, uId, codeList, "A", brCode, fullName, branchOrDept, "1");

                                                    if (accessDone == true) {

                                                        SignatureApprove sigp = new SignatureApprove();
                                                        boolean rslt = sigp.signatureApprovel(profile, "Branch", uId, brCode);

                                                        System.out.println("--------------" + rslt);

                                                        SendSms obj9 = new SendSms();
                                                        String smsResp = obj9.sendSms(contNo, "Hi " + fullName + ",Your one-time Password & Username for the Signature/Windows/Email systems are below. Password: " + pw);
                                                        char firstChar = smsResp.charAt(0);
                                                        System.out.println("firstChar--------------------------------" + firstChar);

                                                        if (firstChar == '0') {

                                                            String sql1 = "update admin_user_creation_det set ad_status=?,sms_send_stat=? WHERE user_id=? AND status=?";

                                                            int result = jdbcTemplate.update(sql1, "Created", "Sent", uId, "A");

                                                            if (result > 0) {

                                                                String sql2 = "update admin_main_table set progres_stus_code=?, progres_stus=? WHERE user_id=? AND status=? AND progres_stus_code!=25";

                                                                result1 = jdbcTemplate.update(sql2, "901", "Approved", uId, "A");

                                                                System.out.println("-----------" + email + "    " + rqstId + "     " + signatureProfile + "    " + branchOrDept + "    " + fullName + "    " + tillNo + "   " + bod);

                                                                ConfigEmail obj = new ConfigEmail();
                                                                String TforS = obj.tforS(email, rqstId, uId, branchOrDept, fullName, tillNo, "User Grant", bod);

                                                            }

                                                        }
                                                    }

                                                    System.out.println("Wright");
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (bod.equals("Department")) {

                                String mkt = "N";
                                String brCode = "99999";

                                ResultSet rs7 = connection.createStatement().executeQuery("SELECT dept_cc FROM admin_dept WHERE dept_name='" + branchOrDept + "'");
                                if (rs7.next()) {

                                    String bCode = rs7.getString(1);

                                    ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + profile + "' AND cc='" + bCode + "' AND status='A'");

                                    if (rs8.next()) {

                                        String programeSigPorf = rs8.getString(1);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                        GetSignatureUSers sigProf = new GetSignatureUSers();
                                        String signatureProfile = sigProf.setSignatureUser(uId, fullName);
                                        System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                                        if (signatureProfile.equals("Success")) {

                                            AS400Programme prog = new AS400Programme();
                                            msg = prog.programmeRun(envr, "User Grant", uId, programeSigPorf, pw, fullName, brCode, mkt, contNo, email);
                                            System.out.println("programe done----" + msg);

                                            if (msg.equals("1")) {

                                                ResultSet rs1 = connection.createStatement().executeQuery("SELECT dept_cc FROM admin_dept WHERE dept_name='" + branchOrDept + "'");

                                                if (rs1.next()) {

                                                    String cc = rs1.getString(1);
                                                    System.out.println("----------------------" + cc + "-----------" + profile);
                                                    try {

                                                        ResultSet rs3 = connection.createStatement().executeQuery("SELECT access_code FROM admin_department_ass_acc WHERE cc=" + cc + " AND profile='" + profile + "'");

                                                        if (rs3.next()) {
                                                            String codeList = rs3.getString(1);

                                                            AccessAssign obj1 = new AccessAssign();
                                                            boolean accessDone = obj1.accessAssignFinal("User Grant", rqstId, uId, codeList, "A", cc, fullName, branchOrDept, "1");
                                                            if (accessDone == true) {

                                                                SignatureApprove sigp = new SignatureApprove();
                                                                boolean rslt = sigp.signatureApprovel(profile, "Department", uId, cc);

                                                                System.out.println("--------------" + rslt);

                                                                SendSms obj9 = new SendSms();
                                                                System.out.println("Cont Num-------------" + contNo);
                                                                String smsResp = obj9.sendSms(contNo, "Hi " + fullName + ",Your one-time Password & Username for the Signature/Windows/Email systems are below. Password: " + pw);
                                                                char firstChar = smsResp.charAt(0);
                                                                System.out.println("firstChar--------------------------------" + firstChar);

                                                                if (firstChar == '0') {

                                                                    String sql1 = "update admin_user_creation_det set ad_status=?,sms_send_stat=? WHERE user_id=? AND status=?";

                                                                    int result = jdbcTemplate.update(sql1, "Created", "Sent", uId, "A");

                                                                    if (result > 0) {

                                                                        String sql2 = "update admin_main_table set progres_stus_code=?, progres_stus=? WHERE user_id=? AND status=? AND progres_stus_code!=25";

                                                                        result1 = jdbcTemplate.update(sql2, "401", "Approved", uId, "A");

                                                                        ConfigEmail obj = new ConfigEmail();
                                                                        String TforS = obj.tforS(email, rqstId, uId, branchOrDept, fullName, tillNo, "User Grant", bod);

                                                                    }

                                                                }

                                                            }

                                                            System.out.println("Wright Department");
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
                            } else {

                                String mkt = "N";
                                String brCode = "99999";

                                ResultSet rs7 = connection.createStatement().executeQuery("SELECT region_cc FROM admin_region_office WHERE region_office_name='" + branchOrDept + "'");
                                if (rs7.next()) {

                                    String bCode = rs7.getString(1);

                                    ResultSet rs8 = connection.createStatement().executeQuery("SELECT velocity_group FROM admin_config_profile WHERE profile='" + profile + "' AND cc='Region' AND status='A'");

                                    if (rs8.next()) {

                                        String programeSigPorf = rs8.getString(1);

/////////////////////////////////////////SIGNATURE USER////////////////////////////////                                               
                                        GetSignatureUSers sigProf = new GetSignatureUSers();
                                        String signatureProfile = sigProf.setSignatureUser(uId, fullName);
                                        System.out.println("signatureProfile--------" + signatureProfile);
//////////////////////////////////////////////////////////////////////////////////////////////    

                                        if (signatureProfile.equals("Success")) {

                                            AS400Programme prog = new AS400Programme();
                                            msg = prog.programmeRun(envr, "User Grant", uId, programeSigPorf, pw, fullName, brCode, mkt, contNo, email);
                                            System.out.println("programe done----" + msg);
                                            if (msg.equals("1")) {

                                                ResultSet rs1 = connection.createStatement().executeQuery("SELECT region_cc FROM admin_region_office WHERE region_office_name='" + branchOrDept + "'");

                                                if (rs1.next()) {

                                                    String cc = rs1.getString(1);
                                                    System.out.println("----------------------" + cc + "-----------" + profile);
                                                    try {

                                                        ResultSet rs3 = connection.createStatement().executeQuery("SELECT access_code FROM admin_region_office_access WHERE profile='" + profile + "'");

                                                        if (rs3.next()) {
                                                            String codeList = rs3.getString(1);

                                                            AccessAssign obj1 = new AccessAssign();
                                                            boolean accessDone = obj1.accessAssignFinal("User Grant", rqstId, uId, codeList, "A", cc, fullName, branchOrDept, "1");
                                                            if (accessDone == true) {

                                                                SignatureApprove sigp = new SignatureApprove();
                                                                boolean rslt = sigp.signatureApprovel(profile, "Region", uId, cc);

                                                                System.out.println("--------------" + rslt);

                                                                SendSms obj9 = new SendSms();
                                                                System.out.println("Cont Num-------------" + contNo);
                                                                String smsResp = obj9.sendSms(contNo, "Hi " + fullName + ",Your one-time Password & Username for the Signature/Windows/Email systems are below. Password: " + pw);
                                                                char firstChar = smsResp.charAt(0);
                                                                System.out.println("firstChar--------------------------------" + firstChar);

                                                                if (firstChar == '0') {

                                                                    String sql1 = "update admin_user_creation_det set ad_status=?,sms_send_stat=? WHERE user_id=? AND status=?";

                                                                    int result = jdbcTemplate.update(sql1, "Created", "Sent", uId, "A");

                                                                    if (result > 0) {

                                                                        String sql2 = "update admin_main_table set progres_stus_code=? WHERE user_id=? AND status=? AND progres_stus_code!=25";

                                                                        result1 = jdbcTemplate.update(sql2, "1300", uId, "A");
                                                                    }

                                                                }

                                                            }

                                                            System.out.println("Wright Department");
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

                        }
                        LOGGER.info("sucess");
                        connection.close();
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

        if (result1 == 0) {
            return messageSource.getMessage("approvel.emailcreate.fail", null, Locale.US);
        } else {
            return messageSource.getMessage("approvel.emailcreate.success", null, Locale.US);
        }
    }

}
