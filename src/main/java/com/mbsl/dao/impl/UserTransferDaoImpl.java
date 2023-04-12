/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mbsl.dao.impl;

import com.google.gson.JsonArray;
import com.mbsl.Classes.AS400Programme;
import com.mbsl.Classes.ConfigEmail;
import com.mbsl.Classes.EnvChange;
import com.mbsl.Classes.RequestIdGenerator;
import com.mbsl.Classes.hrSpecialApprove;
import com.mbsl.DbConn;
import com.mbsl.Properties;
import com.mbsl.dao.UserTransferDao;
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
public class UserTransferDaoImpl implements UserTransferDao {

    private static final Logger LOGGER = LogManager.getLogger(UserTransferDaoImpl.class);

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
    public String transferUser(User transferUser) {

        DbConn conn = new DbConn();
        Connection connection = conn.getConn();
        String bod = "";

        if (transferUser.getClick().trim().equals("Department")) {
            bod = "1350";
        } else if (transferUser.getClick().trim().equals("Branch")) {
            bod = "1750";
        } else if (transferUser.getClick().trim().equals("Region")) {
            bod = "2250";
        } else if (transferUser.getClick().trim().equals("Region Branch 1")) {
            bod = "2650";
        }
        System.out.println("`aaaaaaaaaaaaaaaaaaaaaa" + bod);
        String availability = checkAvailability(transferUser.getEpfNo());

        System.out.println("Available----" + availability + "   ");

        try {
            if (availability.equals("2")) {

                RequestIdGenerator objRqst = new RequestIdGenerator();
                String RqstID = objRqst.generateRequestId("2");

                String sql = "INSERT INTO admin_user_transfers(req_id, user_id, new_br, new_profile, new_desig, new_till, req_type, branch_or_dept, progress_stat, progress_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                int result = jdbcTemplate.update(sql, RqstID, transferUser.getEpfNo(), transferUser.getNewBranchOrDept().trim(), transferUser.getNewProfile().trim(), transferUser.getNewDesignation().trim(), transferUser.getNewTill().trim(), "User Transfer", transferUser.getClick().trim(), "Pending", bod);

                if (result > 0) {
                    System.out.println("A sql row has been inserted.");

                    ConfigEmail obj1 = new ConfigEmail();
                    String cmnMail = obj1.comonMail(RqstID, "User Transfer", "Pending", transferUser.getClick().trim(), transferUser.getBranchorDept().trim());
                }
                System.out.println(result);
                LOGGER.info("sucess");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        if (availability.equals("1")) {
            return messageSource.getMessage("user.transfer.alreadytransfer", null, Locale.US);
        } else if (availability.equals("2")) {
            return messageSource.getMessage("user.transfer.success", null, Locale.US);
        } else {
            return messageSource.getMessage("user.transfer.notavailable", null, Locale.US);
        }
    }

    private String checkAvailability(String epf) {
        DbConn conn = new DbConn();
        String setType = "'" + epf + "'";
        String value = "0";
        int idCount = 0;
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT user_id FROM admin_main_table  WHERE progres_stus_code IN ('401','901','1301') AND user_id=" + setType);
            System.out.println("Qrydone ");
            if (rs.next()) {
                ResultSet rs2 = connection.createStatement().executeQuery("SELECT user_id FROM admin_user_transfers WHERE user_id=" + setType);
                ResultSet rs1 = connection.createStatement().executeQuery("SELECT user_id FROM admin_user_transfers  WHERE progress_code IN ('25','1700','2200','2600','3000') AND user_id=" + setType);
                ResultSet rs3 = connection.createStatement().executeQuery("SELECT COUNT(user_id) AS c FROM admin_user_transfers  WHERE progress_code IN ('1350','1750','2250','2650') AND user_id=" + setType);
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
        System.out.println("value trans---" + value);
        return value;

    }

    @Override
    public List<User> getTransferUsers() {
        DbConn conn = new DbConn();
        List<User> userList = new ArrayList();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {

            Connection connection = conn.getConn();
            ResultSet rs = connection.createStatement().executeQuery("SELECT admin_user_creation_det.new_user_name, admin_user_transfers.req_id, admin_user_transfers.user_id, admin_user_transfers.new_br, admin_user_transfers.new_profile, admin_user_transfers.new_desig, admin_user_transfers.new_till, admin_user_transfers.progress_stat, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till FROM admin_user_transfers INNER JOIN admin_main_table ON admin_user_transfers.user_id=admin_main_table.user_id INNER JOIN admin_user_creation_det ON admin_main_table.user_id=admin_user_creation_det.user_id where admin_user_transfers.progress_code!=25 and admin_user_transfers.progress_code!=1700 and admin_user_transfers.progress_code!=2200 and admin_user_transfers.progress_code!=2600 and admin_user_transfers.progress_code!=3000 and admin_main_table.progres_stus_code!=25 and admin_user_creation_det.status='A'");
            while (rs.next()) {

                User user = new User();
                user.setFullName(rs.getString(1));
                user.setRqstId(rs.getString(2));
                user.setEpfNo(rs.getString(3));
                user.setNewBranchOrDept(rs.getString(4));
                user.setNewProfile(rs.getString(5));
                user.setNewDesignation(rs.getString(6));
                user.setNewTill(rs.getString(7));
                user.setStatus(rs.getString(8));
                user.setBranchorDept(rs.getString(9));
                user.setProfile(rs.getString(10));
                user.setDesignation(rs.getString(11));
                user.setCurrentTill(rs.getString(12));

                userList.add(user);

                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + userList.get(0).getEpfNo());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return userList;
    }

    @Override
    public User getListTransferById(User transferUser) {
        System.out.println("getListTransferById Done");
        List<User> userList = new ArrayList();
        User user = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();

            String sql = "SELECT admin_user_creation_det.new_user_name, admin_main_table.user_id, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till FROM admin_main_table INNER JOIN admin_user_creation_det ON admin_main_table.user_id=admin_user_creation_det.user_id where admin_main_table.user_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, transferUser.getEpfNo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user.setFullName(rs.getString(1));
                user.setEpfNo(transferUser.getEpfNo());
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
    public String updateTransferUser(User transferUser) {
        System.out.println(transferUser.getEpfNo() + "Done");

        String bod = "";

        if (transferUser.getClick().trim().equals("Department")) {
            bod = "1350";
        } else if (transferUser.getClick().trim().equals("Branch")) {
            bod = "1750";
        } else if (transferUser.getClick().trim().equals("Region Branch")) {
            bod = "2250";
        } else if (transferUser.getClick().trim().equals("Region Branch 1")) {
            bod = "2650";
        }

        try {
            String sql = "update admin_user_transfers set new_br=? , new_profile=? , new_desig=? , new_till=? , branch_or_dept=? , progress_stat=?, progress_code=? where progress_code!=? and user_id=?";

            int result = jdbcTemplate.update(sql, transferUser.getNewBranchOrDept().trim(), transferUser.getNewProfile().trim(), transferUser.getNewDesignation().trim(), transferUser.getNewTill().trim(), transferUser.getClick().trim(), "Pending", bod, "25", transferUser.getEpfNo());

            if (result > 0) {
                System.out.println("A sql row has been inserted.");
            }

            System.out.println(result);
            LOGGER.info("sucess");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("user.transferupdate.success", null, Locale.US);
    }

    @Override
    public String deleteTransferUser(User transferUser) {
        System.out.println("334" + transferUser.getEpfNo());

        try {
            String sql2 = "update admin_user_transfers set progress_code=? where user_id=?";

            int result2 = jdbcTemplate.update(sql2, "25", transferUser.getEpfNo());

            if (result2 > 0) {
                System.out.println("A new row has been inserted.");
            }

            LOGGER.info("sucess");
        } catch (Exception e) {
            LOGGER.error("error : ", e);
        }

        return messageSource.getMessage("user.transferdelete.success", null, Locale.US);
    }

    @Override
    public List<User> getAllBranchOrDept(User user) {

        List<User> userList = new ArrayList();
        AS400Libraries libs = new AS400Libraries();
        AS400DBConnection.setLibraries(libs.getLibraries(envr));
        String sql = " ";
        System.out.println("---------------" + user.getClick());
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
        System.out.println("@return--" + userList.get(0));
        return userList;
    }

    @Override
    public User getToButtonListTransferById(User transferUser) {
        System.out.println("getToButtonListTransferById Done");
        List<User> userList = new ArrayList();
        User user = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();

            String sql = "SELECT admin_user_creation_det.new_user_name, admin_user_transfers.req_id, admin_user_transfers.user_id, admin_user_transfers.new_br, admin_user_transfers.new_profile, admin_user_transfers.new_desig, admin_user_transfers.new_till, admin_main_table.prev_br_dep, admin_main_table.prev_prof, admin_main_table.prev_desig, admin_main_table.prev_till FROM admin_user_transfers INNER JOIN admin_main_table ON admin_user_transfers.user_id=admin_main_table.user_id INNER JOIN admin_user_creation_det ON admin_main_table.user_id=admin_user_creation_det.user_id where admin_main_table.user_id=? AND admin_user_transfers.progress_code!=25";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, transferUser.getEpfNo());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user.setFullName(rs.getString(1).trim());
                user.setRqstId(rs.getString(2).trim());
                user.setEpfNo(rs.getString(3).trim());
                user.setNewBranchOrDept(rs.getString(4).trim());
                user.setNewProfile(rs.getString(5).trim());
                user.setNewDesignation(rs.getString(6).trim());
                user.setNewTill(rs.getString(7).trim());
                user.setBranchorDept(rs.getString(8).trim());
                user.setProfile(rs.getString(9).trim());
                user.setDesignation(rs.getString(10).trim());
                user.setCurrentTill(rs.getString(11).trim());

                userList.add(user);

                System.out.println("bbbbbbbbbbb" + userList.get(0).getEpfNo());

            }
            LOGGER.info("sucess");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error : ", e);
        }
        //outPut.add(ja);
        return user;
    }

    @Override
    public User getAppInfoT(User transferUser) {

        System.out.println("-------------" + transferUser.getEpfNo() + "   " + transferUser.getRqstType() + "   " + transferUser.getStatus() + "    " + transferUser.getBranchorDept());

        User transfer = new User();
        String accessModel;
        JsonArray ja = new JsonArray();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "SELECT branch_or_dept FROM admin_user_transfers WHERE user_id=? AND progress_code!=25";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, transferUser.getEpfNo());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String bod = rs.getString(1);
                System.out.println("--------" + bod);
                String sql2 = "SELECT profile_or_design, bod_name FROM admin_approal_users WHERE branch_or_dept=? AND rqst_type=? AND show_info_stts=?";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setString(1, bod.trim());
                ps2.setString(2, transferUser.getRqstType().trim());
                ps2.setString(3, transferUser.getStatus().trim());
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {

                    String profOrDesig = rs2.getString(1);
                    String branchOrDept = rs2.getString(2);

                    if (branchOrDept.equals("AB") && profOrDesig.equals("Region Manager")) {
                        String sql4 = "SELECT admin_region_office.region_office_name FROM admin_region_office INNER JOIN admin_branch ON admin_branch.region_unit=admin_region_office.region_office WHERE admin_branch.br_name=?";
                        PreparedStatement ps4 = connection.prepareStatement(sql4);
                        ps4.setString(1, transferUser.getBranchorDept());
                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            branchOrDept = rs4.getString(1);
                        }

                    } else if (branchOrDept.equals("AB")) {
                        branchOrDept = transferUser.getBranchorDept();
                    }

                    System.out.println("-----------" + profOrDesig + "         " + branchOrDept);
                    String sql3 = "SELECT admin_user_creation_det.new_user_name FROM admin_user_creation_det INNER JOIN admin_main_table ON admin_user_creation_det.user_id=admin_main_table.user_id WHERE admin_main_table.new_br=? AND admin_main_table.new_prof=? AND admin_main_table.progres_stus_code!=25";
                    PreparedStatement ps3 = connection.prepareStatement(sql3);
                    ps3.setString(1, branchOrDept.trim());
                    ps3.setString(2, profOrDesig.trim());

                    ResultSet rs3 = ps3.executeQuery();

                    if (rs3.next()) {

                        transfer.setFullName(rs3.getString(1));
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
        return transfer;
    }

}
